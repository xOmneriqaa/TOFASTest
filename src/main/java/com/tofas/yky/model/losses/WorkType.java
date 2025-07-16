package com.tofas.yky.model.losses;

import com.tofas.core.common.model.base.TfEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_WORK_TYPE")
public class WorkType extends TfEntity {

    @Id
    @SequenceGenerator(name="SEQ_YKY_WORK_TYPE", sequenceName="SEQ_YKY_WORK_TYPE", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_WORK_TYPE", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Column(name = "WAGE_IN_MINUTES")
    private BigDecimal wageInMinutes;

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

    public BigDecimal getWageInMinutes() {
        return wageInMinutes;
    }

    public void setWageInMinutes(BigDecimal wageInMinutes) {
        this.wageInMinutes = wageInMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        WorkType workType = (WorkType) o;

        if (id != null ? !id.equals(workType.id) : workType.id != null) return false;
        if (name != null ? !name.equals(workType.name) : workType.name != null) return false;
        return !(wageInMinutes != null ? !wageInMinutes.equals(workType.wageInMinutes) : workType.wageInMinutes != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (wageInMinutes != null ? wageInMinutes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WorkType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", wageInMinutes=" + wageInMinutes +
                '}';
    }
}
