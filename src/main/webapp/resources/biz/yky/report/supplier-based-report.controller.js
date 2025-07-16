/**
 * Created by t40127 on 29.03.2017.
 */

var ykySupplierBasedReportController = function($scope, $http, tfContextRootService){

    $scope.searchParams = {};
    $scope.workTypes = [];
    $scope.supplierTypesOptions = {
        dataSource: {
            type: "json",
            transport: {
                read: {
                    url: tfContextRootService.getUrl("/report/api/supplier-types"),
                }
            }
        }
    };

    $http.get("/report/api/get-work-types").success(function(data){
        $scope.workTypes = data;

        var colDefs = [
            {
                field: "supplierCode",
                title: "Tedarikçi Kodu",
                filterable: { multi: true },
                width: 100
            }, {
                field: "supplierName",
                title: "Tedarikçi",
                filterable: { multi: true }
            },{
                field: "lossIds",
                title: "Kayıp ID'leri",
                filterable: false
            }, {
                field: "totalDuration",
                title: "Süre Detayı",
                width: 100,
                columns: []
            },{
                field: "totalCost",
                title: "Tutar Detayı",
                template: "#= kendo.toString(totalCost, '0.00') # \u20BA",
                width: 100,
                columns : [
                    {
                        field: "totalCost",
                        title: "Çalışma Türleri",
                        columns: []
                    }, {
                        field: "qLabTestCost",
                        title: "Kalite Lab. Tutarı",
                        template: "#= kendo.toString(qLabTestCost, '0.00') # \u20BA",
                    }, {
                        field: "discardedPartCost",
                        title: "Iskarta Parça Tutarı",
                        template: "#= kendo.toString(discardedPartCost, '0.00') # \u20BA",
                    }
                ]
            }
        ];

        angular.forEach(data, function(val, i){
            colDefs[3].columns.push({
                field: 'durationDetails["' + val + '"]',
                title: val,
                sorting: true
            });

            colDefs[4].columns[0].columns.push({
                field: 'costDetails["' + val  +'"]',
                title: val,
                sorting: true,
                template: "#= kendo.toString(costDetails['" + val  +"'], '0.00') # \u20BA",
            });
        });


        colDefs[3].columns.push({
            field: "totalDuration",
            title: "Toplam Süre",
        });

        colDefs[4].columns[0].columns.push({
            field: "totalCostOfWorkTypes",
            title: "Toplam Çalışma Türleri Tutarı",
            template: "#= kendo.toString(totalCostOfWorkTypes, '0.00') # \u20BA",
        });

        colDefs[4].columns.push({
            field: "totalCost",
            title: "Toplam Tutar",
            template: "#= kendo.toString(totalCost, '0.00') # \u20BA",
        });

        $scope.supplierBasedGridOptions = {
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
                        $http.post("/report/api/supplier-based-report", $scope.searchParams).success(function(data){
                            options.success(data);
                        });
                    }
                },
                pageSize: 15
            },
            resizable: true,
            sortable: true,
            columns: colDefs
        };
    });



    $scope.searchReport = function(){
        $scope.supplierBasedGrid.dataSource.read();
    };


};
ykySupplierBasedReportController.$inject = ["$scope", "$http", "tfContextRootService"];

angular.module("ykyReportsApp")
    .controller("ykySupplierBasedReportController", ykySupplierBasedReportController);