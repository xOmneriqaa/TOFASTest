package com.tofas.yky.model.losses.discards;
/* t40127 @ 05.05.2016. */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_DISCARDED_PARTS")
public class DiscardedPartWoLoss extends AbstractDiscardedPart {

    @Column(name = "LOSS_ID")
    private Long lossId;

    public Long getLossId() {
        return lossId;
    }

    public void setLossId(Long lossId) {
        this.lossId = lossId;
    }
}
