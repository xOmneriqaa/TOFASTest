package com.tofas.yky.model.dto.press.detail;
/* t40127 @ 09.06.2016. */

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Embeddable
public class AtrscField {

    @Column(name = "ATRSC_FIELD")
    protected String atrscField;

    public AtrscField() { }

    public AtrscField(String atrscField) {
        this.atrscField = atrscField;
    }

    public AtrscField(AtrscField atrscField) {
        this.atrscField = atrscField.getAtrscField();
    }

    @JsonIgnore
    public String getAtrscField() {
        return atrscField;
    }

    public void setAtrscField(String atrscField) {
        this.atrscField = atrscField;
    }

    public List<String> getAtrsc() {
        if(atrscField != null && atrscField.trim().length() > 0) {
            return Arrays.asList(atrscField.split(" "));
        } else {
            return Collections.emptyList();
        }

    }

    public void setAtrsc(List<String> atrsc) {
        atrscField = "";
        if(atrsc != null) {
            for (String s : atrsc) {
                atrscField += s + " ";
            }
        }

        atrscField = atrscField.trim();
    }
}