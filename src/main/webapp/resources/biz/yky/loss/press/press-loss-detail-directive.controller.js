/**
 * Created by t40127 on 17.03.2017.
 */


var pressLossDetailDirectiveController = function($scope, commonSelectionOptions, commonDataSourceService){
    $scope.atrscParams = commonDataSourceService.getAtrscParams();

    $scope.partSelectionOptions = function(){
        return commonSelectionOptions.partOptions();
    };

    $scope.removeDetail = function(index){
        $scope.lossDetails.splice(index, 1);
    };

    $scope.atrscFieldOptions = {
        dataSource: commonDataSourceService.getAtrscParams()
    };

};
pressLossDetailDirectiveController.$inject = ["$scope", "commonSelectionOptions", "commonDataSourceService"];


angular.module("ykyPressModule")
    .controller("pressLossDetailDirectiveController", pressLossDetailDirectiveController);