package com.tofas.yky.model.dto.report;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.LossType;
import com.tofas.yky.enums.ProductionSubLossType;

import java.util.Date;

/**
 * Created by t40127 on 29.03.2017.
 */
public class DiscardedPartReportSearchParamsDto {

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date lossDateStart;

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date lossDateEnd;

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date lossInsDateStart;

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date lossInsDateEnd;
    
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date lossDate;
    
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date lossInsDate;

    private LossState lossState;

    private LossType lossType;

    private ProductionSubLossType productionSubLossType;

    public Date getLossDateStart() {
        return lossDateStart;
    }

    public void setLossDateStart(Date lossDateStart) {
        this.lossDateStart = lossDateStart;
    }

    public Date getLossDateEnd() {
        return lossDateEnd;
    }

    public void setLossDateEnd(Date lossDateEnd) {
        this.lossDateEnd = lossDateEnd;
    }

    public Date getLossInsDateStart() {
        return lossInsDateStart;
    }

    public void setLossInsDateStart(Date lossInsDateStart) {
        this.lossInsDateStart = lossInsDateStart;
    }

    public Date getLossInsDateEnd() {
        return lossInsDateEnd;
    }

    public void setLossInsDateEnd(Date lossInsDateEnd) {
        this.lossInsDateEnd = lossInsDateEnd;
    }

    public Date getLossDate() {
		return lossDate;
	}

	public void setLossDate(Date lossDate) {
		this.lossDate = lossDate;
	}
	

	public Date getLossInsDate() {
		return lossInsDate;
	}

	public void setLossInsDate(Date lossInsDate) {
		this.lossInsDate = lossInsDate;
	}

	public LossState getLossState() {
        return lossState;
    }

    public void setLossState(LossState lossState) {
        this.lossState = lossState;
    }

    public LossType getLossType() {
        return lossType;
    }

    public void setLossType(LossType lossType) {
        this.lossType = lossType;
    }

    public ProductionSubLossType getProductionSubLossType() {
        return productionSubLossType;
    }

    public void setProductionSubLossType(ProductionSubLossType productionSubLossType) {
        this.productionSubLossType = productionSubLossType;
    }
}
