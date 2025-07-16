/**
 * Created by t40127 on 29.03.2017.
 */

var ykyPreviousInvoicesController = function($scope, $http, tfDownloadService){
    $scope.invoices = [];

    $http.get("/api/invoice/list-previous").success(function(data){
        $scope.invoices = data;
    });

    $scope.download = function(id){
        tfDownloadService.download("/api/invoice/get-previous/" + id, undefined);
    };
};
ykyPreviousInvoicesController.$inject = ["$scope", "$http", "tfDownloadService"];

angular.module("ykyReportsApp")
    .controller("ykyPreviousInvoicesController", ykyPreviousInvoicesController);