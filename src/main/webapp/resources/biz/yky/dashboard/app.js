/**
 * Created by T40127 on 26.11.2015.
 */

angular.module("ykyDashboardApp", ["ykyDashboardControllers"])
    .config(["$stateProvider", function($stateProvider){

        $stateProvider.state("home", {
            url: "/",
            templateUrl: "/ng/view/home.html",
            controller: "ykyDashboardController",
            ncyBreadcrumb: {
                label: 'YKY'
            }
        });


    }]);