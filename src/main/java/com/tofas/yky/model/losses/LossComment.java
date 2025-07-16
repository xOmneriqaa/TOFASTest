package com.tofas.yky.model.losses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonDateTimeSerializer;
import com.tofas.core.common.utility.TfAuthUtility;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_LOSS_COMMENTS")
public class LossComment implements Comparable<LossComment> {

    @Id
    @SequenceGenerator(name="SEQ_YKY_LOSS_COMMENTS", sequenceName="SEQ_YKY_LOSS_COMMENTS", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_LOSS_COMMENTS", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LOSS_ID")
    @JsonIgnore
    private Loss loss;

    @Column(name = "LOSS_COMMENT")
    private String comment;

    @JsonSerialize(using = TfJsonDateTimeSerializer.class)
    @Column(name = "INSDATE")
    private Date insertedDate;

    @Column(name = "INSBY")
    private String insertedBy;

    @PrePersist
    public void prePersist() {
        this.insertedDate = new Date();
        this.insertedBy = TfAuthUtility.getUsername();
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getInsertedDate() {
        return insertedDate;
    }

    public void setInsertedDate(Date insertedDate) {
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

        LossComment that = (LossComment) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        if (insertedDate != null ? !insertedDate.equals(that.insertedDate) : that.insertedDate != null) return false;
        return !(insertedBy != null ? !insertedBy.equals(that.insertedBy) : that.insertedBy != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (insertedDate != null ? insertedDate.hashCode() : 0);
        result = 31 * result + (insertedBy != null ? insertedBy.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LossComment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", insertedDate=" + insertedDate +
                ", insertedBy='" + insertedBy + '\'' +
                '}';
    }

    @Override
    public int compareTo(LossComment o) {
        return this.insertedDate.compareTo(o.getInsertedDate());
    }
}
