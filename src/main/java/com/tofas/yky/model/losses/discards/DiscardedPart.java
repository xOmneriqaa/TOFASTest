package com.tofas.yky.model.losses.discards;
/* T40127 @ 16.10.2015. */

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.yky.model.decoratorbases.decorators.VPartDecorator;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.losses.Loss;

import javax.persistence.*;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_DISCARDED_PARTS")
public class DiscardedPart extends AbstractDiscardedPart {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOSS_ID")
    @JsonIgnore
    private Loss loss;

    @Embedded
    private VPartDecorator part;

    @Embedded
    private VSupplierDecorator supplier;

    public Loss getLoss() {
        return loss;
    }

    public void setLoss(Loss loss) {
        this.loss = loss;
    }

    public VPartDecorator getPart() {
        return part;
    }

    public void setPart(VPartDecorator part) {
        this.part = part;
    }

    public VSupplierDecorator getSupplier() {
        return supplier;
    }

    public void setSupplier(VSupplierDecorator supplier) {
        this.supplier = supplier;
    }

}
