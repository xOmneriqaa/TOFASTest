package com.tofas.yky.model.losses;

import com.tofas.core.common.model.base.TfEntity;

import javax.persistence.*;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_LOSS_APPROVER")
public class QualityLabLossApproverGroup extends TfEntity {

    @Id
    @SequenceGenerator(name="SEQ_YKY_LOSS_APPROVER", sequenceName="SEQ_YKY_LOSS_APPROVER", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_LOSS_APPROVER", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    public QualityLabLossApproverGroup(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public QualityLabLossApproverGroup() { }

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
    public String toString() {
        return "QualityLabLossApprover{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
