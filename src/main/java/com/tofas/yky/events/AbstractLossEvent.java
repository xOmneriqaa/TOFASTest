package com.tofas.yky.events;
/* T40127 @ 22.10.2015. */

public abstract class AbstractLossEvent {

    private Long lossId;

    public AbstractLossEvent(Long lossId) {
        this.lossId = lossId;
    }

    public Long getLossId() {
        return lossId;
    }
}
