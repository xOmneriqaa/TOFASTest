package com.tofas.yky.model.dto;
/* T40127 @ 15.10.2015. */

public class DiscardedPartDto {

    private String disegno;

    private Integer qty;

    private DiscardedPartBasePriceDto baseUnitPrice;

    private String baseUnitType;

    private Long discardedPartVoucherNo;

    public Long getDiscardedPartVoucherNo() {
        return discardedPartVoucherNo;
    }

    public void setDiscardedPartVoucherNo(Long discardedPartVoucherNo) {
        this.discardedPartVoucherNo = discardedPartVoucherNo;
    }

    public String getBaseUnitType() {
        return baseUnitType;
    }

    public void setBaseUnitType(String baseUnitType) {
        this.baseUnitType = baseUnitType;
    }

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public DiscardedPartBasePriceDto getBaseUnitPrice() {
        return baseUnitPrice;
    }

    public void setBaseUnitPrice(DiscardedPartBasePriceDto baseUnitPrice) {
        this.baseUnitPrice = baseUnitPrice;
    }

    @Override
    public String toString() {
        return "DiscardedPartDto{" +
                "disegno='" + disegno + '\'' +
                ", qty=" + qty +
                ", baseUnitPrice=" + baseUnitPrice +
                '}';
    }
}
