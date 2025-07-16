/**
 * Created by t40127 on 17.03.2017.
 */


var ykyPressApproveModalController = function($modalInstance, $scope, loss){
    $scope.loss = loss;

    $scope.approve = function(){
        $modalInstance.close(loss);
    };

    $scope.cancel = function(){
        $modalInstance.dismiss();
    };

    setTimeout(function(){
        $scope.validator.validate();
    }, 300);

};
ykyPressApproveModalController.$inject = ["$modalInstance", "$scope", "loss"];

angular.module("ykyPressModule")
    .controller("ykyPressApproveModalController", ykyPressApproveModalController);