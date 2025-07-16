var ykyRefreeHomeController = function($scope, $http){
    $scope.totalObjected = 0;
    $scope.totalMyObjected = 0;

    $http.get("/refree/api/total-objected").success(function(data){
        $scope.totalObjected = data;
    });

    $http.get("/refree/api/total-my-objected").success(function(data){
        $scope.totalMyObjected = data;
    });
};
ykyRefreeHomeController.$inject = ["$scope", "$http"];


var ykyRefreeAllController = function($scope, $http){
    $scope.objections = [];

    $http.get("/refree/api/list-open-objections").success(function(data){
        $scope.objections = data;
    });
};
ykyRefreeAllController.$inject = ["$scope", "$http"];

var ykyRefreeMyController = function($scope, $http){
    $scope.objections = [];

    $http.get("/refree/api/list-open-my-objections").success(function(data){
        $scope.objections = data;
    });
};
ykyRefreeMyController.$inject = ["$scope", "$http"];

angular.module("ykRefreeControllers", [])
    .controller("ykyRefreeHomeController", ykyRefreeHomeController)
    .controller("ykyRefreeAllController", ykyRefreeAllController)
    .controller("ykyRefreeMyController", ykyRefreeMyController);
