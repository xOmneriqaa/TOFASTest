package com.tofas.yky.model.losses.discards;
/* t40127 @ 05.05.2016. */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.model.base.TfEntity;
import com.tofas.core.common.serializer.TfJsonDateSerializer;
import com.tofas.yky.enums.BaseUnit;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public class AbstractDiscardedPart extends TfEntity {

    @Id
    @SequenceGenerator(name="SEQ_YKY_DISCARDED_PARTS", sequenceName="SEQ_YKY_DISCARDED_PARTS", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_DISCARDED_PARTS", strategy = GenerationType.SEQUENCE)
    protected Long id;

    protected BigDecimal qty;

    @Column(name = "BASE_PRICE")
    protected BigDecimal basePrice;

    @Column(name = "BASE_UNIT")
    protected String baseUnit;

    @JsonSerialize(using = TfJsonDateSerializer.class)
    @Column(name = "PRICE_DATE")
    protected Date priceDate;

    @Column(name = "PROVIDED_BY")
    protected String providedBy;

    @Transient
    protected BaseUnit baseUnitType;

    @Column(name = "DISCARD_VOUCHER_NO")
    protected Long discardedPartVoucherNo;

    @PostLoad
    public void setBaseUnitType() {
        this.baseUnitType = BaseUnit.getUnit(this.baseUnit);
    }

    public BigDecimal getTotalCost() {
        if(this.getBasePrice() != null) {
            return this.basePrice.multiply(qty);
        } else {
            return BigDecimal.ZERO;
        }
    }

    public String getBaseUnitText() {
        return baseUnitType.getLabel();
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit;
    }

    public BaseUnit getBaseUnitType() {
        return baseUnitType;
    }

    public void setBaseUnitType(BaseUnit baseUnitType) {
        this.baseUnitType = baseUnitType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }

    public String getProvidedBy() {
        return providedBy;
    }

    public void setProvidedBy(String providedBy) {
        this.providedBy = providedBy;
    }

    public Long getDiscardedPartVoucherNo() {
        return discardedPartVoucherNo;
    }

    public void setDiscardedPartVoucherNo(Long discardedPartVoucherNo) {
        this.discardedPartVoucherNo = discardedPartVoucherNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AbstractDiscardedPart that = (AbstractDiscardedPart) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(qty, that.qty) &&
                Objects.equals(discardedPartVoucherNo, that.discardedPartVoucherNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, qty, discardedPartVoucherNo);
    }

    @Override
    public String toString() {
        return "DiscardedPart{" +
                "id=" + id +
                ", qty=" + qty +
                ", basePrice=" + basePrice +
                ", priceDate=" + priceDate +
                ", providedBy='" + providedBy + '\'' +
                '}';
    }

    public boolean hasBasePrice() {
        return getBasePrice() != null && getBasePrice().compareTo(BigDecimal.ZERO) >= 0;
    }
}
