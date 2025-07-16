package com.tofas.yky.model.losses.press.pos;
/* t40127 @ 25.04.2016. */

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(schema = "TFS_YKY", name = "V_PRESS_DISEGNO_DETAIL")
public class VPosDisegnoDetail {

    @Id
    private String disegno;

    private BigDecimal width;

    private String quality;

    private BigDecimal thickness;

    private String forma;

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public BigDecimal getThickness() {
        return thickness;
    }

    public void setThickness(BigDecimal thickness) {
        this.thickness = thickness;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VPosDisegnoDetail that = (VPosDisegnoDetail) o;

        return !(disegno != null ? !disegno.equals(that.disegno) : that.disegno != null);

    }

    @Override
    public int hashCode() {
        return disegno != null ? disegno.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "VPosDisegnoDetail{" +
                "disegno='" + disegno + '\'' +
                ", width='" + width + '\'' +
                ", quality='" + quality + '\'' +
                ", thickness='" + thickness + '\'' +
                ", forma='" + forma + '\'' +
                '}';
    }
}
