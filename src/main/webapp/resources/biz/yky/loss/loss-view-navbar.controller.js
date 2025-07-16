/**
 * Created by t40127 on 17.03.2017.
 */


var ykyLossViewNavbarController = function($scope, $state){
    $scope.viewLoss = function (){
        if($scope.lossId) {
            $state.go("losses.view", {id: $scope.lossId}, {reload: true});
        }
    };
};
ykyLossViewNavbarController.$inject = ["$scope", "$state"];


angular.module("ykyLossApp")
    .controller("ykyLossViewNavbarController", ykyLossViewNavbarController);