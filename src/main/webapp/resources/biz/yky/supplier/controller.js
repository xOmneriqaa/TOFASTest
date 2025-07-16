var ykySupplierOpenLogisticsController = function($scope, $http){
    $scope.openLosses = [];

    $http.get("/supplier/api/open/logistics").success(function(data){
        $scope.openLosses = data;
    });

};
ykySupplierOpenLogisticsController.$inject = ["$scope", "$http"];

var ykySupplierOpenProductionsController = function($scope, $http){
    $scope.openLosses = [];

    $http.get("/supplier/api/open/production").success(function(data){
        $scope.openLosses = data;
    });

};
ykySupplierOpenProductionsController.$inject = ["$scope", "$http"];

var ykySupplierOpenQualityController = function($scope, $http){
    $scope.openLosses = [];

    $http.get("/supplier/api/open/quality").success(function(data){
        $scope.openLosses = data;
    });

};
ykySupplierOpenQualityController.$inject = ["$scope", "$http"];

var ykySupplierOpenQualityLabController = function($scope, $http){
    $scope.openLosses = [];

    $http.get("/supplier/api/open/quality-lab").success(function(data){
        $scope.openLosses = data;
    });

};
ykySupplierOpenQualityLabController.$inject = ["$scope", "$http"];


angular.module("ykySupplierControllers", [])
    .controller("ykySupplierOpenLogisticsController", ykySupplierOpenLogisticsController)
    .controller("ykySupplierOpenProductionsController", ykySupplierOpenProductionsController)
    .controller("ykySupplierOpenQualityController", ykySupplierOpenQualityController)
    .controller("ykySupplierOpenQualityLabController", ykySupplierOpenQualityLabController);
