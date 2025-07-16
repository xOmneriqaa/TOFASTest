package com.tofas.yky.model.losses.directdurations;
/* t40127 @ 05.05.2016. */

import com.tofas.core.common.model.base.TfEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@MappedSuperclass
public class AbstractLossDirectDuration extends TfEntity {

    @Id
    @SequenceGenerator(name="SEQ_YKY_LOSS_DIRECT_DURATIONS", sequenceName="SEQ_YKY_LOSS_DIRECT_DURATIONS", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_LOSS_DIRECT_DURATIONS", strategy = GenerationType.SEQUENCE)
    protected Long id;

    @Column(name = "PART_QUANTITY")
    protected Long qty;

    @Column(name = "DURATION")
    protected Long duration;

    @Column(name = "WORK_TYPE_TX_CACHED")
    protected String workTypeCached;

    @Column(name = "WORK_TYPE_WAGE_CACHED")
    protected BigDecimal workTypeWageCached;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getWorkTypeCached() {
        return workTypeCached;
    }

    public void setWorkTypeCached(String workTypeCached) {
        this.workTypeCached = workTypeCached;
    }

    public BigDecimal getWorkTypeWageCached() {
        return workTypeWageCached;
    }

    public void setWorkTypeWageCached(BigDecimal workTypeWageCached) {
        this.workTypeWageCached = workTypeWageCached;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AbstractLossDirectDuration that = (AbstractLossDirectDuration) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (qty != null ? !qty.equals(that.qty) : that.qty != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (workTypeCached != null ? !workTypeCached.equals(that.workTypeCached) : that.workTypeCached != null)
            return false;
        return !(workTypeWageCached != null ? !workTypeWageCached.equals(that.workTypeWageCached) : that.workTypeWageCached != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (qty != null ? qty.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (workTypeCached != null ? workTypeCached.hashCode() : 0);
        result = 31 * result + (workTypeWageCached != null ? workTypeWageCached.hashCode() : 0);
        return result;
    }

    public BigDecimal getTotalCost() {
        return BigDecimal.valueOf(duration).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP).multiply(workTypeWageCached);
    }
    
}
