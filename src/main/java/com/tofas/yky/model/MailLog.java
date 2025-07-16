package com.tofas.yky.model;
/* T40127 @ 24.10.2015. */

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tofas.core.common.serializer.TfJsonTimestampSerializer;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_MAIL_LOGS")
public class MailLog {

    @Id
    @SequenceGenerator(name="SEQ_YKY_MAIL_LOGS", sequenceName="SEQ_YKY_MAIL_LOGS", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_MAIL_LOGS", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String subject;

    private String body;

    @Column(name = "TO_ADDRESS")
    private String to;

    @Column(name = "CC_ADDRESS")
    private String cc;

    @JsonSerialize(using = TfJsonTimestampSerializer.class)
    @Column(name = "MAIL_DATE")
    private Timestamp mailDate;

    @Column(name = "LOSS_ID")
    private Long lossId;

    public Long getLossId() {
        return lossId;
    }

    public void setLossId(Long lossId) {
        this.lossId = lossId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public Timestamp getMailDate() {
        return mailDate;
    }

    public void setMailDate(Timestamp mailDate) {
        this.mailDate = mailDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MailLog mailLog = (MailLog) o;

        if (id != null ? !id.equals(mailLog.id) : mailLog.id != null) return false;
        if (subject != null ? !subject.equals(mailLog.subject) : mailLog.subject != null) return false;
        if (body != null ? !body.equals(mailLog.body) : mailLog.body != null) return false;
        if (to != null ? !to.equals(mailLog.to) : mailLog.to != null) return false;
        if (cc != null ? !cc.equals(mailLog.cc) : mailLog.cc != null) return false;
        return !(mailDate != null ? !mailDate.equals(mailLog.mailDate) : mailLog.mailDate != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (cc != null ? cc.hashCode() : 0);
        result = 31 * result + (mailDate != null ? mailDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MailLog{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", to='" + to + '\'' +
                ", cc='" + cc + '\'' +
                ", mailDate=" + mailDate +
                '}';
    }
}
