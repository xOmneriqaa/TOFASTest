package com.tofas.yky.events;

import com.tofas.core.restful.model.dto.announcements.TfSessionBasedFileUpladFileInfo;
import com.tofas.core.restful.model.dto.announcements.TfSessionBasedFileUploader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by t40127 on 14.12.2016.
 */
public class ClearOrphanUploadedFilesEvent {

    private List<TfSessionBasedFileUpladFileInfo> files;

    public ClearOrphanUploadedFilesEvent(List<TfSessionBasedFileUpladFileInfo> files) {
        this.files = new ArrayList<>(files);
        files.clear();
    }

    public List<TfSessionBasedFileUpladFileInfo> getFiles() {
        return files;
    }
}
