/**
 * Created by t40127 on 17.03.2017.
 */


var ykyPressOpenLossesController = function($scope, $http){
    $scope.openLosses = [];

    $http.get("/loss/press/api/list-open").success(function(data){
        $scope.openLosses.push.apply($scope.openLosses, data);
    });

};
ykyPressOpenLossesController.$inject = ["$scope", "$http"];


angular.module("ykyPressModule")
    .controller("ykyPressOpenLossesController", ykyPressOpenLossesController)