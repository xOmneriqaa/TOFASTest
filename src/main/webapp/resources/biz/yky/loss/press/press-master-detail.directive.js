/**
 * Created by t40127 on 17.03.2017.
 */


var pressMasterDetailDirective = function(){
    return {
        restrict: "AE",
        replace: true,
        transclude: true,
        scope: {
            supplier: "=supplier",
            firmType: "=firmType",
            firmDescription: "=firmDescription",
            lossDetails: "=lossDetails",
            lossDate: "=lossDate",
            lossDescription: "=lossDescription",
            uploadErrors: "=uploadErrors",
            validator: "=validator",
            validateFunc: "&",
            saveFunc: "&"
        },
        templateUrl: '/ng/view/yky/loss/press/press-master-detail-directive.html',
        controller: pressMasterDetailDirectiveController
    };
};


angular.module("ykyPressModule")
    .directive("pressMasterDetailDirective", pressMasterDetailDirective);