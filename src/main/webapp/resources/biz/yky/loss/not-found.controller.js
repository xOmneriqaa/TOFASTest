/**
 * Created by t40127 on 17.03.2017.
 */


var ykyLossesNotFoundController = function($scope, $stateParams){
    $scope.lossId = $stateParams.id;
};
ykyLossesNotFoundController.$inject = ["$scope", "$stateParams"];

angular.module("ykyLossApp")
    .controller("ykyLossesNotFoundController", ykyLossesNotFoundController);