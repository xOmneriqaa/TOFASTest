package com.tofas.yky.model.losses.production.duration;
/* t40127 @ 05.05.2016. */

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@MappedSuperclass
public class AbstractLossStdStepDuration {

    @Id
    @SequenceGenerator(name = "SEQ_YKY_LOSSES_STD_STEPS", sequenceName = "SEQ_YKY_LOSSES_STD_STEPS", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_YKY_LOSSES_STD_STEPS", strategy = GenerationType.SEQUENCE)
    protected Long id;

    @Column
    protected Long qty;

    @Column(name = "STEP_NAME_CACHED")
    protected String stepNameCached;

    @Column(name = "STEP_DURATION_CACHED")
    protected BigDecimal stepDurationCached;

    @Column(name = "BLUE_COLLAR_WAGE_CACHED")
    protected BigDecimal blueCollarWageCached;

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

    public String getStepNameCached() {
        return stepNameCached;
    }

    public void setStepNameCached(String stepNameCached) {
        this.stepNameCached = stepNameCached;
    }

    public BigDecimal getStepDurationCached() {
        return stepDurationCached;
    }

    public void setStepDurationCached(BigDecimal stepDurationCached) {
        this.stepDurationCached = stepDurationCached;
    }

    public BigDecimal getBlueCollarWageCached() {
        return blueCollarWageCached;
    }

    public void setBlueCollarWageCached(BigDecimal blueCollarWageCached) {
        this.blueCollarWageCached = blueCollarWageCached;
    }
    
    @Override
    public String toString() {
        return "LossStdStepDuration{" +
                "id=" + id +
                ", qty=" + qty +
                ", stepNameCached='" + stepNameCached + '\'' +
                ", stepDurationCached=" + stepDurationCached +
                '}';
    }

    public BigDecimal getTotalCost() {
        return this.stepDurationCached.multiply(BigDecimal.valueOf(this.qty))
                .multiply(this.blueCollarWageCached).divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
    }
}
