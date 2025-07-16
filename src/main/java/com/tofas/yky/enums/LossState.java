package com.tofas.yky.enums;

public enum LossState {

    OPEN(0),

    APPROVED(1),

    PAUSED(-1),

    INVOICE_APPROVED(2),

    INVOICED(3),

    CANCELED(4),

    SUPP_CHAIN(5),

    EX_FIRM_NOT_INVOICED(6),

    PRESS_CLOSED(7);

    private Integer value;

    LossState(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
