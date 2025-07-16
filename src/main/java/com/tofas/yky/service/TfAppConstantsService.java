package com.tofas.yky.service;

import com.tofas.core.common.model.TfAppParam;
import com.tofas.core.common.repository.TfAppParametersRepository;
import com.tofas.yky.model.dto.press.config.PressParameters;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class TfAppConstantsService {

    private String blueCollarId;

    private String lossAttachmentsUploadFolder;

    private String app_url;

    private String developer_email;

    private String application_email_personal;

    private String key_user_email;

    private String invoicable_minimum_loss_cost;

    private String numOfDaysForInternalSupplier;

    private String numOfDaysForExtraSerieSupplier;

    private String numOfDaysNotAnsweredObjections;

    private String numOfDaysDeniedObjections;

    // press
    private String customsCostParam;

    private String logisticsCostParam;

    private String scrapSalesParamHrd088;

    private String scrapSalesParamHrd033;

    private String blueCollarBaseWageAsEur;

    @Resource private TfAppParametersRepository tfAppParametersRepository;

    @PostConstruct
    public void init() {
        blueCollarId = "blue_collar_id";
        lossAttachmentsUploadFolder = "loss-attachments/";
        app_url = "app_url";
        developer_email = "developer_email";
        application_email_personal = "application_email_personal";
        key_user_email = "key_user_email";
        invoicable_minimum_loss_cost = "invoicable_minimum_loss_cost";
        numOfDaysForInternalSupplier = "numOfDaysForInternalSupplier";
        numOfDaysForExtraSerieSupplier = "numOfDaysForExtraSerieSupplier";
        numOfDaysNotAnsweredObjections = "numOfDaysNotAnsweredObjections";
        numOfDaysDeniedObjections = "numOfDaysDeniedObjections";
        customsCostParam = "customsCostParam";
        logisticsCostParam = "logisticsCostParam";
        scrapSalesParamHrd088 = "scrapSalesParamHrd088";
        scrapSalesParamHrd033 = "scrapSalesParamHrd033";
        blueCollarBaseWageAsEur = "blueCollarBaseWageAsEur";
    }

    public String getProductionLossLogisticsApprover() {
        return "TFG_YKY_APPROVAL_LOGISTICS";
    }

    public String getProductionLossQualityApprover() {
        return "TFG_YKY_APPROVAL_QUALITY";
    }

    public Long getBlueCollarId() {
        return Long.parseLong(tfAppParametersRepository.findOneByName(blueCollarId).getValue());
    }

    public String getLossAttachmentsUploadFolder() {
        return lossAttachmentsUploadFolder;
    }

    public String getLossAttachmentPath(String fileName) {
        return getLossAttachmentsUploadFolder() + fileName;
    }

    public String getAppUrl() {
        return tfAppParametersRepository.findOneByName(app_url).getValue();
    }

    public String getDeveloperEmail() {
        return tfAppParametersRepository.findOneByName(this.developer_email).getValue();
    }

    public String getApplicationMailAddress() {
        return "yky@tofas.com.tr";
    }

    public String getApplciationName() {
        return tfAppParametersRepository.findOneByName(application_email_personal).getValue();
    }

    public String getKeyUserEmail() {
        return tfAppParametersRepository.findOneByName(key_user_email).getValue();
    }

    public BigDecimal getInvoicableMinimumCost() {
        return BigDecimal.valueOf(Long.parseLong(tfAppParametersRepository.findOneByName(invoicable_minimum_loss_cost).getValue()));
    }

    public Integer getNumOfDaysDeniedObjections() {
        try {
            return Integer.parseInt(tfAppParametersRepository.findOneByName(numOfDaysDeniedObjections).getValue());
        } catch (Exception e) {
            return 1;
        }
    }

    public Integer getNumOfDaysForExtraSerieSupplier() {
        try {
            return Integer.parseInt(tfAppParametersRepository.findOneByName(numOfDaysForExtraSerieSupplier).getValue());
        } catch (Exception e) {
            return 1;
        }
    }

    public Integer getNumOfDaysForInternalSupplier() {
        try {
            return Integer.parseInt(tfAppParametersRepository.findOneByName(numOfDaysForInternalSupplier).getValue());
        } catch (Exception e) {
            return 1;
        }
    }

    public Integer getNumOfDaysNotAnsweredObjections() {
        try {
            return Integer.parseInt(tfAppParametersRepository.findOneByName(numOfDaysNotAnsweredObjections).getValue());
        } catch (Exception e) {
            return 1;
        }
    }

    public BigDecimal getCustomsCostParam() {
        return new BigDecimal(tfAppParametersRepository.findOneByName(customsCostParam).getValue());
    }

    public BigDecimal getLogisticsCostParam() {
        return new BigDecimal(tfAppParametersRepository.findOneByName(logisticsCostParam).getValue());
    }

    public BigDecimal getScrapSalesParamHrd033() {
        return new BigDecimal(tfAppParametersRepository.findOneByName(scrapSalesParamHrd033).getValue());
    }

    public BigDecimal getBlueCollarBaseWageAsEur() {
        return new BigDecimal(tfAppParametersRepository.findOneByName(blueCollarBaseWageAsEur).getValue());
    }

    public BigDecimal getScrapSalesParamHrd088() {
        return new BigDecimal(tfAppParametersRepository.findOneByName(scrapSalesParamHrd088).getValue());
    }

    public PressParameters getPressParameters() {
        PressParameters pressParameters = new PressParameters();

        pressParameters.setCustomsCostParam(getCustomsCostParam());
        pressParameters.setLogisticsCostParam(getLogisticsCostParam());
        pressParameters.setScrapSalesParamHrd033(getScrapSalesParamHrd033());
        pressParameters.setScrapSalesParamHrd088(getScrapSalesParamHrd088());
        pressParameters.setBlueCollarBaseWageAsEur(getBlueCollarBaseWageAsEur());

        return pressParameters;
    }

    public boolean setPressParameters(PressParameters pressParameters) {
        TfAppParam tfAppParam = tfAppParametersRepository.findOneByName(customsCostParam);
        tfAppParam.setValue(pressParameters.getCustomsCostParam().toString());
        tfAppParametersRepository.save(tfAppParam);

        tfAppParam = tfAppParametersRepository.findOneByName(logisticsCostParam);
        tfAppParam.setValue(pressParameters.getLogisticsCostParam().toString());
        tfAppParametersRepository.save(tfAppParam);

        tfAppParam = tfAppParametersRepository.findOneByName(scrapSalesParamHrd033);
        tfAppParam.setValue(pressParameters.getScrapSalesParamHrd033().toString());
        tfAppParametersRepository.save(tfAppParam);

        tfAppParam = tfAppParametersRepository.findOneByName(scrapSalesParamHrd088);
        tfAppParam.setValue(pressParameters.getScrapSalesParamHrd088().toString());
        tfAppParametersRepository.save(tfAppParam);

        tfAppParam = tfAppParametersRepository.findOneByName(blueCollarBaseWageAsEur);
        tfAppParam.setValue(pressParameters.getBlueCollarBaseWageAsEur().toString());
        tfAppParametersRepository.save(tfAppParam);

        return true;
    }
}
