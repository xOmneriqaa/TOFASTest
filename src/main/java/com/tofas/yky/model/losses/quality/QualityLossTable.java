package com.tofas.yky.model.losses.quality;

import com.tofas.core.common.model.base.TfEntity;

import javax.persistence.*;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_QUALITY_LOSS_TABLES")
public class QualityLossTable extends TfEntity {

    @Id
    @SequenceGenerator(name="SEQ_YKY_QUALITY_LOSS_TABLES", sequenceName="SEQ_YKY_QUALITY_LOSS_TABLES", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_QUALITY_LOSS_TABLES", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        QualityLossTable that = (QualityLossTable) o;

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
        return "QualityLossTable{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
