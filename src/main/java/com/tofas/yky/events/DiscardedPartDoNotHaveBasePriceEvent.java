package com.tofas.yky.events;
/* MND @ 26.10.2016. */

public class DiscardedPartDoNotHaveBasePriceEvent extends AbstractLossEvent {

    public DiscardedPartDoNotHaveBasePriceEvent(Long lossId) {
        super(lossId);
    }
}
