package com.tofas.yky.model.report;
/* t40127 @ 29.03.2016. */

import com.tofas.core.common.model.base.TfEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_INVOICES")
public class InvoiceCsv extends TfEntity {

    @Id
    @SequenceGenerator(name="SEQ_YKY_INVOICES", sequenceName="SEQ_YKY_INVOICES", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_INVOICES", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CSV_DATA")
    private String csvData;

    public InvoiceCsv() { }

    public InvoiceCsv(Long id, Date insDate, String insBy) {
        this();

        this.id = id;
        this.insertedDate = new Timestamp(insDate.getTime());
        this.insertedBy = insBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCsvData() {
        return csvData;
    }

    public void setCsvData(String csvData) {
        this.csvData = csvData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        InvoiceCsv that = (InvoiceCsv) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "InvoiceCsv{" +
                "id=" + id +
                ", csvData='" + csvData + '\'' +
                '}';
    }
}
