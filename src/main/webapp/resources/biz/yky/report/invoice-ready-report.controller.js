/**
 * Created by t40127 on 29.03.2017.
 */

var ykyInvoiceReadyReportController = function($scope, $http, tfDownloadService){
    $scope.losses = [];



    $scope.selectAll = function(){
        angular.forEach($scope.losses, function(val, i){
            val.checked = true;
        });
    };
    $scope.selectNone = function(){
        angular.forEach($scope.losses, function(val, i){
            val.checked = false;
        });
    };

    $scope.refreshInvoices = function(){
        $http.get("/report/api/invoice-ready-losses").success(function(data){
            $scope.losses = [];
            angular.forEach(data, function(val, i){
                val.checked = false;
                $scope.losses.push(val);
            });
        });
    };

    $scope.getInvoice = function(){
        var selectedIds = [];
        angular.forEach($scope.losses, function(val, i){
            if(val.checked) {
                selectedIds = selectedIds.concat(val.lostIds);
            }
        });
        tfDownloadService.download("/api/invoice/get-invoices", selectedIds);
    };

    $scope.refreshInvoices();
};
ykyInvoiceReadyReportController.$inject = ["$scope", "$http", "tfDownloadService"];

angular.module("ykyReportsApp")
    .controller("ykyInvoiceReadyReportController", ykyInvoiceReadyReportController);