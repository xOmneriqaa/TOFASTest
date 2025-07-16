package com.tofas.yky.service;

import com.google.common.eventbus.EventBus;
import com.tofas.core.common.utility.TfAuthUtility;
import com.tofas.core.restful.model.dto.announcements.TfSessionBasedFileUploader;
import com.tofas.yky.components.SupplierUserInfo;
import com.tofas.yky.events.LossIsApprovedEvent;
import com.tofas.yky.model.additional.IDiscardedPartAddableLoss;
import com.tofas.yky.model.additional.ISupplierReferencedLoss;
import com.tofas.yky.model.decoratorbases.ISupplier;
import com.tofas.yky.model.dto.AbstractLossDto;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.LossObjection;
import com.tofas.yky.model.losses.discards.DiscardedPart;
import com.tofas.yky.repositories.CommonLossRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CommonLossService {

    private final EventBus eventBus;

    private final CommonLossRepository commonLossRepository;

    private final LossFileSaveService lossFileSaveService;

    private final SupplierUserInfo supplierUserInfo;

    @Autowired
    public CommonLossService(EventBus eventBus, CommonLossRepository commonLossRepository, LossFileSaveService lossFileSaveService, SupplierUserInfo supplierUserInfo) {
        this.eventBus = eventBus;
        this.commonLossRepository = commonLossRepository;
        this.lossFileSaveService = lossFileSaveService;
        this.supplierUserInfo = supplierUserInfo;
    }

    public <T extends Loss> T getLoss(T loss) {

        boolean isUserSupplier = TfAuthUtility.getUsername().charAt(0) == 'S';

        // if user is supplier, remove extra information
        if(isUserSupplier) {

            // if user is supplier and loss is not belong to them, return null object
            ISupplier supplier = getLossSupplier(loss);
            if(supplier != null && !supplier.getSapCode().equals(supplierUserInfo.getSupplier().getSapCode())) {
                return null;
            }

            // remove discarded part info from loss detail if user is supplier
            if(loss instanceof IDiscardedPartAddableLoss) {

                for (DiscardedPart discardedPart : ((IDiscardedPartAddableLoss) loss).getDiscardedParts()) {
                    discardedPart.setBasePrice(BigDecimal.ZERO);
                    discardedPart.setBaseUnit("");
                    discardedPart.setSupplier(null);
                    discardedPart.setPriceDate(null);
                    discardedPart.setProvidedBy(null);
                    discardedPart.setQty(BigDecimal.ZERO);
                }
            }

        }


        // if loss has a objection, fill objection user emails
        LossObjection objection = loss.getCurrentObjection();
        if(objection != null) {
            Set<String> responsibles = objection.getObjectionType().getResponsibles();
            List<String> emails = commonLossRepository.getEmailsOfUsers(responsibles);

            objection.getObjectionType().setResponsibleEmails(new HashSet<>(emails));
        }

        return loss;
    }

    public void addCommonFields(Loss loss, AbstractLossDto lossDto, TfSessionBasedFileUploader tfSessionBasedFileUploader) {
        loss.setLossDate(lossDto.getLossDate());
        loss.setDescription(lossDto.getDescription());

        lossFileSaveService.addFiles(loss, tfSessionBasedFileUploader);
    }

    protected void lossIsApproved(Loss loss) {
        eventBus.post(new LossIsApprovedEvent(loss));
    }


    private ISupplier getLossSupplier(Loss loss) {
        if(loss instanceof ISupplierReferencedLoss) {
            return ((ISupplierReferencedLoss) loss).getSupplier();
        } else {
            return null;
        }
    }

    public boolean addFiles(Long lossId, TfSessionBasedFileUploader tfSessionBasedFileUploader) {
        Loss loss = commonLossRepository.getLoss(lossId);

        if(loss != null) {
            lossFileSaveService.addFiles(loss, tfSessionBasedFileUploader);

            commonLossRepository.save(loss);

            return true;
        } else {
            return false;
        }
    }
}
