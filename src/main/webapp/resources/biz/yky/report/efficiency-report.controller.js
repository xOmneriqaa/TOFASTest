/**
 * Created by t40127 on 29.03.2017.
 */

var ykyEfficiencyController = function($scope, $http, $translate){
    $scope.searchParams = {};

    $scope.efficiencyReportGridOptions = {
        toolbar: [{name : "excel", text: "Excel"}],
        excel: {
            allPages: true,
            fileName: "YSKY - Tedarikçi Bazlı Rapor.xlsx",
            filterable: true
        },
        autoBind: false,
        dataSource: {
            type: "json",
            transport: {
                read: function(options) {
                    $http.post("/report/api/efficiency-report", $scope.searchParams).success(function(data){
                        for(var i = 0; i < data.length; i++) {
                            data[i].lossType = $translate.instant(data[i].lossType);
                            data[i].lossSubType = $translate.instant(data[i].lossSubType);
                        }
                        options.success(data);
                    });
                }
            },
            pageSize: 20
        },
        resizable: true,
        sortable: true,
        columns: [
            {
                field: "lossId",
                title: "Kayıp ID",
                width: 50
            }, {
                field: "lossType",
                title: "Kayıp Türü",
                width: 50,
                filterable: {
                    multi: true
                }
            }, {
                field: "lossSource",
                title: "Kayıp Kaynağı",
                width: 50,
                filterable: {
                    multi: true
                }
            }, {
                field: "lossDate",
                title: "Kayıp Tarihi",
                width: 100
            }, {
                field: "lossDescription",
                title: "Kayıp Açıklama",
                width: 400
            }, {
                field: "state",
                title: "Statü",
                width: 50
            }, {
                field: "stateDef",
                title: "Statü Açıklama",
                width: 100
            }, {
                field: "stateDate",
                title: "Statü Tarihi",
                width: 100
            }, {
                field: "tut",
                title: "TUT",
                width: 50
            }, {
                field: "shift",
                title: "Vardiya",
                width: 50
            }, {
                field: "duration",
                title: "Süre",
                width: 50
            }, {
                field: "finishDate",
                title: "Tamamlanma Tarihi",
                width: 100
            }, {
                field: "lossCode",
                title: "Kayıp Kodu",
                width: 50
            }, {
                field: "openDate",
                title: "Açılma Tarihi",
                width: 100
            }, {
                field: "approveDate",
                title: "Onaylanma Tarihi",
                width: 100
            }, {
                field: "invoiceDate",
                title: "Faturalanma Tarihi",
                width: 100
            }
        ]
    };

    $scope.search = function(){
        $scope.efficiencyReportGrid.dataSource.read();
    };
};
ykyEfficiencyController.$inject = ["$scope", "$http", "$translate"];

angular.module("ykyReportsApp")
    .controller("ykyEfficiencyController", ykyEfficiencyController);