package com.tofas.yky.model.losses.qualitylab;

import com.tofas.core.common.model.base.TfEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_QUALITY_LAB_TESTS")
public class QualityLabTest extends TfEntity {

    @Id
    @SequenceGenerator(name = "SEQ_YKY_QUALITY_LAB_TESTS", sequenceName = "SEQ_YKY_QUALITY_LAB_TESTS", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_YKY_QUALITY_LAB_TESTS", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        QualityLabTest that = (QualityLabTest) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QualityLabTest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
