/**
 * Created by t40127 on 17.03.2017.
 */

var pressMasterDetailDirectiveController = function($scope, commonDataSourceService){
    $scope.pressSuppliers = commonDataSourceService.getPressSuppliers();
    $scope.firmTypes = commonDataSourceService.getFirmTypes();

    $scope.getTotalCost = function(){
        var totalCost = 0;

        for(var i = 0; i < $scope.lossDetails.length; i++) {
            totalCost += $scope.lossDetails[i]._totalCost;
        }

        return totalCost;
    };
};
pressMasterDetailDirectiveController.$inject = ["$scope", "commonDataSourceService"];

angular.module("ykyPressModule")
    .controller("pressMasterDetailDirectiveController", pressMasterDetailDirectiveController);