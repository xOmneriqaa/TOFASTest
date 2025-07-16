package com.tofas.yky.controller;
/* T40127 @ 11.11.2015. */

import com.tofas.yky.enums.SupplierType;
import com.tofas.yky.model.decoratorbases.impl.VSupplier;
import com.tofas.yky.model.dto.invoice.SupplierBasedInvoiceModelDto;
import com.tofas.yky.model.dto.report.*;
import com.tofas.yky.model.report.LossDetailReportObject;
import com.tofas.yky.model.report.SqpReportObject;
import com.tofas.yky.model.report.VEfficiencyReport;
import com.tofas.yky.service.DiscardedPartService;
import com.tofas.yky.service.InvoiceReportService;
import com.tofas.yky.service.report.DiscardedPartReportService;
import com.tofas.yky.service.report.MainReportService;
import com.tofas.yky.service.report.SqpReportService;
import com.tofas.yky.service.SupplierBasedReportService;
import com.tofas.yky.service.report.EfficiencyReportService;
import com.tofas.yky.service.report.LossDetailReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequestMapping("/report/api")
@RestController
public class ReportController {

    private final MainReportService mainReportService;

    private final DiscardedPartService discardedPartService;

    private final SupplierBasedReportService supplierBasedReportService;

    private final InvoiceReportService invoiceReportService;

    private final EfficiencyReportService efficiencyReportService;

    private final DiscardedPartReportService discardedPartReportService;
    
    private final LossDetailReportService lossDetailReportService;
    
    private final SqpReportService sqpReportService;

    private List<SupplierType> supplierTypes;

    @Autowired
    public ReportController(MainReportService mainReportService, DiscardedPartService discardedPartService,
                            SupplierBasedReportService supplierBasedReportService,
                            InvoiceReportService invoiceReportService, EfficiencyReportService efficiencyReportService, DiscardedPartReportService discardedPartReportService,LossDetailReportService lossDetailReportService,
                            SqpReportService sqpReportService) {
        this.mainReportService = mainReportService;
        this.discardedPartService = discardedPartService;
        this.supplierBasedReportService = supplierBasedReportService;
        this.invoiceReportService = invoiceReportService;
        this.efficiencyReportService = efficiencyReportService;
        this.discardedPartReportService = discardedPartReportService;
        this.lossDetailReportService=lossDetailReportService;
        this.sqpReportService=sqpReportService;
    }

    @PostConstruct
    public void init() {
        supplierTypes = new ArrayList<>();
        supplierTypes.add(SupplierType.LOCAL_SUPPLIER);
        supplierTypes.add(SupplierType.EXTRA_SERIE);
        supplierTypes.add(SupplierType.FIAT_POLO);
    }

    @RequestMapping("/main-report")
    public List<MainReportObjectDto> getMainReport(@RequestBody MainReportSearchParamsDto searchParams) {
        return mainReportService.listMainReport(searchParams);
    }

    @RequestMapping("/supplier-based-report")
    public Collection<SupplierBasedReportObjectDto> getSupplierBasedReport(@RequestBody SupplierBasedReportSearchParams supplierBasedReportSearchParams){
        return supplierBasedReportService.getReport(supplierBasedReportSearchParams);
    }

    @RequestMapping("/get-work-types")
    public Collection<String> getWorkTypes() {
        return mainReportService.getWorkTypes();
    }

    @RequestMapping("/get-suppliers")
    public List<VSupplier> getSuppliers() {
        return discardedPartService.getSuppliers();
    }

    @RequestMapping("/supplier-types")
    public List<SupplierType> getSupplierTypes() {
        return supplierTypes;
    }

    @RequestMapping("/invoice-ready-losses")
    public Collection<SupplierBasedInvoiceModelDto> getInvoiceReadyLosses() {
        return invoiceReportService.getInvoiceReadyLosses();
    }

    @RequestMapping("/efficiency-report")
    public Collection<VEfficiencyReport> listReport(@RequestBody EfficiencyReportSearchParamsDto efficiencyReportSearchParamsDto) {
        return efficiencyReportService.listReport(efficiencyReportSearchParamsDto);
    }

    @RequestMapping("/discarded-part-report")
    public List<DiscardPartReportDto> listDiscardedPartReport(
            @RequestBody DiscardedPartReportSearchParamsDto discardedPartReportSearchParamsDto) {
        return discardedPartReportService.listReport(discardedPartReportSearchParamsDto);
    }
    
    @RequestMapping("/loss-detail-report")
    public List<LossDetailReportObject> getLossDetailReport(@RequestBody MainReportSearchParamsDto searchParams) {
        return lossDetailReportService.lossDetailReportObjectList(searchParams);
    }
    
    @RequestMapping("/sqp-report")
    public List<SqpReportObject> getSqpReport(@RequestBody MainReportSearchParamsDto searchParams) {
        return sqpReportService.sqpReportObjectList(searchParams);
    }
}
