/**
 * Created by t40127 on 29.03.2017.
 */


var discardedPartReportController = function ($scope, $http, commonDataSourceService, $translate) {

    $scope.searchParams = {};
    $scope.lossStates = [];
    $scope.lossTypes = commonDataSourceService.getLossTypes();
    $scope.subTypes = [];
    
    $http.get("/common/api/loss-states").success(function(data){
        $scope.lossStates = data;
    });
    $http.get("/common/api/production-sub-loss-types").success(function(data){
        $scope.subTypes = data;
    });
   

    $scope.searchReport = function () {
        $scope.discardedPartsGrid.dataSource.read();
    };


    $scope.discardedPartsGridOptions = {
        toolbar: [{name : "excel", text: "Excel"}],
        excel: {
            allPages: true,
            fileName: "YSKY - Iskarta Parca Rapor.xlsx",
            filterable: true
        },
        autoBind: false,
        dataSource: {
            transport: {
                read: function(options) {
                    $http.post("/report/api/discarded-part-report", $scope.searchParams).success(function(data){
                        for(var i = 0; i < data.length; i++) {
                            data[i]["lossType"] = $translate.instant(data[i]["lossType"]);
                            data[i]["lossState"] = $translate.instant(data[i]["lossState"]);
                            data[i]["subType"] = $translate.instant(data[i]["subType"]);
                        }
                        options.success(data);
                    });
                }
            },
            pageSize: 20
        },
        resizable: true,
        groupable: true,
        pageable: {
            pageSizes: true
        },
        filterable: true,
        columnMenu: true,
        reorderable: true,
        columns: [/*{
            field: "id",
            title: "ID",
            width: 60,
            attributes: {
                style: "text-align: right;"
            }
        }, */{
            field: "disegno",
            title: "Resim No",
            width: 140
        }, {
            field: "discardVoucherNo",
            title: "Iskarta Fiş Numarası",
            width: 140
        }, {
            field: "qty",
            title: "Iskarta Parça Adedi",
            width: 80
        }, {
            field: "discardCost",
            title: "Iskarta Tutarı",
            template: "#= kendo.toString(discardCost, '0.00') # \u20BA",
            attributes: {
                style: "text-align: right;"
            },
            width: 100
        }, {
            field: "lossId",
            title: "Kayip ID",
            attributes: {
                style: "text-align: right;"
            },
            width: 100
        }, {
            field: "lossDate",
            title: "Kayıp Tarihi",
            width: 100
        }, {
            field: "lossInsDate",
            title: "Kayıp Giriş Tarihi",
            width: 140
        }, {
            field: "lossState",
            title: "Kayıp Durumu",
            width: 100
        }, {
            field: "lastStateChanges",
            title: "Son Statü Değişikliği",
            width: 140
        }, {
            field: "lossType",
            title: "Kayıp Türü",
            width: 100
        }, {
            field: "description",
            title: "Kayıp Açıklama",
            width: 180
        },{
        	field: "subType",
        	title: "Kayıp Birimi",
        	width: 180
        }]
    };

};

discardedPartReportController.$inject = ["$scope", "$http", "commonDataSourceService", "$translate"];



angular.module("ykyReportsApp")
    .controller("discardedPartReportController", discardedPartReportController);