package com.tofas.yky.model.losses.production.duration;

import com.tofas.core.common.model.base.TfEntity;
import com.tofas.yky.enums.ProductionSubLossType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_STEPS")
public class Step extends TfEntity {

    @Id
    @SequenceGenerator(name="SEQ_YKY_STEPS", sequenceName="SEQ_YKY_STEPS", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_STEPS", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @ElementCollection(targetClass = ProductionSubLossType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "YKY_STEPS_PROD_LOSS_SUB_TYP", schema = "TFS_YKY",
            joinColumns = @JoinColumn(name = "STEP_ID"))
    @Column(name = "PRODUCTION_LOSS_SUB_TYPE")
    @Enumerated(EnumType.STRING)
    private Set<ProductionSubLossType> productionSubLossTypes;

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

    public Set<ProductionSubLossType> getProductionSubLossTypes() {
        return productionSubLossTypes;
    }

    public void setProductionSubLossTypes(Set<ProductionSubLossType> productionSubLossTypes) {
        this.productionSubLossTypes = productionSubLossTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Step step = (Step) o;

        return !(id != null ? !id.equals(step.id) : step.id != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productionSubLossTypes=" + productionSubLossTypes +
                '}';
    }
}
