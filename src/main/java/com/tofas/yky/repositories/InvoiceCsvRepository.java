package com.tofas.yky.repositories;
/* t40127 @ 29.03.2016. */

import com.tofas.core.common.repository.base.TfBaseRepository;
import com.tofas.yky.model.report.InvoiceCsv;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceCsvRepository extends TfBaseRepository<InvoiceCsv, Long> {


    @Query("Select new InvoiceCsv(i.id, i.insertedDate, i.insertedBy) from InvoiceCsv i")
    List<InvoiceCsv> listAllPreviousInvoices();


}
