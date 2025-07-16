package com.tofas.yky.model.losses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tofas.core.common.model.base.TfEntity;

import javax.persistence.*;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_LOSS_ATTACHMENTS")
public class LossAttachment extends TfEntity {

    @Id
    @SequenceGenerator(name="SEQ_YKY_STEPS", sequenceName="SEQ_YKY_STEPS", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_STEPS", strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LOSS_ID")
    @JsonIgnore
    private Loss loss;

    @Column
    private String name;

    @Column
    private String path;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        LossAttachment that = (LossAttachment) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(path != null ? !path.equals(that.path) : that.path != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LossAttachment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
