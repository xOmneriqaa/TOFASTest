package com.tofas.yky.service;
/* t40127 @ 29.03.2016. */

import com.tofas.yky.model.report.InvoiceCsv;
import com.tofas.yky.repositories.InvoiceCsvRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class InvoiceService {

    @Resource
    InvoiceCsvRepository invoiceCsvRepository;

    @Transactional
    public void saveInvoice(String content) {
        InvoiceCsv invoiceCsv = new InvoiceCsv();
        invoiceCsv.setCsvData(content);

        invoiceCsvRepository.save(invoiceCsv);
    }

    public List<InvoiceCsv> listAllPreviousInvoices() {
        return invoiceCsvRepository.listAllPreviousInvoices();
    }

    public InvoiceCsv findOne(Long id) {
        return invoiceCsvRepository.findOne(id);
    }
}
