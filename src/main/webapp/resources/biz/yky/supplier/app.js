angular.module("ykySupplierApp", ["ykySupplierControllers"])
    .config(["$stateProvider", function ($stateProvider) {

        $stateProvider.state("supplier", {
            url: "/supplier",
            templateUrl: "/ng/view/yky/supplier/index.html",
            ncyBreadcrumb: {
                label: "{{'supplier'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_SUPPLIER']
            }
        }).state("supplier.openLogistics", {
            url: "/open/logistics",
            templateUrl: "/ng/view/yky/supplier/open/logistics.html",
            controller: "ykySupplierOpenLogisticsController",
            ncyBreadcrumb: {
                label: "{{'openLogisticsLosses'|translate}}"
            }
        }).state("supplier.openProduction", {
            url: "/open/production",
            templateUrl: "/ng/view/yky/supplier/open/production.html",
            controller: "ykySupplierOpenProductionsController",
            ncyBreadcrumb: {
                label: "{{'openProductionLosses'|translate}}"
            }
        }).state("supplier.openQuality", {
            url: "/open/quality",
            templateUrl: "/ng/view/yky/supplier/open/quality.html",
            controller: "ykySupplierOpenQualityController",
            ncyBreadcrumb: {
                label: "{{'openQualityLosses'|translate}}"
            }
        }).state("supplier.openQualityLab", {
            url: "/open/quality-lab",
            templateUrl: "/ng/view/yky/supplier/open/quality-lab.html",
            controller: "ykySupplierOpenQualityLabController",
            ncyBreadcrumb: {
                label: "{{'openQualityLabLosses'|translate}}"
            }
        });

}]);
