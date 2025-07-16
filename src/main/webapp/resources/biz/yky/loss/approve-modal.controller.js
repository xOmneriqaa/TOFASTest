/**
 * Created by t40127 on 17.03.2017.
 */


var ykyApproveModalController = function($scope, $modalInstance, $http, disegno, lossType, willSqpOpen, commonDataSourceService){
    $scope.sqpNo = "";
    $scope.supplierNo = "";
    $scope.fieldVisible = lossType == "QUALITY_LAB" ? true : false;
    //$scope.sqpInputDisabled = lossType == "QUALITY_LAB" && willSqpOpen !== "X" ? true : false;
    // Artık tüm Kalite Lab kayıtlarında eski/yeni farketmeksizin SQP_NO sorulacak
    //$scope.sqpInputDisabled = lossType == "QUALITY_LAB" ? false : true;
    $scope.sqpInputDisabled = false;
    $scope.isSuppChainProject = false;
    $scope.suppliers = [];
    $scope.lossCodes = commonDataSourceService.getLossCodes();

    $http.post("/common/api/supplier-from-disegno", disegno).success(function(data){
        $scope.suppliers = data;
    });

    $scope.ok = function () {
        $modalInstance.close({
            sqpNo: $scope.sqpNo,
            supplierNo: $scope.supplierNo,
            isSuppChain: $scope.isSuppChainProject,
            lossCodeId: $scope.lossCodeId
        });
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };
};
ykyApproveModalController.$inject = ["$scope", "$modalInstance", "$http", "disegno", "lossType", "willSqpOpen", "commonDataSourceService"];

angular.module("ykyLossApp")
    .controller("ykyApproveModalController", ykyApproveModalController);