package com.tofas.yky.events;
/* T40127 @ 24.10.2015. */

import com.tofas.yky.model.dto.LossCommentDto;

public class LossIsCommentedEvent  {

    private LossCommentDto comment;

    public LossIsCommentedEvent(LossCommentDto comment) {
        this.comment = comment;
    }

    public LossCommentDto getComment() {
        return comment;
    }
}
