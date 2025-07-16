package com.tofas.yky.service;

import com.tofas.core.common.utility.TfFileUtility;
import com.tofas.core.restful.model.dto.announcements.TfSessionBasedFileUpladFileInfo;
import com.tofas.core.restful.model.dto.announcements.TfSessionBasedFileUploader;
import com.tofas.yky.model.dto.AbstractLossDto;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.LossAttachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class LossFileSaveService {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS_");

    private final TfAppConstantsService tfAppConstantsService;
    private final TfFileUtility tfFileUtility;

    @Autowired
    public LossFileSaveService(TfAppConstantsService tfAppConstantsService, TfFileUtility tfFileUtility) {
        this.tfAppConstantsService = tfAppConstantsService;
        this.tfFileUtility = tfFileUtility;
    }

    public void addFiles(Loss loss, TfSessionBasedFileUploader tfSessionBasedFileUploader) {
        if(loss.getAttachments() == null) {
            loss.setAttachments(new HashSet<>());
        }
        Set<LossAttachment> lossAttachments = loss.getAttachments();

        boolean success = true;
        for(TfSessionBasedFileUpladFileInfo file : tfSessionBasedFileUploader.getFiles()) {
            String fileName = sdf.format(new Date()) + file.getName();
            String serverFileName = tfAppConstantsService.getLossAttachmentPath(fileName);

            success &= tfFileUtility.saveFileInServerDefinedPath(serverFileName, file.getFile());

            if(success) {
                LossAttachment lossAttachment = new LossAttachment();
                lossAttachment.setName(file.getName());
                lossAttachment.setPath(fileName);
                lossAttachment.setLoss(loss);

                lossAttachments.add(lossAttachment);
            }
        }

        if(success) {
            loss.setAttachments(lossAttachments);
        } else {
            throw new RuntimeException("Exception while saving loss attachments...");
        }

    }


}
