/**
 * Created by t40127 on 29.03.2017.
 */

var lossDetailReportController = function($scope, $http, tfContextRootService, $translate, $q,
                                       commonDataSourceService, commonSelectionOptions){
    $scope.lossStates = [];
    $scope.reportData = [];
    $scope.searchParams = {};

    $http.get("/common/api/loss-states").success(function(data){
        $scope.lossStates = data;
    });

    $scope.tutOptions = commonSelectionOptions.tutOptions();
    $scope.modelSelectionOptions = commonSelectionOptions.modelOptions();
    $scope.lossSources = commonDataSourceService.getLossSources();
    $scope.lossTypes = commonDataSourceService.getLossTypes();
    
    console.log($scope.lossTypes);

    $scope.supplierSelectOptions = function(){
        return {
            dataSource: {
                data: commonDataSourceService.getSuppliers(),
            },
            dataTextField: "name",
            dataValueField: "sapCode",
            template: "#:data.sapCode# - #:data.name#",
            filter: "contains",
            optionLabel: "..."
        };
    };

        $scope.lossDetailData = new kendo.data.ObservableArray([]);

        var colDefs = [{
            field: "id",
            title: "ID",
            width: 80,
            template: function(data){
                return '<a href="' + tfContextRootService.getUrl('/#/losses/view/' + data.id )
                    + '" target="_blank">' + data.id + '</a>';
            }
        },{
            field: "lossType",
            title: "Kayıp Türü",
            filterable: { multi: true, search: true },
            width: 100
        }, {
            field: "lossSubType",
            title: "Kayıp Birimi",
            filterable: { multi: true, search: true },
            width: 100
        }, {
            field: "description",
            title: "Kayıp Açıklaması",
            filterable: { multi: true, search: true },
            width: 200
        }, {
            field: "supplierCode",
            title: "Tedarikçi Kodu",
            filterable: { multi: true, search: true },
            width: 80
        }, {
            field: "supplierName",
            title: "Tedarikçi Adı",
            filterable: { multi: true, search: true },
            width: 200
        },  {
            field: "sqpNo",
            title: "SQP No",
            filterable: { multi: true, search: true },
            width: 200
        }, 
//        {
//            field: "lossDate",
//            title: "Kayıp Tarihi",
//            filterable: { multi: true, search: true },
//            width: 120
//        },
        {
            field: "stepNameCached",
            title: "Step Name Cached",
            filterable: { multi: true, search: true },
            width: 150
        }, {
            field: "blueCollarWageCached",
            title: "blueCollarWageCached",
            filterable: { multi: true, search: true },
            width: 150
        },  {
            field: "qty",
            title: "Adet",
            filterable: { multi: true, search: true },
            width: 150
        },{
            field: "stepDurationCached",
            title: "stepDurationCached",
            filterable: { multi: true, search: true },
            width: 180
        }
        ];
//
//
//        angular.forEach(data, function(val, i){
//            colDefs.push({
//                field: 'durationDetails["' + val + '"]',
//                title: val + " (dk)",
//                sorting: true,
//                aggregates: [ "sum"],
//                groupFooterTemplate: "#= sum #",
//                width: 100,
//                attributes: {
//                    style: "text-align: right;"
//                }
//            });
//        });
//
//        colDefs.push({
//            field: "totalDuration",
//            title: "Toplam Süre (dk)",
//            aggregates: [ "sum"],
//            groupFooterTemplate: "#= sum #",
//            width: 100,
//            attributes: {
//                style: "text-align: right;"
//            }
//        });
//
//
//
//        colDefs.push({
//            field: "qLabTestCost",
//            title: "Kalite Lab. Tutar",
//            template: "#= kendo.toString(qLabTestCost, '0.00') # \u20BA",
//            aggregates: [ "sum"],
//            groupFooterTemplate: "#= kendo.toString(sum, '0.00') # \u20BA",
//            width: 100,
//            attributes: {
//                style: "text-align: right;"
//            }
//        });
//
//        colDefs.push({
//            field: "discardedPartCost",
//            title: "Iskarta Parça Tutar",
//            template: "#= kendo.toString(discardedPartCost, '0.00') # \u20BA",
//            aggregates: [ "sum"],
//            groupFooterTemplate: "#= kendo.toString(sum, '0.00') # \u20BA",
//            width: 100,
//            attributes: {
//                style: "text-align: right;"
//            }
//        });
//
//
//        angular.forEach(data, function(val, i){
//
//            colDefs.push({
//                field: 'costDetails["' + val  +'"]',
//                title: val + " \u20BA",
//                sorting: true,
//                template: "#= kendo.toString(costDetails['" + val  +"'], '0.00') # \u20BA",
//                aggregates: [ "sum"],
//                groupFooterTemplate: "#= kendo.toString(sum, '0.00') # \u20BA",
//                width: 100,
//                attributes: {
//                    style: "text-align: right;"
//                }
//            });
//        });
//
//
//
//        colDefs.push({
//            field: "totalCostOfWorkTypes",
//            title: "Toplam Çalışma Türleri Tutarı \u20BA",
//            template: "#= kendo.toString(totalCostOfWorkTypes, '0.00') # \u20BA",
//            aggregates: [ "sum"],
//            groupFooterTemplate: "#= kendo.toString(sum, '0.00') # \u20BA",
//            width: 100,
//            attributes: {
//                style: "text-align: right;"
//            }
//        });
//
//        colDefs.push({
//            field: "totalCost",
//            title: "Toplam Tutar \u20BA",
//            template: "#= kendo.toString(totalCost, '0.00') # \u20BA",
//            aggregates: [ "sum"],
//            groupFooterTemplate: "#= kendo.toString(sum, '0.00') # \u20BA",
//            width: 100,
//            attributes: {
//                style: "text-align: right;"
//            }
//        });

        $scope.lossDetailReportGridOptions = {
            toolbar: [{name : "excel", text: "Excel"}],
            excel: {
                allPages: true,
                fileName: "YSKY - Kayip Rapor Detay.xlsx",
                filterable: true
            },
            autoBind: false,
            dataSource: {
                type: "json",
                data: $scope.lossDetailReportData,
                pageSize: 5
            },
            resizable: true,
            groupable: true,
            reorderable: true,
            pageable: {
                pageSizes: true
            },
            filterable: true,
            columnMenu: true,
            columns: colDefs
        };

   

    $scope.searchReport = function(){
    	console.log("in searchReport function");
        $http.post("/report/api/loss-detail-report", $scope.searchParams).success(function(data){
console.log("in searchReport function post success");
          //  var allPromises = [];
if(data == null){
	
	console.log("DATA NULL,loss-detail-report.js");
	console.log(data);
}else{
	console.log("DATA not NULL,loss-detail-report.js");
	console.log(data);
	 for(var i = 0; i < data.length; i++) {
         var val = data[i];
         val.lossType = $translate.instant(val.lossType);
         val.lossSubType = $translate.instant(val.lossSubType);

         var lossStateTx = $translate.instant(val.lossState);
         if(val.lossState == 'CANCELED'
             && (val.lossStateChanger.length != 6 && val.lossStateChanger.charAt(0) != 'T')) {
             lossStateTx += ' (Sistem)';
         }
         //TODO OBJECTION STATUS (Sistem) ekleme için aşağıdaki logic'i kurgulamak gerekiyor. 
         //311 li id'de itiraz statüsü Accept Sistem olmalıydı,acik olarak geliyor. kurguda statü değiştirmiyor büyük ihtimal
         //objectionState = ACCEPT && (val.lossStateChanger.length != 6 && val.lossStateChanger.charAt(0) != 'T')

         val.lossState = lossStateTx;
     }

     $scope.lossDetailData.splice(0, $scope.lossDetailData.length); // clear data
     $scope.lossDetailData.push.apply($scope.lossDetailData, data); // set data
     $scope.lossDetailReportGrid.dataSource.data($scope.lossDetailData);
	
}
           

            /*for(var i = 0; i < $scope.lossDetailReportGridOptions.columns.length; i++) {
             $scope.lossDetailReportGrid.autoFitColumn(i);
             }*/
        });

    };

};
lossDetailReportController.$inject = ["$scope", "$http", "tfContextRootService", "$translate", "$q",
    "commonDataSourceService", "commonSelectionOptions"];

angular.module("ykyReportsApp")
    .controller("lossDetailReportController", lossDetailReportController);