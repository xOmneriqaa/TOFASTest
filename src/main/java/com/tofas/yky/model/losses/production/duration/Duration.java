package com.tofas.yky.model.losses.production.duration;

import com.tofas.core.common.model.base.TfEntity;
import com.tofas.yky.model.Model;
import com.tofas.yky.model.decoratorbases.decorators.VPartDecorator;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_DURATIONS")
public class Duration extends TfEntity {

    @Id
    @SequenceGenerator(name="SEQ_YKY_DURATIONS", sequenceName="SEQ_YKY_DURATIONS", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_DURATIONS", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Embedded
    private VPartDecorator part;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "YKY_DURATION_MODELS", schema = "TFS_YKY",
            joinColumns = {@JoinColumn(name = "DURATION_ID")},
            inverseJoinColumns = {@JoinColumn(name = "MODEL_CODE")})
    private Set<Model> models;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "stepDurationId.durationDef", cascade=CascadeType.ALL)
    private Set<StepDuration> stepDurations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VPartDecorator getPart() {
        return part;
    }

    public void setPart(VPartDecorator part) {
        this.part = part;
    }

    public Set<Model> getModels() {
        return models;
    }

    public void setModels(Set<Model> models) {
        this.models = models;
    }

    public Set<StepDuration> getStepDurations() {
        return stepDurations;
    }

    public void setStepDurations(Set<StepDuration> stepDurations) {
        this.stepDurations = stepDurations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Duration duration = (Duration) o;

        return !(id != null ? !id.equals(duration.id) : duration.id != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Duration{" +
                "id=" + id +
                ", part=" + part +
                ", models=" + models +
                ", stepDurations=" + stepDurations +
                '}';
    }
}
