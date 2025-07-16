/**
 * Created by t40127 on 17.03.2017.
 */


// in-complete and un-approved losses
var ykyUnApprovedLossesController = function($scope, $http){
    $scope.losses = [];

    $http.get("/production-loss/api/list-non-approved").success(function(data){
        $scope.losses = data;
    });
};
ykyUnApprovedLossesController.$inject = ["$scope", "$http"];

angular.module("ykyLossApp")
    .controller("ykyUnApprovedLossesController", ykyUnApprovedLossesController);