package com.tofas.yky.model.dto.view;
/* T40127 @ 18.10.2015. */

import com.tofas.yky.model.losses.LossAttachment;

public class LossAttachmentViewDto {

    private String name;

    private String path;

    public LossAttachmentViewDto(LossAttachment attach) {
        this.name = attach.getName();
        this.path = attach.getPath();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "LossAttachmentForSupplierDto{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
