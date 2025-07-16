var newLogisticLossController = function($controller, $scope, $http, commonSelectionOptions, commonDataSourceService, $state){

    // extend base controller
    $controller("ykyCommonLossController", {$scope: $scope});
    $controller("ykyWorkDurationsController", { $scope: $scope });
    $controller("ykyAttachmentsController", { $scope: $scope });
    $controller("ykyDiscardedPartsController", {$scope: $scope});
    $controller("ykyOtherCostsController", {$scope: $scope});

    $scope.suppliers = [];
    $scope.lossCodes = commonDataSourceService.getLossCodes();
    $scope.logisticsLossTypes = commonDataSourceService.getLogisticsLossTypes();
    $scope.logisticsAcceptencePoints = commonDataSourceService.getLogisticsAcceptencePoints();
    $scope.loss = {
        qty: 0,
    };

    $scope.modelSelectionOptions = commonSelectionOptions.modelOptions();
    $scope.partSelectionOptions = function(){
        return commonSelectionOptions.partOptions({
            cascade : function(e){
                if(this.value()) {
                    $http.post("/common/api/supplier-from-disegno", this.value()).success(function(data){
                        $scope.suppliers = data;
                    });
                }
            }
        });
    }


    // biz
    $scope.saveLogisticsLoss = function(){
        $scope.loss.workDurations = $scope.workDurations;
        $scope.loss.discardedParts = $scope.discardedParts;
        $scope.loss.otherCosts = $scope.otherCosts;

        $http.post("/loss/api/logistics/save", $scope.loss).success(function(data){
            if(data) {
                $state.go("losses.view", {id: data});
            } else {
                // todo handle error
            }
        });
    };

    // run validation for one time to display error messages at start
    setTimeout(function(){
        $scope.validator.validate();
    }, 1000);
};
newLogisticLossController.$inject = ["$controller", "$scope", "$http", "commonSelectionOptions", "commonDataSourceService", "$state"];

angular.module("ykyLossApp")
    .controller("newLogisticLossController", newLogisticLossController);
