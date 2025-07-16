package com.tofas.yky.model.losses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonTimestampDeSerializer;
import com.tofas.core.common.serializer.TfJsonTimestampSerializer;
import com.tofas.core.common.utility.TfAuthUtility;
import com.tofas.yky.enums.LossState;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_LOSS_STATE_CHANGES")
public class LossStateChange implements Comparable<LossStateChange> {

    @Id
    @SequenceGenerator(name = "SEQ_YKY_LOSS_STATE_CHANGES", sequenceName = "SEQ_YKY_LOSS_STATE_CHANGES", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_YKY_LOSS_STATE_CHANGES", strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "LOSS_ID")
    private Loss loss;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATE_CODE")
    private LossState lossState;

    @JsonSerialize(using = TfJsonTimestampSerializer.class)
    @JsonDeserialize(using = TfJsonTimestampDeSerializer.class)
    @Column(name = "INSDATE")
    private Timestamp insertedDate;

    @Column(name = "INSBY")
    private String insertedBy;

    @PrePersist
    public void beforeInsert() {
        this.insertedBy = TfAuthUtility.getUsername();
        this.insertedDate = new Timestamp(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Loss getLoss() {
        return loss;
    }

    public void setLoss(Loss loss) {
        this.loss = loss;
    }

    public LossState getLossState() {
        return lossState;
    }

    public void setLossState(LossState lossState) {
        this.lossState = lossState;
    }

    public Timestamp getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(Timestamp insertedDate) {
        this.insertedDate = insertedDate;
    }

    public String getInsertedBy() {
        return insertedBy;
    }

    public void setInsertedBy(String insertedBy) {
        this.insertedBy = insertedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LossStateChange that = (LossStateChange) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (loss != null ? !loss.equals(that.loss) : that.loss != null) return false;
        if (lossState != that.lossState) return false;
        if (insertedDate != null ? !insertedDate.equals(that.insertedDate) : that.insertedDate != null) return false;
        return !(insertedBy != null ? !insertedBy.equals(that.insertedBy) : that.insertedBy != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (lossState != null ? lossState.hashCode() : 0);
        result = 31 * result + (insertedDate != null ? insertedDate.hashCode() : 0);
        result = 31 * result + (insertedBy != null ? insertedBy.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LossStateChange{" +
                "id=" + id +
                ", loss=" + loss +
                ", lossState=" + lossState +
                ", insertedDate=" + insertedDate +
                ", insertedBy='" + insertedBy + '\'' +
                '}';
    }

    @Override
    public int compareTo(LossStateChange o) {
        if(this.getInsertedDate() == null || o.getInsertedDate() == null) {
            return ObjectUtils.compare(this.lossState, o.getLossState());
        } else {
            return ObjectUtils.compare(this.getInsertedDate(), o.getInsertedDate());
        }
    }
}
