package com.tofas.yky.model.losses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.core.common.model.base.TfEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "YKY_LOSS_OTHER_COSTS", schema = "TFS_YKY")
public class LossOtherCost extends TfEntity {

    @Id
    @SequenceGenerator(name = "SEQ_YKY_LOSS_OTHER_COSTS", sequenceName = "SEQ_YKY_LOSS_OTHER_COSTS", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_YKY_LOSS_OTHER_COSTS", strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "LOSS_ID")
    private Loss loss;

    private String description;

    private BigDecimal cost;

    public LossOtherCost() {}

    public LossOtherCost(Loss loss, String description, BigDecimal cost) {
        this.loss = loss;
        this.description = description;
        this.cost = cost;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LossOtherCost that = (LossOtherCost) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, description, cost);
    }

    @Override
    public String toString() {
        return "LossOtherCost{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                '}';
    }
}
