/**
 * Created by t40127 on 17.03.2017.
 */


var tfPressParametersController = function($scope, $http){
    $scope.pressParameters = {};
    $scope.numberFieldOptions = {
        min: 0,
        decimals: 5,
        format: "n5"
    };

    $http.get("/loss/press/api/get-parameters").success(function(data){
        $scope.pressParameters = data;
    });


    $scope.save = function(){
        $http.post("/loss/press/api/set-parameters", $scope.pressParameters).success(function(data){
            if(data) {
                $scope.reload();
            }
        });
    };

};
tfPressParametersController.$inject = ["$scope", "$http"];


angular.module("ykyAdminApp")

    .controller("tfPressParametersController", tfPressParametersController);