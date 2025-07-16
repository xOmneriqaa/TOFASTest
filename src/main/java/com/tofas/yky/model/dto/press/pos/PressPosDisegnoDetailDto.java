package com.tofas.yky.model.dto.press.pos;
/* t40127 @ 26.04.2016. */

import com.tofas.yky.model.losses.press.pos.VPosDisegnoDetail;

import java.math.BigDecimal;

public class PressPosDisegnoDetailDto {

    private String disegno;

    private BigDecimal width;

    private String quality;

    private BigDecimal thickness;

    private String forma;

    private BigDecimal basePrice;

    public PressPosDisegnoDetailDto() { }

    public PressPosDisegnoDetailDto(VPosDisegnoDetail disegnoDetail, BigDecimal basePrice) {
        this.disegno = disegnoDetail.getDisegno();
        this.width = disegnoDetail.getWidth();
        this.quality = disegnoDetail.getQuality();
        this.thickness = disegnoDetail.getThickness();
        this.forma = disegnoDetail.getForma();
        this.basePrice = basePrice;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

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
}
