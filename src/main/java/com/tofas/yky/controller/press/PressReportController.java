package com.tofas.yky.controller.press;
/* t40127 @ 26.05.2016. */

import com.tofas.yky.enums.PressLossType;
import com.tofas.yky.events.scheduled.PressDashboardDataScheduler;
import com.tofas.yky.model.dashboard.press.PressDashboardData;
import com.tofas.yky.model.dto.press.report.PressMainReportSearchParameters;
import com.tofas.yky.model.losses.press.PressLoss;
import com.tofas.yky.repositories.press.PressDashboardDataRepository;
import com.tofas.yky.service.press.PressReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loss/press/report/api")
public class PressReportController {

    private final PressReportService pressReportService;

    private final PressDashboardDataRepository pressDashboardDataRepository;

    private final PressDashboardDataScheduler pressDashboardDataScheduler;

    @Autowired
    public PressReportController(PressDashboardDataRepository pressDashboardDataRepository,
                                 PressReportService pressReportService,
                                 PressDashboardDataScheduler pressDashboardDataScheduler) {
        this.pressDashboardDataRepository = pressDashboardDataRepository;
        this.pressReportService = pressReportService;
        this.pressDashboardDataScheduler = pressDashboardDataScheduler;
    }

    @RequestMapping("/quality")
    public List<PressLoss> getPressQualityLoss(@RequestBody PressMainReportSearchParameters pressMainReportSearchParameters) {
        return pressReportService.getPressLoss(PressLossType.QUALITY, pressMainReportSearchParameters);
    }

    @RequestMapping("/logistics")
    public List<PressLoss> getPressLogisticsLoss(@RequestBody PressMainReportSearchParameters pressMainReportSearchParameters) {
        return pressReportService.getPressLoss(PressLossType.LOGISTICS, pressMainReportSearchParameters);
    }

    @RequestMapping("/dashboard")
    public List<PressDashboardData> getDashboardData() {
        pressDashboardDataScheduler.run();
        return pressDashboardDataRepository.findAll(new Sort("year", "supplierCode"));
    }

}
