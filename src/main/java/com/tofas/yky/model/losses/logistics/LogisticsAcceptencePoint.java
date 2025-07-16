package com.tofas.yky.model.losses.logistics;


import com.tofas.core.common.model.base.TfEntity;

import javax.persistence.*;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_LGSTC_ACP_PTS")
public class LogisticsAcceptencePoint extends TfEntity {

    @Id
    @SequenceGenerator(name="SEQ_YKY_LGSTC_ACP_PTS", sequenceName="SEQ_YKY_LGSTC_ACP_PTS", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_LGSTC_ACP_PTS", strategy = GenerationType.SEQUENCE)
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

        LogisticsAcceptencePoint that = (LogisticsAcceptencePoint) o;

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
        return "LogisticsAcceptencePoint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
