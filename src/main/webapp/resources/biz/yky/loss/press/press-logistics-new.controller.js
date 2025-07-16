/**
 * Created by t40127 on 17.03.2017.
 */


var newPressLogisticsLossController = function($scope, $http, $controller){
    $scope.isLogistics = true;
    $controller("pressCommonController", {$scope: $scope});
};
newPressLogisticsLossController.$inject = ["$scope", "$http", "$controller"];


angular.module("ykyPressModule")
    .controller("newPressLogisticsLossController", newPressLogisticsLossController);