package com.tofas.yky.model;

import com.tofas.core.common.model.base.TfEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_MODELS")
public class Model extends TfEntity {

    @Id
    private String code;

    @Column
    private String name;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

        Model model = (Model) o;

        if (code != null ? !code.equals(model.code) : model.code != null) return false;
        return !(name != null ? !name.equals(model.name) : model.name != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Model{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
