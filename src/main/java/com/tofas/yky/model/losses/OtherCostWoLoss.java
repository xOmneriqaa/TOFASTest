package com.tofas.yky.model.losses;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tofas.core.common.model.base.TfEntity;
import com.tofas.yky.model.losses.production.duration.AbstractLossStepDuration;

@Entity
@Table(name = "YKY_LOSS_OTHER_COSTS", schema = "TFS_YKY")
public class OtherCostWoLoss extends AbstractOtherLoss {

  
    @Column(name = "LOSS_ID")
    private Long lossId;

	public Long getLossId() {
		return lossId;
	}

	public void setLossId(Long lossId) {
		this.lossId = lossId;
	}
        
}
