var ykyBuyerUnPricedPartsController = function($scope, $http){
    $scope.unPricedParts = [];

    $http.get("/buyer/api/d-parts-wo-base-price").success(function(data){
        $scope.unPricedParts = data;
    });

};
ykyBuyerUnPricedPartsController.$inject = ["$scope", "$http"];

var ykyBuyerAddPricePartController = function($scope, $stateParams, $http, $state, tfContextRootService){
    $scope.suppliers = [];
    $http.get("/buyer/api/get-discard-part/" + $stateParams.id).success(function(data){
        $scope.discardPart = data;
    });

    $scope.supplierSelectOptions = {
        dataSource: {
            transport: {
                read: {
                    dataType: "json",
                    url: tfContextRootService.getUrl("/buyer/api/get-suppliers"),
                }
            }
        },
        dataTextField: "name",
        dataValueField: "sapCode",
        template: "#:data.sapCode# - #:data.name#",
        filter: "contains",
    };


    $scope.addPrice = function(){
        $scope.basePrice.id = $stateParams.id;
        $http.post("/buyer/api/save-discard-part", $scope.basePrice).success(function(data){
            if(data) {
                $state.go("buyer.unPricedParts", {}, {reload: true});
            }
        });
    };
};
ykyBuyerAddPricePartController.$inject = ["$scope", "$stateParams", "$http", "$state", "tfContextRootService"];


angular.module("ykyBuyerControllers", [])
    .controller("ykyBuyerUnPricedPartsController", ykyBuyerUnPricedPartsController)
    .controller("ykyBuyerAddPricePartController", ykyBuyerAddPricePartController);
