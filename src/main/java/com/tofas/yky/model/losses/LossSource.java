package com.tofas.yky.model.losses;

import com.tofas.core.common.model.base.TfEntity;

import javax.persistence.*;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_LOSS_SOURCE")
public class LossSource extends TfEntity {

    @Id
    @SequenceGenerator(name = "SEQ_YKY_LOSS_SOURCE", sequenceName = "SEQ_YKY_LOSS_SOURCE", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_YKY_LOSS_SOURCE", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SHORT_NAME")
    private String shortName;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LossSource that = (LossSource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(shortName != null ? !shortName.equals(that.shortName) : that.shortName != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LossSource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                '}';
    }
}
