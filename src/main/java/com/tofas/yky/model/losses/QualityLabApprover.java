package com.tofas.yky.model.losses;

import com.tofas.core.common.model.base.TfEntity;

import javax.persistence.*;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_QUALITY_LAB_APPROVERS")
public class QualityLabApprover extends TfEntity {

    @Id
    @SequenceGenerator(name="SEQ_YKY_QUALITY_LAB_APPROVERS", sequenceName="SEQ_YKY_QUALITY_LAB_APPROVERS", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_QUALITY_LAB_APPROVERS", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "APPROVER_ROLE")
    private String approverRole;

    public QualityLabApprover() {
    }

    public QualityLabApprover(Long id, String approverRole) {
        this.id = id;
        this.approverRole = approverRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApproverRole() {
        return approverRole;
    }

    public void setApproverRole(String approverRole) {
        this.approverRole = approverRole;
    }

    @Override
    public String toString() {
        return "QualityLabApprover{" +
                "id=" + id +
                ", approverRole='" + approverRole + '\'' +
                '}';
    }
}
