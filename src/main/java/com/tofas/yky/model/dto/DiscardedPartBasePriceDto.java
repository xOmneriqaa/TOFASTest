package com.tofas.yky.model.dto;
/* T40127 @ 15.10.2015. */

public class DiscardedPartBasePriceDto {

    private String id;

    private String firmCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirmCode() {
        return firmCode;
    }

    public void setFirmCode(String firmCode) {
        this.firmCode = firmCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscardedPartBasePriceDto that = (DiscardedPartBasePriceDto) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return !(firmCode != null ? !firmCode.equals(that.firmCode) : that.firmCode != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firmCode != null ? firmCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DiscardedPartBasePriceDto{" +
                "id='" + id + '\'' +
                ", firmCode=" + firmCode +
                '}';
    }
}
