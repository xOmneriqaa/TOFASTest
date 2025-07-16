/**
 * Created by t40127 on 17.03.2017.
 */


var ykyObjectionModalController = function($scope, $modalInstance, loss, objectionTypes){
    $scope.loss = loss;
    $scope.objectionTypes = objectionTypes;
    $scope.objectionResult = {};
    $scope.newObjection = {};

    // remove current objection
    var currentObjectionIndex = -1;
    for(var i = 0; i < $scope.objectionTypes.length; i++) {
        if($scope.objectionTypes[i].id == $scope.loss.currentObjection.objectionType.id) {
            currentObjectionIndex = i;
            break;
        }
    }

    if(currentObjectionIndex >= 0) {
        $scope.objectionTypes.splice(currentObjectionIndex, 1);
    }

    $scope.changeObjectionType = function(){
        var resultObj = {
            isChange : true,
            newObjection: $scope.newObjection
        };
        $modalInstance.close(resultObj);
    };

    $scope.saveResult = function(){
        var resultObj = {
            isChange : false,
            objectionResult: $scope.objectionResult
        };
        $modalInstance.close(resultObj);
    };


    $scope.cancel = function(){
        $modalInstance.dismiss("canceled");
    };
};
ykyObjectionModalController.$inject = ["$scope", "$modalInstance", "loss", "objectionTypes"];

angular.module("ykyLossApp")
    .controller("ykyObjectionModalController", ykyObjectionModalController);