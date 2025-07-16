/**
 * Created by T40127 on 11.11.2015.
 */


angular.module("ykyReportsApp", ["pressReportControllers"])
    .config(["$stateProvider", function ($stateProvider) {

        $stateProvider.state("reports", {
            url: "/reports",
            templateUrl: "/ng/view/yky/report/index.html",
            ncyBreadcrumb: {
                label: "{{'reports'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_REPORT', 'TFG_YKY_SUPPLIER', 'TFG_YKY_LC_PRODUCTION', 'TFG_YKY_LC_LOGISTICS',
                    'TFG_YKY_APPROVAL_QUALITY', 'TFG_YKY_APPROVAL_LOGISTICS', 'TFG_YKY_LC_QUALITY',
                    'TFG_YKY_LC_QUALITY_LAB', 'TFG_YKY_PRESS_USER']
            }
        })

        .state("reports.mainReport", {
            url: "/main-report",
            controller: "ykyMainReportController",
            templateUrl: "/ng/view/yky/report/main-report.html",
            ncyBreadcrumb: {
                label: "{{'mainReport'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_REPORT', 'TFG_YKY_SUPPLIER', 'TFG_YKY_LC_PRODUCTION', 'TFG_YKY_LC_LOGISTICS',
                    'TFG_YKY_APPROVAL_QUALITY', 'TFG_YKY_APPROVAL_LOGISTICS', 'TFG_YKY_LC_QUALITY',
                    'TFG_YKY_LC_QUALITY_LAB', 'TFG_YKY_PRESS_USER']
            }
        })

        .state("reports.discardedParts", {
            url: "/discarded-parts",
            controller: "discardedPartReportController",
            templateUrl: "/ng/view/yky/report/discarded-parts.html",
            ncyBreadcrumb: {
                label: "{{'discardedPartsReport'|translate}}"
            },
            data: {
               roles: ['TFG_YKY_ADMIN', 'TFG_YKY_REPORT']
            }
        })

        .state("reports.supplierBased", {
            url: "/supplier-based",
            controller: "ykySupplierBasedReportController",
            templateUrl: "/ng/view/yky/report/supplier-based.html",
            ncyBreadcrumb: {
                label: "{{'supplierBasedReport'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_ADMIN']
            }
        })

        .state("reports.invoiceReadyReport", {
            url: "/invoice-ready",
            controller: "ykyInvoiceReadyReportController",
            templateUrl: "/ng/view/yky/report/invoice-ready.html",
            ncyBreadcrumb: {
                label: "{{'invoiceReadyLosses'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_ADMIN']
            }
        })

        .state("reports.previousInvoices", {
            url: "/previous-invoices",
            controller: "ykyPreviousInvoicesController",
            templateUrl: "/ng/view/yky/report/previous-invoices.html",
            ncyBreadcrumb: {
                label: "{{'previousInvoices'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_ADMIN']
            }
        })

        .state("reports.efficiency", {
            url: "/efficency",
            controller: "ykyEfficiencyController",
            templateUrl: "/ng/view/yky/report/efficiency.html",
            ncyBreadcrumb: {
                label: "{{'efficiencyReport'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_ADMIN']
            }
        })

        .state("reports.press", {
            url: "/press",
            templateUrl: "/ng/view/yky/report/press/index.html",
            ncyBreadcrumb: {
                label: "{{'pressReports'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_PRESS_USER']
            }
        })

        .state("reports.press.quality", {
            url: "/quality",
            controller: "pressQualityReportController",
            templateUrl: "/ng/view/yky/report/press/press-quality.html",
            ncyBreadcrumb: {
                label: "{{'pressQualityReports'|translate}}"
            }
        })

        .state("reports.press.logistics", {
            url: "/logistics",
            controller: "pressLogisticsReportController",
            templateUrl: "/ng/view/yky/report/press/press-logistics.html",
            ncyBreadcrumb: {
                label: "{{'pressLogisticsReports'|translate}}"
            }
        })

        .state("reports.press.details", {
            url: "/details",
            controller: "pressDetailsReportController",
            templateUrl: "/ng/view/yky/report/press/press-details.html",
            ncyBreadcrumb: {
                label: "{{'pressDetailsReports'|translate}}"
            }
        })

        .state("reports.press.dashboard", {
            url: "/dashboard",
            controller: "pressDashboardController",
            templateUrl: "/ng/view/yky/report/press/dashboard.html",
            ncyBreadcrumb: {
                label: "{{'pressDashboard'|translate}}"
            }
        })
        
           .state("reports.lossDetailReport", {
            url: "/loss-detail-report",
            controller: "lossDetailReportController",
            templateUrl: "/ng/view/yky/report/loss-detail-report.html",
            ncyBreadcrumb: {
                label: "{{'lossDetailReport'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_REPORT', 'TFG_YKY_SUPPLIER', 'TFG_YKY_LC_PRODUCTION', 'TFG_YKY_LC_LOGISTICS',
                    'TFG_YKY_APPROVAL_QUALITY', 'TFG_YKY_APPROVAL_LOGISTICS', 'TFG_YKY_LC_QUALITY',
                    'TFG_YKY_LC_QUALITY_LAB', 'TFG_YKY_PRESS_USER']
            }
        })
        
        .state("reports.sqpReport", {
            url: "/sqp-report",
            controller: "sqpReportController",
            templateUrl: "/ng/view/yky/report/sqp-report.html",
            ncyBreadcrumb: {
                label: "{{'sqpReport'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_REPORT', 'TFG_YKY_SUPPLIER', 'TFG_YKY_LC_PRODUCTION', 'TFG_YKY_LC_LOGISTICS',
                    'TFG_YKY_APPROVAL_QUALITY', 'TFG_YKY_APPROVAL_LOGISTICS', 'TFG_YKY_LC_QUALITY',
                    'TFG_YKY_LC_QUALITY_LAB', 'TFG_YKY_PRESS_USER']
            }
        })
        ;

    }]);