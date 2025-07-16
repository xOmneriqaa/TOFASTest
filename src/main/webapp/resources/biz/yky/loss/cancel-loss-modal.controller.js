/**
 * Created by t40127 on 17.03.2017.
 */


var ykyCancelLossModal = function($scope, $modalInstance){
    $scope.ok = function(){
        $modalInstance.close($scope.cancelDesc);
    };

    $scope.cancel = function(){
        $modalInstance.dismiss();
    };
};
ykyCancelLossModal.$inject = ["$scope", "$modalInstance"];

angular.module("ykyLossApp")
    .controller("ykyCancelLossModal", ykyCancelLossModal);