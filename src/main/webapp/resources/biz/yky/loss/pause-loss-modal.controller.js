/**
 * Created by t40127 on 17.03.2017.
 */


var ykyPauseLossModal = function($scope, $modalInstance, paused){
    $scope.ok = function(){
        $modalInstance.close();
    };

    $scope.cancel = function(){
        $modalInstance.dismiss();
    };

    $scope.isLossPaused = function () {
        return paused;
    };

};
ykyPauseLossModal.$inject = ["$scope", "$modalInstance", "paused"];

angular.module("ykyLossApp")
    .controller("ykyPauseLossModal", ykyPauseLossModal);