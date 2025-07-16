package com.tofas.yky.events.listener;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.tofas.core.restful.model.dto.announcements.TfSessionBasedFileUpladFileInfo;
import com.tofas.yky.events.ClearOrphanUploadedFilesEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by t40127 on 14.12.2016.
 */
@Component
public class ClearOrphanUploadedFilesEventListener {

    @Autowired
    public ClearOrphanUploadedFilesEventListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleEvent(ClearOrphanUploadedFilesEvent event) {
        List<TfSessionBasedFileUpladFileInfo> cachedFiles = event.getFiles();

        for (TfSessionBasedFileUpladFileInfo file : cachedFiles) {
            file.getFile()
                    .toFile()
                    .delete();
        }
        cachedFiles.clear();
    }
}
