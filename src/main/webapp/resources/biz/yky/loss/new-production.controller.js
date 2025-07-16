var newProductionLossController = function($controller, $scope, $http, commonSelectionOptions, commonDataSourceService, $state){

    // extend base controller
    $controller("ykyCommonLossController", {$scope: $scope});
    $controller("ykyWorkDurationsController", { $scope: $scope });
    $controller("ykyAttachmentsController", { $scope: $scope });
    $controller("ykyDiscardedPartsController", {$scope: $scope});
    $controller("ykyOtherCostsController", {$scope: $scope});

    $scope.blueCollarWage = function(){
        return commonDataSourceService.getBlueCollarWage();
    };

    $scope.lossTypes = commonDataSourceService.getProductionSubLossTypes();
    $scope.stepDefs = [];
    $scope.lossSources = commonDataSourceService.getLossSources();
    $scope.shiftCodes = [];

    $scope.loss = {
        qty: 0,
        steps: []
    };

    $scope.getLogisticsTypeId = function(){
        for(var i = 0; i < $scope.lossSources.length; i++) {
            if($scope.lossSources[i].shortName == 'L') {
                return $scope.lossSources[i].id;
            }
        }

        return undefined;
    };


    $scope.modelSelectionOptions = commonSelectionOptions.modelOptions();
    $scope.partSelectionOptions = function(){
        return commonSelectionOptions.partOptions({
            change : function(){
                $scope.invalidateSteps();
            }
        });
    }
    $scope.tutOptions = commonSelectionOptions.tutOptions();
    $scope.effectedTutOptions = commonSelectionOptions.tutOptions();

    $scope.modelSelectionOptions.change = function() {
        $scope.invalidateSteps();
    };

    $scope.tutOptions.change = function(){
        $scope.invalidateSteps();
        $scope.updateShiftCodes();
    };


    // biz

    $scope.getTotalCostOfSteps = function(){
        var cost = 0;
        angular.forEach($scope.loss.steps, function(val, i){
            if(val.selected) {
                cost += val.getCost();
            }
        });
        return cost;
    };

    $scope.getTotalTimeOfSteps = function(){
        var time = 0;
        angular.forEach($scope.loss.steps, function(val, i){
            if(val.selected) {
                time += val.getTime();
            }
        });
        return time;
    };

    $scope.isStepsValid = function(){
        return $scope.loss && $scope.loss.steps && $scope.loss.steps.length > 0;
    };

    $scope.invalidateSteps = function() {
        $scope.loss.steps = [];
    };

    $scope.updateShiftCodes = function(){
        $http.get(("/common/api/shift-codes/" + $scope.loss.tut)).success(function(data){
            $scope.shiftCodes = data;
        });
    };

    $scope.updateSteps = function(){
        var lossType = $scope.loss.lossType;
        var disegno = $scope.loss.disegno;
        var model = $scope.loss.model;
        var tut = $scope.loss.tut;
        if(disegno && model && tut) {
            $http.get(("/common/api/related-steps/" + lossType + "/" + model + "/" + disegno + "/" + tut)).success(function(data){
                $scope.loss.steps = [];
                for(var i = 0; i < data.length; i++) {
                    var stepInfo = {};
                    angular.copy(data[i], stepInfo);
                    stepInfo.selected = false;
                    stepInfo.qty = $scope.loss.qty;
                    stepInfo.getTime = function(){
                        return this.qty * this.stepDuration;
                    };
                    stepInfo.getCost = function(){
                        return this.getTime() * $scope.blueCollarWage() / 60;
                    };
                    $scope.loss.steps.push(stepInfo);

                }
            });
        }

    };

    $scope.saveProductionLoss = function(){
        var stepInfo = $scope.loss.steps;
        var serverStepInfo = [];
        for(var i = 0; i < stepInfo.length; i++) {
            if(stepInfo[i].selected) {
                serverStepInfo.push({
                    stepId: stepInfo[i].stepId,
                    durationId: stepInfo[i].durationId,
                    qty: stepInfo[i].qty,
                    standartStep: stepInfo[i].standartStep
                });
            }
        }

        var serverLoss = {};
        angular.copy($scope.loss, serverLoss);
        serverLoss.steps = serverStepInfo;
        console.log("serverLoss: "+serverLoss);
        serverLoss.discardedParts = $scope.discardedParts;

        $scope.loss.otherCosts = $scope.otherCosts;
        serverLoss.otherCosts	= $scope.otherCosts;

        $http.post("/loss/api/production/save", serverLoss).success(function(data){
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
newProductionLossController.$inject = ["$controller", "$scope", "$http", "commonSelectionOptions", "commonDataSourceService", "$state"];

angular.module("ykyLossApp")
    .controller("newProductionLossController", newProductionLossController);
