/**
 * Created by t40127 on 17.03.2017.
 */


var editPressController = function($stateParams, $controller, $scope, $http, $state){
    $scope.loss = {};
    $controller("pressCommonController", {$scope: $scope});

    $http.get(("/loss/api/press/get/" + $stateParams.id)).success(function(data){
        $scope.loss = data;
    });

    $scope.easyUpdateDetails = function(){
        var easyUpdateUrl = undefined;
        if($scope.loss.pressLossType == 'LOGISTICS') {
            easyUpdateUrl = "/loss/press/api/easy-update-details-l";
        } else if($scope.loss.pressLossType == 'QUALITY') {
            easyUpdateUrl = "/loss/press/api/easy-update-details-q";
        }

        if(easyUpdateUrl) {
            $http.post(easyUpdateUrl, $scope.loss.details).success(function(data){
                $scope.loss.details.splice(0, $scope.loss.details.length);
                $scope.loss.details.push.apply($scope.loss.details, data);
            });
        }
    };

    $scope.saveDetails = function(){
        var easyUpdateUrl = undefined;
        if($scope.loss.pressLossType == 'LOGISTICS') {
            easyUpdateUrl = "/loss/press/api/save-details-l";
        } else if($scope.loss.pressLossType == 'QUALITY') {
            easyUpdateUrl = "/loss/press/api/save-details-q";
        }

        if(easyUpdateUrl) {
            $http.post(easyUpdateUrl, $scope.loss.details).success(function(data){
                $state.go("losses.view", {id: $scope.loss.id}, {reload: true});
            });
        }
    };

};
editPressController.$inject = ["$stateParams", "$controller", "$scope", "$http", "$state"];

angular.module("ykyPressModule")
    .controller("editPressController", editPressController);