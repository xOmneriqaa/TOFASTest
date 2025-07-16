package com.tofas.yky.report;
/* t40127 @ 29.03.2016. */

import com.tofas.yky.model.report.InvoiceCsv;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class PreviousCsvView extends AbstractView {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm");

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        InvoiceCsv invoiceCsv = (InvoiceCsv) model.get("invoiceCsv");

        String fileName = "sap-invoice-" + sdf.format(new Date()) + ".csv";

        response.setContentType("application/csv");
        response.setHeader("content-disposition","attachment;filename =" + fileName);

        ServletOutputStream writer = response.getOutputStream();
        writer.print(invoiceCsv.getCsvData());
    }
}
