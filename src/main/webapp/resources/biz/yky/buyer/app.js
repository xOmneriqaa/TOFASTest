angular.module("ykyBuyerApp", ["ykyBuyerControllers"])
    .config(["$stateProvider", function ($stateProvider) {

        $stateProvider.state("buyer", {
            url: "/buyer",
            templateUrl: "/ng/view/yky/buyer/index.html",
            ncyBreadcrumb: {
                label: "{{'buyer'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_BUYER']
            }
        }).state("buyer.unPricedParts", {
            url: "/un-priced-parts",
            templateUrl: "/ng/view/yky/buyer/un-priced-parts.html",
            controller: "ykyBuyerUnPricedPartsController",
            ncyBreadcrumb: {
                label: "{{'unPricedParts'|translate}}"
            }
        }).state("buyer.unPricedParts.addPrice", {
            url: "/:id",
            templateUrl: "/ng/view/yky/buyer/add-price.html",
            controller: "ykyBuyerAddPricePartController",
            ncyBreadcrumb: {
                label: "{{'addPrice'|translate}}"
            }
        });

    }]);
