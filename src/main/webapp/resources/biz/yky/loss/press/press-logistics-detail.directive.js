/**
 * Created by t40127 on 17.03.2017.
 */


var pressLogisticsDetailDirective = function(){
    return {
        restrict: "AE",
        replace: "true",
        scope: {
            edit: "=edit",
            detailEdit: "=detailEdit",
            lossDetails: "=lossDetails",
            hrdParams: "=hrdParams"
        },
        templateUrl: "/ng/view/yky/loss/press/press-logistics-detail.table.html",
        controller: pressLossDetailDirectiveController
    };
};

angular.module("ykyPressModule")
    .directive("pressLogisticsDetailTable", pressLogisticsDetailDirective);