package com.tofas.yky.events.listener;
/* T40127 @ 21.10.2015. */

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.tofas.core.common.config.TfConstants;
import com.tofas.core.common.model.TfFrmLog;
import com.tofas.core.common.utility.TfLogWriterUtility;
import com.tofas.yky.enums.LossType;
import com.tofas.yky.events.AbstractLossEvent;
import com.tofas.yky.model.MailLog;
import com.tofas.yky.model.VSupplierEMail;
import com.tofas.yky.model.additional.IPartReferencedLoss;
import com.tofas.yky.model.additional.ISupplierReferencedLoss;
import com.tofas.yky.model.decoratorbases.ISupplier;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.repositories.CommonLossRepository;
import com.tofas.yky.repositories.MailLogRepository;
import com.tofas.yky.repositories.VSupplierEmailRepository;
import com.tofas.yky.repositories.VSupplierRepository;
import com.tofas.yky.service.TfAppConstantsService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mock.web.MockHttpServletRequest;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

public abstract class AbstractMailSenderWithTemplateEventListener {

    @Autowired
    protected JavaMailSender mailSender;


    @Autowired
    protected SpringTemplateEngine springTemplateEngine;

    @Autowired
    protected VSupplierRepository vSupplierRepository;

    @Autowired
    private ServletContext context;


    @Autowired
    protected TfAppConstantsService tfAppConstantsService;

    @Autowired
    private MailLogRepository mailLogRepository;

    @Autowired
    TfLogWriterUtility tfLogWriterUtility;

    @Resource
    CommonLossRepository commonLossRepository;

    @Autowired
    TfConstants tfConstants;

    protected @Autowired VSupplierEmailRepository vSupplierEmailRepository;


    protected void sendMail(Loss loss, List<String> to, List<String> cc, String subject, String template,
                            Map<String, Object> params) {
        Long lossId = null;
        String supplierCode = null;

        lossId = loss != null ? loss.getId() : -1;
        supplierCode = loss != null && loss instanceof ISupplierReferencedLoss ?
                ((ISupplierReferencedLoss) loss).getSupplier().getSapCode() : null;

        sendMail(lossId, supplierCode, to, cc, subject, template, params);
    }

    protected void sendMail(Long lossId, String supplierCode, List<String> to, List<String> cc, String subject, String template,
                            Map<String, Object> params) {

        // dont send mail if it has any polo flag
        if(supplierCode != null) {
            VSupplier vSupplier = vSupplierRepository.findBySapCode(supplierCode);

            if(vSupplier != null && vSupplier.getPoloFlag() != null && vSupplier.getPoloFlag().trim().length() > 0) {
                return;
            }

        }

        //check if mail addresses are valid
        if(to.size() == 0 && cc.size() == 0) {
            return;
        }

        try {
            // render the requested template
            params.put("contextPath", tfAppConstantsService.getAppUrl());
            String htmlContent = renderTemplate(template, params);

            Map<String, Object> mainParams = new HashMap<>();
            mainParams.put("actualContent", htmlContent);


            // put rendered content into the master template
            String wholeHtmlContent = renderTemplate("layout/email/base", mainParams);
            String imageFolder = System.getProperty("com.tofas.appFolder");




            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);


            String[] toAddressesAsArray = organizeEmailAddresses(to);

            String[] ccAddressesAsArray = organizeEmailAddresses(cc);

            // if it is qa, append qa to subject
            if(!tfConstants.isProd()) {
                subject = "** Test **" + subject;
            }

            helper.setTo(toAddressesAsArray);
            helper.setCc(ccAddressesAsArray);
            helper.setFrom(tfAppConstantsService.getApplicationMailAddress(), tfAppConstantsService.getApplciationName());
            helper.setSubject(subject);
            helper.setText(wholeHtmlContent, true);

            if(imageFolder != null) {
                byte[] bitImage = getLogo(new FileInputStream(new File((imageFolder + "/yky/upload/bit.png"))));
                byte[] tfImage = getLogo(new FileInputStream(new File((imageFolder + "/yky/upload/4logo-sade-ntf.png"))));
                helper.addInline("bit.png", new ByteArrayResource(bitImage), "image/png");
                helper.addInline("tofas.png", new ByteArrayResource(tfImage), "image/png");
            }

            mailSender.send(message);

            lossId = lossId == null ? -1 : lossId;
            writeMailLog(lossId, to, cc, subject, wholeHtmlContent);

        } catch (Exception e) {
            handleFail(e);
        }

    }

    private String[] organizeEmailAddresses(List<String> emails) {
        Set<String> addresses = new HashSet<>();

        for (String email : emails) {
            if(email != null && email.trim().length() > 0) {

                if(email.lastIndexOf(';') >= 0) {
                    String[] chunkedEmails = email.split(";");
                    for (String chunkedEmail : chunkedEmails) {
                        if(chunkedEmail != null && chunkedEmail.trim().length() > 0) {
                            addresses.add(CharMatcher.WHITESPACE.trimFrom(chunkedEmail));
                        }
                    }
                } else {
                    addresses.add(CharMatcher.WHITESPACE.trimFrom(email));
                }

            }
        }

        return addresses.toArray(new String[addresses.size()]);
    }

    private void handleFail(Exception e) {
        StringWriter stackTrace = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTrace));

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(tfAppConstantsService.getApplicationMailAddress());
        simpleMailMessage.setTo(tfAppConstantsService.getDeveloperEmail());
        simpleMailMessage.setSubject("Exception while sending email");
        simpleMailMessage.setText(stackTrace.toString());

        mailSender.send(simpleMailMessage);

        TfFrmLog frmLog = tfLogWriterUtility.saveLog(null, e);
    }

    private void writeMailLog(Long lossId, List<String> to, List<String> cc, String subject, String body) {
        MailLog mailLog = new MailLog();
        mailLog.setBody(body);
        mailLog.setCc(getMailAddresses(cc));
        mailLog.setTo(getMailAddresses(to));
        mailLog.setSubject(subject);
        mailLog.setMailDate(new Timestamp((System.currentTimeMillis())));

        mailLogRepository.save(mailLog);
    }

    private String getMailAddresses(List<String> addresses) {
        Joiner joiner = Joiner.on(';').skipNulls();
        return joiner.join(addresses);
    }

    protected String renderTemplate(String template, Map<String, Object> params) {
        final WebContext ctx = new WebContext(new MockHttpServletRequest(), null, context, new Locale("tr", "TR"), params);
        return springTemplateEngine.process(template, ctx);
    }

    public byte[] getLogo(InputStream fileName){
        byte[] image;
        try {
            image = IOUtils.toByteArray(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return image;
    }

    protected ISupplier getSupplier(Loss loss) {
        if(loss instanceof ISupplierReferencedLoss) {
            return ((ISupplierReferencedLoss) loss).getSupplier();
        } else {
            return null;
        }
    }

    protected String getDisegno(Loss loss) {
        if(loss instanceof IPartReferencedLoss) {
            return ((IPartReferencedLoss) loss).getPart().getDisegno();
        } else {
            return null;
        }
    }

    protected Loss getLoss(AbstractLossEvent event) {
        return getLoss(event.getLossId());
    }

    protected Loss getLoss(Long lossId) {
        return commonLossRepository.getLoss(lossId);
    }

    protected List<String> getSupplierEmailsOfLoss(Loss loss) {
        List<String> toAddresses = new ArrayList<>();

        if(loss instanceof ISupplierReferencedLoss) {
            List<VSupplierEMail> supplierEMails =
                    getSupplierEmail(((ISupplierReferencedLoss) loss).getSupplier().getSapCode(), loss.getLossType());

            if(supplierEMails != null && supplierEMails.size() > 0) {

                for (VSupplierEMail supplierEMail : supplierEMails) {
                    toAddresses.add(supplierEMail.getEmail());
                }
            }
        }

        return toAddresses;

    }

    protected List<VSupplierEMail> getSupplierEmail(String supplierSapCode, LossType lossType) {
        if (lossType.equals(LossType.QUALITY) || lossType.equals(LossType.QUALITY_LAB)) {
            return vSupplierEmailRepository.findBySupplierSapCodeAndResponsibleType(supplierSapCode, LossType.QUALITY);
        } else if(lossType.equals(LossType.LOGISTICS)) {
            return vSupplierEmailRepository.findBySupplierSapCodeAndResponsibleType(supplierSapCode, LossType.LOGISTICS);
        } else if(lossType.equals(LossType.PRODUCTION)) {
            return vSupplierEmailRepository.findBySupplierSapCode(supplierSapCode);
        }

        return Collections.emptyList();
    }


}
