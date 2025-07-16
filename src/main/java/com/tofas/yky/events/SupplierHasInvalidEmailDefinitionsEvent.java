package com.tofas.yky.events;
/* T40127 @ 01.11.2015. */

import com.tofas.yky.model.decoratorbases.ISupplier;

public class SupplierHasInvalidEmailDefinitionsEvent {

    private ISupplier supplier;
    private String disegno;

    public SupplierHasInvalidEmailDefinitionsEvent(ISupplier supplier, String disegno) {
        this.supplier = supplier;
        this.disegno = disegno;
    }

    public ISupplier getSupplier() {
        return supplier;
    }

    public String getDisegno() {
        return disegno;
    }
}
