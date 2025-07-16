/**
 * Created by t40127 on 17.03.2017.
 */


var ykyLossesListLogisticsController = function($scope, $http){
    $scope.gridOptions = {
        autoBind: false,
        dataSource: {
            type: "json",
            transport: {
                read: function(options) {
                    $http.get("/common/api/loss-listing/logistics").success(function(data){
                        options.success(data);
                    });
                }
            },
            pageSize: 10
        },
        columns: [
            {
                field: "id",
                title: "{{'lossId'| translate}}",
                filterable: true
            }, {
                field: "description",
                title: "{{'description'| translate}}",
                filterable: true
            }, {
                field: "lossType",
                title: "{{'lossType'| translate}}",
                filterable: true
            }, {
                field: "lossDate",
                title: "{{'lossDate'| translate}}",
                filterable: true
            }, {
                field: "lossState",
                title: "{{'lossState'| translate}}",
                filterable: true
            }, {
                field: "supplierCode",
                title: "{{'supplierCode'| translate}}",
                filterable: true
            }, {
                field: "supplierName",
                title: "{{'supplierName'| translate}}",
                filterable: true
            }, {
                field: "qty",
                title: "{{'qty'| translate}}",
                filterable: true
            }, {
                field: "sqpNo",
                title: "{{'sqpNo'| translate}}",
                filterable: true
            }
        ]
    };

    $scope.searchParams = {};

    $scope.listLogisticsLoss = function(){
        $scope.logisticsLossList.dataSource.read();
    };
};
ykyLossesListLogisticsController.$inject = ["$scope", "$http"];


angular.module("ykyLossApp")
    .controller("ykyLossesListLogisticsController", ykyLossesListLogisticsController);