/**
 * Created by t40127 on 17.03.2017.
 */


var newPressQualityLossController = function($scope, $http, $controller){
    $scope.isLogistics = false;
    $controller("pressCommonController", {$scope: $scope});
};
newPressQualityLossController.$inject = ["$scope", "$http", "$controller"];

angular.module("ykyPressModule")
    .controller("newPressQualityLossController", newPressQualityLossController);