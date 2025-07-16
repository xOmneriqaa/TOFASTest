angular.module("ykyRefreeApp", ["ykRefreeControllers"])
    .config(["$stateProvider", function ($stateProvider) {
        $stateProvider.state("objections", {
            url: "/objections",
            templateUrl: "/ng/view/yky/refree/index.html",
            controller: "ykyRefreeHomeController",
            ncyBreadcrumb: {
                label: "{{'objections'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_REF']
            }
        }).state("objections.all", {
            url: "/all",
            templateUrl: "/ng/view/yky/refree/list.html",
            controller: "ykyRefreeAllController",
            ncyBreadcrumb: {
                label: "{{'allObjections'|translate}}"
            }
        }).state("objections.my", {
            url: "/my",
            templateUrl: "/ng/view/yky/refree/list.html",
            controller: "ykyRefreeMyController",
            ncyBreadcrumb: {
                label: "{{'myObjections'|translate}}"
            }
        });
    }]);