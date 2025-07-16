package com.tofas.yky.controller;
/* T40127 @ 24.11.2015. */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tofas.core.common.controllers.modelandview.AbstractCsvView;
import com.tofas.yky.model.dto.invoice.SupplierBasedInvoiceModelDto;
import com.tofas.yky.model.report.InvoiceCsv;
import com.tofas.yky.report.InvoiceCsvView;
import com.tofas.yky.report.PreviousCsvView;
import com.tofas.yky.service.InvoiceReportService;
import com.tofas.yky.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/invoice")
public class InvoiceController {

    @Autowired
    InvoiceReportService invoiceReportService;

    @Autowired
    InvoiceService invoiceService;

    @RequestMapping("/get-invoices")
    public ModelAndView getInvoiceCsv(@ModelAttribute("tfFormParam") String parameter) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> params = new HashMap<>();
        try {
            Long[] lostIds = objectMapper.readValue(parameter, Long[].class);
            Collection<SupplierBasedInvoiceModelDto> invoicableModels = invoiceReportService.getInvoiceReadyLosses(lostIds);
            params.put("invoices", invoicableModels);
            params.put("invoiceService", invoiceService);

        } catch (IOException e) {
            e.printStackTrace();
        }

        AbstractCsvView view = new InvoiceCsvView("sap-sd-compatible-invoice.csv");

        return new ModelAndView(view, params);
    }

    @RequestMapping("/get-previous/{id}")
    public ModelAndView getPreviousInvoiceCsv(@PathVariable("id") Long id) {
        InvoiceCsv invoiceCsv = invoiceService.findOne(id);

        Map<String, Object> params = new HashMap<>();
        params.put("invoiceCsv", invoiceCsv);

        PreviousCsvView view = new PreviousCsvView();
        return new ModelAndView(view, params);
    }

    @ResponseBody
    @RequestMapping("/list-previous")
    public List<InvoiceCsv> listAllPreviousInvoices() {
        return invoiceService.listAllPreviousInvoices();
    }

}
