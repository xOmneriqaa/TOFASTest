package com.tofas.yky.events;
/* T40127 @ 23.11.2015. */

public class LossIsCanceledDueToNotAnsweredObjectionEvent extends AbstractLossEvent {

    public LossIsCanceledDueToNotAnsweredObjectionEvent(Long lossId) {
        super(lossId);
    }
}
