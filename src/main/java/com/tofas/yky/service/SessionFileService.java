package com.tofas.yky.service;

import com.google.common.eventbus.EventBus;
import com.tofas.core.restful.model.dto.announcements.TfSessionBasedFileUploader;
import com.tofas.yky.events.ClearOrphanUploadedFilesEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by t40127 on 14.12.2016.
 */
@Service
public class SessionFileService {

    private final TfSessionBasedFileUploader tfSessionBasedFileUploader;

    private final EventBus eventBus;

    @Autowired
    public SessionFileService(TfSessionBasedFileUploader tfSessionBasedFileUploader, EventBus eventBus) {
        this.tfSessionBasedFileUploader = tfSessionBasedFileUploader;
        this.eventBus = eventBus;
    }


    public boolean addFile(MultipartFile file) {
        return tfSessionBasedFileUploader.addFile(file);
    }

    public boolean removeFile(String fileNames) {
        tfSessionBasedFileUploader.removeFile(fileNames);
        return true;
    }

    public boolean clearAllFiles() {
        eventBus.post(new ClearOrphanUploadedFilesEvent(tfSessionBasedFileUploader.getFiles()));
        return true;
    }

    public TfSessionBasedFileUploader getSessionBasedFileUploader() {
        return tfSessionBasedFileUploader;
    }
}
