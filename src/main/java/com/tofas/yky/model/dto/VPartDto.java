package com.tofas.yky.model.dto;

public class VPartDto {

    protected String disegno;

    protected String description;

    public VPartDto() { }

    public VPartDto(String disegno, String description) {
        this.disegno = disegno;
        this.description = description;
    }

    public String getDisegno() {
        return disegno;
    }

    public void setDisegno(String disegno) {
        this.disegno = disegno;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VPartDto vPartDto = (VPartDto) o;

        if (disegno != null ? !disegno.equals(vPartDto.disegno) : vPartDto.disegno != null) return false;
        return !(description != null ? !description.equals(vPartDto.description) : vPartDto.description != null);

    }

    @Override
    public int hashCode() {
        int result = disegno != null ? disegno.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VPartDto{" +
                "disegno='" + disegno + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
