package com.tofas.yky.report;
/* T40127 @ 24.11.2015. */

import com.tofas.core.common.controllers.modelandview.AbstractCsvView;
import com.tofas.yky.model.dto.invoice.SupplierBasedInvoiceModelDto;
import com.tofas.yky.report.util.DuplicateOutputStream;
import com.tofas.yky.service.InvoiceService;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class InvoiceCsvView extends AbstractCsvView {

    public InvoiceCsvView(String fileName) {
        super(fileName, ";");
    }

    @Override
    protected void writeCsv(ServletOutputStream writer, Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        InvoiceService invoiceService = (InvoiceService) model.get("invoiceService");
        Collection<SupplierBasedInvoiceModelDto> invoicesCollection = (Collection<SupplierBasedInvoiceModelDto>) model.get("invoices");
        List<SupplierBasedInvoiceModelDto> invoices = new ArrayList<>(invoicesCollection);



        DuplicateOutputStream doubleOutStream = new DuplicateOutputStream(writer);

        try {
            for (int i = 0; i < invoices.size(); i++) {
                SupplierBasedInvoiceModelDto invoice = invoices.get(i);

                this.writeCell(doubleOutStream, Integer.toString((i + 1)))
                        .writeCell(doubleOutStream, "ZTLL")
                        .writeCell(doubleOutStream, "Z566")
                        .writeCell(doubleOutStream, "Z1")
                        .writeCell(doubleOutStream, "Z1")
                        .writeCell(doubleOutStream, "ZTBU")
                        .writeCell(doubleOutStream, "001")
                        .writeCell(doubleOutStream, invoice.getSupplier().getSapCode())
                        .writeCell(doubleOutStream, "")
                        .writeCell(doubleOutStream, "")
                        .writeCell(doubleOutStream, "")
                        .writeCell(doubleOutStream, invoice.getLossIds())
                        .writeCell(doubleOutStream, "")
                        .writeCell(doubleOutStream, "")
                        .writeCell(doubleOutStream, "")
                        .writeCell(doubleOutStream, "")
                        .writeCell(doubleOutStream, "SD_HZM017")
                        .writeCell(doubleOutStream, "")
                        .writeCell(doubleOutStream, "1")
                        .writeCell(doubleOutStream, "")
                        .writeCell(doubleOutStream, invoice.getTotalCost().toString())
                        .writeCell(doubleOutStream, "TRY")
                        .writeCell(doubleOutStream, "")
                        .writeCell(doubleOutStream, "")
                        .writeCell(doubleOutStream, invoice.getSupplier().getSapCode())
                        .writeCell(doubleOutStream, invoice.getSupplier().getSapCode())
                        .writeCell(doubleOutStream, invoice.getSupplier().getSapCode())
                        .newLine(doubleOutStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        invoiceService.saveInvoice(doubleOutStream.getContent());
    }
}
