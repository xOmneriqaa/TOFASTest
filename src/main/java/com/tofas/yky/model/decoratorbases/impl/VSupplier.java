package com.tofas.yky.model.decoratorbases.impl;

import com.tofas.yky.model.decoratorbases.ISupplier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "TFS_YKY", name = "V_SUPPLIERS")
public class VSupplier implements Serializable, ISupplier {

    @Id
    @Column(name = "SUPPLIER_CODE")
    private Long code;

    @Column(name = "SUPPLIER_SAP_CODE", unique = true)
    private String sapCode;

    @Column(name = "SUPPLIER_NAME")
    private String name;

    @Column(name = "DIRECT_MATERIAL_FLAG")
    private String directMaterialFlag;

    @Column(name = "AUXILIARY_MATERIAL_FLAG")
    private String auxiliaryMaterialFlag;

    @Column(name = "SPARE_PART_FLAG")
    private String sparePartFlag;

    @Column(name = "SHEET_FLAG")
    private String sheetFlag;

    @Column(name = "EXTRA_SERIE_FLAG")
    private String extraSerieFlag;

    @Column(name = "POLO_FLAG")
    private String poloFlag;

    public VSupplier() {
        code = -1L;
        name = "";
        sapCode = "";
    }

    @Override
    public boolean isDirectMaterial() {
        return this.directMaterialFlag != null && this.directMaterialFlag.equals("Y");
    }

    @Override
    public boolean isAuxiliaryMaterial() {
        return this.auxiliaryMaterialFlag != null && this.auxiliaryMaterialFlag.equals("Y");
    }

    @Override
    public boolean isSparePart() {
        return this.sparePartFlag != null && this.sparePartFlag.equals("Y");
    }

    @Override
    public boolean isSheet() {
        return this.sheetFlag != null && this.sheetFlag.equals("Y");
    }

    @Override
    public boolean isExtraSerie() {
        return this.extraSerieFlag != null && this.extraSerieFlag.equals("Y");
    }

    @Override
    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    @Override
    public String getSapCode() {
        return sapCode;
    }

    public void setSapCode(String sapCode) {
        this.sapCode = sapCode;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirectMaterialFlag() {
        return directMaterialFlag;
    }

    public void setDirectMaterialFlag(String directMaterialFlag) {
        this.directMaterialFlag = directMaterialFlag;
    }

    public String getAuxiliaryMaterialFlag() {
        return auxiliaryMaterialFlag;
    }

    public void setAuxiliaryMaterialFlag(String auxiliaryMaterialFlag) {
        this.auxiliaryMaterialFlag = auxiliaryMaterialFlag;
    }

    public String getSparePartFlag() {
        return sparePartFlag;
    }

    public void setSparePartFlag(String sparePartFlag) {
        this.sparePartFlag = sparePartFlag;
    }

    public String getSheetFlag() {
        return sheetFlag;
    }

    public void setSheetFlag(String sheetFlag) {
        this.sheetFlag = sheetFlag;
    }

    public String getExtraSerieFlag() {
        return extraSerieFlag;
    }

    public void setExtraSerieFlag(String extraSerieFlag) {
        this.extraSerieFlag = extraSerieFlag;
    }

    public String getPoloFlag() {
        return poloFlag;
    }

    public void setPoloFlag(String poloFlag) {
        this.poloFlag = poloFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VSupplier vSupplier = (VSupplier) o;

        if (code != null ? !code.equals(vSupplier.code) : vSupplier.code != null) return false;
        return !(sapCode != null ? !sapCode.equals(vSupplier.sapCode) : vSupplier.sapCode != null);

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (sapCode != null ? sapCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VSupplier{" +
                "code=" + code +
                ", sapCode='" + sapCode + '\'' +
                ", name='" + name + '\'' +
                ", directMaterialFlag='" + directMaterialFlag + '\'' +
                ", auxiliaryMaterialFlag='" + auxiliaryMaterialFlag + '\'' +
                ", sparePartFlag='" + sparePartFlag + '\'' +
                ", sheetFlag='" + sheetFlag + '\'' +
                ", extraSerieFlag='" + extraSerieFlag + '\'' +
                ", poloFlag='" + poloFlag + '\'' +
                '}';
    }
}
