package com.tofas.yky.model.dto.form;
/* T40127 @ 24.10.2015. */

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tofas.core.common.serializer.TfJsonDateDeSerializer;

import java.util.Date;

public class MailLogSearchParams {

    private String toAddress;

    private String ccAddress;

    private String subject;

    private String body;

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date startDate;

    @JsonDeserialize(using = TfJsonDateDeSerializer.class)
    private Date endDate;

    public String getToAddress() {
        return toAddress  == null ? null : "%" + toAddress + "%";
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getCcAddress() {
        return ccAddress == null ? null : "%" + ccAddress + "%";
    }

    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    public String getSubject() {
        return subject  == null ? null : "%" + subject + "%";
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body  == null ? null : "%" + body + "%";
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
