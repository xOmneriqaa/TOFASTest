/**
 * Created by t40127 on 17.03.2017.
 */


var pressQualityDetailDirective = function(){
    return {
        restrict: "AE",
        replace: "true",
        scope: {
            approve: "=approve",
            edit: "=edit",
            lossDetails: "=lossDetails",
            hrdParams: "=hrdParams"
        },
        templateUrl: "/ng/view/yky/loss/press/press-quality-detail.table.html",
        controller: pressLossDetailDirectiveController
    };
};

angular.module("ykyPressModule")
    .directive("pressQualityDetailDirective", pressQualityDetailDirective);