package com.tofas.yky.model.losses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.model.base.TfEntity;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.core.common.serializer.TfJsonDateTimeSerializer;
import com.tofas.yky.enums.LossState;
import com.tofas.yky.enums.LossType;
import com.tofas.yky.enums.ObjectionStatus;
import com.tofas.yky.events.scheduled.returntype.LossInvoiceEventReturn;
import com.tofas.yky.model.additional.IDiscardedPartAddableLoss;
import com.tofas.yky.model.additional.ISupplierReferencedLoss;
import com.tofas.yky.model.decoratorbases.ISupplier;
import com.tofas.yky.model.losses.discards.DiscardedPart;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.*;

@Configurable
@Entity
@Table(schema = "TFS_YKY", name = "YKY_LOSSES")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "LOSS_TYPE")
public abstract class Loss extends TfEntity {

    private static final NumberFormat numberFormat;
    static {
        numberFormat = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) numberFormat).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) numberFormat).setDecimalFormatSymbols(decimalFormatSymbols);
    }

    @Id
    @SequenceGenerator(name="SEQ_YKY_LOSSES", sequenceName="SEQ_YKY_LOSSES", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_LOSSES", strategy = GenerationType.SEQUENCE)
    @Column(name = "ID")
    protected Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "LOSS_TYPE", insertable = false, updatable = false)
    protected LossType lossType;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    @Column(name = "LOSS_DATE")
    protected Date lossDate;

    @Column(name = "DESCRIPTION")
    protected String description;

    @Column(name = "CANCEL_DESCRIPTION")
    protected String cancelDescription;
    
    @Column(name = "INSBY",insertable=false, updatable=false)
    protected String insBy;
    

    @OneToMany(mappedBy = "loss", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("id")
    @Sort(type = SortType.NATURAL)
    protected SortedSet<LossStateChange> stateChanges;

    @OneToMany(mappedBy = "loss", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected Set<LossAttachment> attachments;

    @OrderBy("insertedDate")
    @Sort(type = SortType.NATURAL)
    @OneToMany(mappedBy = "loss", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected SortedSet<LossComment> comments;

    @OrderBy("step")
    @Sort(type = SortType.NATURAL)
    @OneToMany(mappedBy = "loss", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected SortedSet<LossObjection> objections;

    @PrePersist
    public void beforeInsert() {
        if(stateChanges == null) {
            stateChanges = new TreeSet<>();
        }

        LossStateChange lossStateChange = new LossStateChange();
        lossStateChange.setLoss(this);
        lossStateChange.setLossState(LossState.OPEN);
        stateChanges.add(lossStateChange);
    }

    public LossState getCurrentState() {
        if(stateChanges.size() > 0) {
            return stateChanges.last().getLossState();
        } else {
            return LossState.OPEN;
        }

    }

    public boolean getBeObjected() {
        return objections.size() == 0;
    }

    public ObjectionStatus getObjectionStatus() {
        if(objections.size() == 0) {
            return null;
        } else {
            return getCurrentObjection().getObjectionStatus();
        }
    }

    public LossObjection getCurrentObjection() {
        return this.objections.size() == 0 ? null : this.objections.last();
    }

    @JsonIgnore
    public LossObjection getFirstObjection() {
        return this.objections.size() == 0 ? null : this.objections.first();
    }

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    public Date getObjectedDate() {
        if(objections.size() == 0) {
            return null;
        } else {
            return getCurrentObjection().getInsertedDate();
        }
    }

    protected void changeLossStatus(LossState lossState) {
        LossStateChange newState = new LossStateChange();
        newState.setLoss(this);
        newState.setLossState(lossState);
        stateChanges.add(newState);
    }

    public void cancelLoss(String cancelDesc) {
        this.setStatusBool(false);
        this.setCancelDescription(cancelDesc);

        changeLossStatus(LossState.CANCELED);
    }

    public void pauseLoss() {
        changeLossStatus(LossState.PAUSED);
    }

    public void approveLoss() {
        changeLossStatus(LossState.APPROVED);
    }


    public void invoiceLoss() {
        changeLossStatus(LossState.INVOICED);
    }

    public void invoiceApprove() {
        changeLossStatus(LossState.INVOICE_APPROVED);
    }

    public void externalFirmNotInivoiced() {
        changeLossStatus(LossState.EX_FIRM_NOT_INVOICED);
    }

    public abstract BigDecimal getTotalCost();

    public void putApprovedMailParams(Map<String, Object> map) {
        map.put("desc", description);
        map.put("id", id);
        map.put("totalCost", numberFormat.format(getTotalCost()));
    }

    public LossInvoiceEventReturn processInvoiceApproveEvent(Integer numOfDaysExtraSerieSupplier,
                                                             Integer numOfDaysInternalSupplier,
                                                             Integer numOfDaysNotAnsweredObjections,
                                                             Integer numOfDaysDeniedObjections) {
        LossState lossState = getCurrentState();


        if(lossState != null && lossState.equals(LossState.APPROVED)) {

            if(this instanceof IDiscardedPartAddableLoss) {
                Set<DiscardedPart> discardedParts = ((IDiscardedPartAddableLoss) this).getDiscardedParts();

                for (DiscardedPart discardedPart : discardedParts) {
                    if(!discardedPart.hasBasePrice()) {
                        return LossInvoiceEventReturn.DISCARDED_PART_UNDEFINED_BASE_PRICE;
                    }
                }
            }


            ISupplier supplier;
            if(this instanceof ISupplierReferencedLoss) {
                supplier = ((ISupplierReferencedLoss) this).getSupplier();

                if(supplier.isExtraSerie()) {
                    return numOfDatesPassedSinceApproval(numOfDaysExtraSerieSupplier, numOfDaysNotAnsweredObjections,
                            numOfDaysDeniedObjections);
                } else {
                    return numOfDatesPassedSinceApproval(numOfDaysInternalSupplier, numOfDaysNotAnsweredObjections,
                            numOfDaysDeniedObjections);
                }

            }


            return LossInvoiceEventReturn.DO_NOTHING;
        }

        return LossInvoiceEventReturn.DO_NOTHING;
    }

    protected LossInvoiceEventReturn numOfDatesPassedSinceApproval(Integer dates,
                                                                   Integer numOfDaysNotAnsweredObjections,
                                                                   Integer numOfDaysDeniedObjections) {
        Date approvalDate = this.stateChanges.last().getInsertedDate();
        Date futureDate = DateUtils.addDays(approvalDate, dates);

        if(this.objections.size() > 0) {
            ObjectionStatus lastObjectionStatus = getObjectionStatus();

            // if objections is open for 7 days, and not answered, cancel the loss
            if(lastObjectionStatus.equals(ObjectionStatus.OPEN)) {
                Date cancelationDate = DateUtils.addDays(objections.last().getInsertedDate(),
                        numOfDaysNotAnsweredObjections);

                if((new Date()).after(cancelationDate)) {
                    return LossInvoiceEventReturn.CANCEL_DUE_TO_NOT_ANSWERED_OBJECTION;
                }

            // if objections is denied and 15 days passed, invoice the loss
            } else if(lastObjectionStatus.equals(ObjectionStatus.DENY)) {
                Date invoiceApprovalDate = DateUtils.addDays(objections.last().getUpdatedDate(),
                        numOfDaysDeniedObjections);

                if((new Date()).after(invoiceApprovalDate)) {
                    return LossInvoiceEventReturn.INVOICE_APPROVE;
                }
            }

            // if any objection exists for open / accept / transfer do not invoice approve the loss
            return LossInvoiceEventReturn.DO_NOTHING;
        }

        return (new Date()).after(futureDate) ? LossInvoiceEventReturn.INVOICE_APPROVE : LossInvoiceEventReturn.DO_NOTHING;
    }


    protected Long getTimePassedInObjectionProcess() {
        if(objections.size() == 0) {
            return 0L;
        } else {
            return objections.last().getUpdatedDate().getTime() - objections.first().getInsertedDate().getTime();
        }
    }

    public boolean getLossCanBeCancelable(Integer numOfDaysDeniedObjections) {
        if(!this.getStatusBool() && objections.size() > 0) {
            if(objections.last().getObjectionStatus().equals(ObjectionStatus.DENY)) {
                Date newDate = DateUtils.addDays(objections.last().getUpdatedDate(), numOfDaysDeniedObjections);

                if((new Date()).after(newDate)) {
                    return true;
                }
            }
        }

        return false;
    }



    // GETTER AND SETTER

    public SortedSet<LossObjection> getObjections() {
        return objections;
    }

    public void setObjections(SortedSet<LossObjection> objections) {
        this.objections = objections;
    }

    public SortedSet<LossComment> getComments() {
        return comments;
    }

    public void setComments(SortedSet<LossComment> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LossType getLossType() {
        return lossType;
    }

    public void setLossType(LossType lossType) {
        this.lossType = lossType;
    }

    public Date getLossDate() {
        return lossDate;
    }

    public void setLossDate(Date lossDate) {
        this.lossDate = lossDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SortedSet<LossStateChange> getStateChanges() {
        return stateChanges;
    }

    public void setStateChanges(SortedSet<LossStateChange> stateChanges) {
        this.stateChanges = stateChanges;
    }

    public Set<LossAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<LossAttachment> attachments) {
        this.attachments = attachments;
    }

    public String getCancelDescription() {
        return cancelDescription;
    }

    public void setCancelDescription(String cancelDescription) {
        this.cancelDescription = cancelDescription;
    }

    public String getInsBy() {
		return insBy;
	}

	public void setInsBy(String insBy) {
		this.insBy = insBy;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Loss loss = (Loss) o;

        return !(id != null ? !id.equals(loss.id) : loss.id != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Loss{" +
                "id=" + id +
                ", lossType=" + lossType +
                ", lossDate=" + lossDate +
                ", description='" + description + '\'' +
                '}';
    }



}
