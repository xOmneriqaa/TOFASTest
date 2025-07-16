var newQualityLabLossController = function($controller, $scope, $http, commonSelectionOptions, commonDataSourceService, $state){

    // extend base controller
    $controller("ykyCommonLossController", {$scope: $scope});
    $controller("ykyWorkDurationsController", { $scope: $scope });
    $controller("ykyAttachmentsController", { $scope: $scope });

    $scope.suppliers = [];
    $scope.approverRoles = [];
    $scope.qualityLabTestInfos = commonDataSourceService.getQualityLabTests();
    $scope.qualityLabTests = [];

    $scope.loss = {
        qty: 0,
        //willSqpOpen: false
    };

    $scope.getApproverRoles = function(){
        $http.get("/loss/api/approver-roles").success(function(data){
            for(var i = 0; i<data.length; i++){
                var name = ""
                if(data[i].approverRole.includes("DESK")) name = "Montaj Masası"
                else if(data[i].approverRole.includes("SAMPLE")) name = "Numune Masası"
                else if(data[i].approverRole.includes("AFTERSALES")) name = "Satış Sonrası Masası"
                else if(data[i].approverRole.includes("SUSPENSION")) name = "Süspansiyon Masası"
                else if(data[i].approverRole.includes("BODY")) name = "Gövde Masası"
                data[i] = { name:name, id:data[i].id}
            }
            $scope.approverRoles = data;
        });
    }

    $scope.getApproverRoles();

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
    };


    // biz
    $scope.addNewQualityLabTest = function(){
        $scope.qualityLabTests.push({
            qty: 1,
            testId: 0
        });
    };

    $scope.removeQualityLabTest = function(i){
        $scope.qualityLabTests.splice(i, 1);
    };

    $scope.getQualityTestPrice = function(index){
        if(index != undefined) {
            var idVal = $scope.qualityLabTests[index].testId;
            for(var i = 0; i < $scope.qualityLabTestInfos.length; i++) {
                if($scope.qualityLabTestInfos[i].id == idVal) {
                    return $scope.qualityLabTestInfos[i].price * $scope.qualityLabTests[index].qty;
                }
            }
        }

        return 0;
    };

    $scope.getQualityLabTestCost = function(){
        var totalCost = 0;
        angular.forEach($scope.qualityLabTests, function(val, i){
            totalCost += $scope.getQualityTestPrice(i);
        });

        return totalCost;
    };


    $scope.saveQualityLabLoss = function(){
        $scope.loss.workDurations = $scope.workDurations;
        $scope.loss.qualityLabTests = $scope.qualityLabTests;

        //$scope.loss.willSqpOpen = $scope.loss.willSqpOpen ? "X" : " ";

        $http.post("/loss/api/quality-lab/save", $scope.loss).success(function(data){
            if(data) {
                $state.go("losses.view", {id: data});
            } else {
                // todo handle error
            }
        });
    };


    $scope.validate = function(){
        // run validation for one time to display error messages at start
        setTimeout(function(){
            $scope.validator.validate();
        }, 1000);
    };

    $scope.validate();

};
newQualityLabLossController.$inject = ["$controller", "$scope", "$http", "commonSelectionOptions", "commonDataSourceService", "$state"];

angular.module("ykyLossApp")
    .controller("newQualityLabLossController", newQualityLabLossController);
