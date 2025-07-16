package com.tofas.yky.events;
/* T40127 @ 25.11.2015. */

public class SendReminderMailToObjectionResponsiblesEvent extends AbstractLossEvent {

    public SendReminderMailToObjectionResponsiblesEvent(Long lossId) {
        super(lossId);
    }
}
