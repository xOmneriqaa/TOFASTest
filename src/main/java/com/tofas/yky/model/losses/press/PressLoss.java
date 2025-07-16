package com.tofas.yky.model.losses.press;
/* t40127 @ 25.04.2016. */

import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.PressFirmType;
import com.tofas.yky.enums.PressLossType;
import com.tofas.yky.model.decoratorbases.decorators.VSupplierDecorator;
import com.tofas.yky.model.losses.Loss;
import com.tofas.yky.model.losses.press.details.AbstractPressLoss;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_PRESS_LOSSES")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("PRESS")
@PrimaryKeyJoinColumn(name = "LOSS_ID")
public class PressLoss extends Loss {


    @Enumerated(EnumType.STRING)
    @Column(name = "PRESS_LOSS_TYPE")
    private PressLossType pressLossType;


    @Enumerated(value = EnumType.STRING)
    @Column(name = "FRM_INS_SHP")
    private PressFirmType firmType;

    @Embedded
    private VSupplierDecorator supplier;

    @Column(name = "FIRM_DESCRIPTION")
    private String firmDescription;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "loss")
    private List<AbstractPressLoss> details;

    public PressLoss() {
        details = new ArrayList<>();
    }

    @Override
    public BigDecimal getTotalCost() {
        BigDecimal totalCost = BigDecimal.ZERO;

        for (AbstractPressLoss detail : details) {
            totalCost = totalCost.add(detail.get_totalCost());
        }

        return totalCost;
    }

    public BigDecimal getTotalCreditNoteAmount() {
        BigDecimal creditNoteAmount = BigDecimal.ZERO;

        for (AbstractPressLoss detail : details) {
            creditNoteAmount = creditNoteAmount.add(detail.getCreditNoteAmount());
        }

        return creditNoteAmount;
    }

    public BigDecimal getBalance() {
        return getTotalCost().subtract(getTotalCreditNoteAmount());
    }

    public PressLossType getPressLossType() {
        return pressLossType;
    }

    public void setPressLossType(PressLossType pressLossType) {
        this.pressLossType = pressLossType;
    }

    public PressFirmType getFirmType() {
        return firmType;
    }

    public void setFirmType(PressFirmType firmType) {
        this.firmType = firmType;
    }

    public VSupplierDecorator getSupplier() {
        return supplier;
    }

    public void setSupplier(VSupplierDecorator supplier) {
        this.supplier = supplier;
    }

    public String getFirmDescription() {
        return firmDescription;
    }

    public void setFirmDescription(String firmDescription) {
        this.firmDescription = firmDescription;
    }

    public List<AbstractPressLoss> getDetails() {
        return details;
    }

    public void setDetails(List<AbstractPressLoss> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "PressLoss{" +
                "pressLossType=" + pressLossType +
                ", firmType='" + firmType + '\'' +
                ", supplier=" + supplier +
                ", firmDescription='" + firmDescription + '\'' +
                ", details=" + details +
                "} " + super.toString();
    }

    public void approveLoss() {
        changeLossStatus(LossState.PRESS_CLOSED);
    }
}
