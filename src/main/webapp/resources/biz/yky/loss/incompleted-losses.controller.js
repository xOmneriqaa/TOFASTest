/**
 * Created by t40127 on 17.03.2017.
 */


var ykyInCompletedLossesController = function($scope, $http){
    $scope.losses = [];

    $http.get("/production-loss/api/list-in-completed").success(function(data){
        $scope.losses = data;
    });
};
ykyInCompletedLossesController.$inject = ["$scope", "$http"];

angular.module("ykyLossApp")
    .controller("ykyInCompletedLossesController", ykyInCompletedLossesController)