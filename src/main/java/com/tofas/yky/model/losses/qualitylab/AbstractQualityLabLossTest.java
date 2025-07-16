package com.tofas.yky.model.losses.qualitylab;
/* t40127 @ 05.05.2016. */

import javax.persistence.*;
import java.math.BigDecimal;

@MappedSuperclass
public class AbstractQualityLabLossTest  {

    @Id
    @SequenceGenerator(name = "SEQ_YKY_QUALITY_LAB_LS_TESTS", sequenceName = "SEQ_YKY_QUALITY_LAB_LS_TESTS", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_YKY_QUALITY_LAB_LS_TESTS", strategy = GenerationType.SEQUENCE)
    protected Long id;

    @Column
    protected Double qty;

    @Column(name = "QUALITY_TEST_NAME_CACHED")
    protected String qualityTestNameCached;

    @Column(name = "QUALITY_TEST_PRICE_CACHED")
    protected BigDecimal qualityTestPriceCached;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public String getQualityTestNameCached() {
        return qualityTestNameCached;
    }

    public void setQualityTestNameCached(String qualityTestNameCached) {
        this.qualityTestNameCached = qualityTestNameCached;
    }

    public BigDecimal getQualityTestPriceCached() {
        return qualityTestPriceCached;
    }

    public void setQualityTestPriceCached(BigDecimal qualityTestPriceCached) {
        this.qualityTestPriceCached = qualityTestPriceCached;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractQualityLabLossTest that = (AbstractQualityLabLossTest) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (qty != null ? !qty.equals(that.qty) : that.qty != null) return false;
        if (qualityTestNameCached != null ? !qualityTestNameCached.equals(that.qualityTestNameCached) : that.qualityTestNameCached != null)
            return false;
        return !(qualityTestPriceCached != null ? !qualityTestPriceCached.equals(that.qualityTestPriceCached) : that.qualityTestPriceCached != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (qty != null ? qty.hashCode() : 0);
        result = 31 * result + (qualityTestNameCached != null ? qualityTestNameCached.hashCode() : 0);
        result = 31 * result + (qualityTestPriceCached != null ? qualityTestPriceCached.hashCode() : 0);
        return result;
    }

    public BigDecimal getTotalCost() {
        return this.qualityTestPriceCached.multiply(BigDecimal.valueOf(qty));
    }

}
