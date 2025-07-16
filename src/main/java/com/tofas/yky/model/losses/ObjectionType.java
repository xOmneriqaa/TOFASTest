package com.tofas.yky.model.losses;

import com.tofas.core.common.model.base.TfEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_OBJECTION_TYPES")
public class ObjectionType extends TfEntity {

    @Id
    @SequenceGenerator(name="SEQ_YKY_OBJECTION_TYPES", sequenceName="SEQ_YKY_OBJECTION_TYPES", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_OBJECTION_TYPES", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "YKY_OBJECTIONS_RESPONSIBLES", schema = "TFS_YKY",
            joinColumns = @JoinColumn(name = "OBJECTION_TYPE_ID"))
    @Column(name="USERNAME")
    private Set<String> responsibles;

    // this value is filled on certaion operations, please find them in callers of setter method
    @Transient
    private Set<String> responsibleEmails;

    @PostLoad
    public void init() {
        responsibleEmails = new HashSet<>();
    }

    public Set<String> getResponsibleEmails() {
        return responsibleEmails;
    }

    public void setResponsibleEmails(Set<String> responsibleEmails) {
        this.responsibleEmails = responsibleEmails;
    }

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

    public Set<String> getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(Set<String> responsibles) {
        this.responsibles = responsibles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ObjectionType that = (ObjectionType) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ObjectionType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", responsibles=" + responsibles +
                '}';
    }
}
