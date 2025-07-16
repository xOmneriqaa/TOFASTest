var sqpReportController = function($scope, $http, tfContextRootService, $translate, $q,
                                       commonDataSourceService, commonSelectionOptions){
//    $scope.lossStates = [];
    $scope.reportData = [];
    $scope.searchParams = {};

//    $http.get("/common/api/loss-states").success(function(data){
//        $scope.lossStates = data;
//    });

//    $scope.tutOptions = commonSelectionOptions.tutOptions();
    $scope.modelSelectionOptions = commonSelectionOptions.modelOptions();
//    $scope.lossSources = commonDataSourceService.getLossSources();
//    $scope.lossTypes = commonDataSourceService.getLossTypes();
    
    

   

        $scope.sqpData = new kendo.data.ObservableArray([]);

        var colDefs = [
        	{
            field: "sqp",
            title: "SQP",
            filterable: { multi: true, search: true },
            width: 200
        }, {
            field: "approvalInsBy",
            title: "Approval Ins. By",
            filterable: { multi: true, search: true },
            width: 100
        }, {
            field: "approvalDate",
            title: "Min. Approval Date",
            filterable: { multi: true, search: true },
            width: 100
        },{
            field: "supplierName",
            title: "Supplier Name",
            filterable: { multi: true, search: true },
            width: 100
        },{
            field: "costDetail",
            title: "Cost Detail",
            filterable: { multi: true, search: true },
            width: 100
        }, {
            field: "costItem",
            title: "Cost Item",
            filterable: { multi: true, search: true },
            width: 200
        }, {
            field: "costUnit",
            title: "Cost Unit",
            filterable: { multi: true, search: true },
            width: 80
        }, {
            field: "costSubItem",
            title: "Cost Sub Item",
            filterable: { multi: true, search: true },
            width: 200
        },  {
            field: "amount",
            title: "Amount",
            filterable: { multi: true, search: true },
            width: 100,
            template: '#: kendo.toString(amount, "0.00") #'
        }, 
        {
            field: "unit",
            title: "Unit",
            filterable: { multi: true, search: true },
            width: 100
        },{
            field: "unitCostInEuro",
            title: "Unit Cost In Euro",
            filterable: { multi: true, search: true },
            width: 150,
            template: '#: kendo.toString(unitCostInEuro, "0.00") #'
        },{
            field: "totalCost", aggregates: ["sum"], groupFooterTemplate: "TOTAL:  #=kendo.toString(sum, '0.00')#" ,
            title: "Total Cost In Euro",
            filterable: { multi: true, search: true },
            width: 200,
            template: '#: kendo.toString(totalCost, "0.00") #',
          
        }
        ,{
            field: "euroRate",
            title: "Euro Rate",
            filterable: { multi: true, search: true },
            width: 100
        },
       
//        {
//            field: "lossDate",
//            title: "Kayıp Tarihi",
//            filterable: { multi: true, search: true },
//            width: 120
//        },
       
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

        $scope.sqpReportGridOptions = {
            toolbar: [{name : "excel", text: "Excel"}],
            excel: {
                allPages: true,
                fileName: "YSKY - SQP_raporu.xlsx",
                filterable: true
            },
            autoBind: false,
            dataSource: {
                type: "json",
                data: $scope.sqpReportData,
                pageSize: 10,
                group: { field: "sqp",aggregates: [
                    { field: "totalCost", aggregate: "sum" },
                 ] },
//                aggregate: [ { field: "totalCost", aggregate: "sum" },]
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
        $http.post("/report/api/sqp-report", $scope.searchParams).success(function(data){
console.log("in searchReport function post success");
          //  var allPromises = [];
if(data == null){
	
	console.log("DATA NULL,loss-detail-report.js");
	console.log(data);
}else{
	console.log("DATA not NULL,loss-detail-report.js");
	console.log(data);
//	 for(var i = 0; i < data.length; i++) {
//         var val = data[i];
//         val.lossType = $translate.instant(val.lossType);
//         val.lossSubType = $translate.instant(val.lossSubType);
//
//         var lossStateTx = $translate.instant(val.lossState);
//         if(val.lossState == 'CANCELED'
//             && (val.lossStateChanger.length != 6 && val.lossStateChanger.charAt(0) != 'T')) {
//             lossStateTx += ' (Sistem)';
//         }
//         //TODO OBJECTION STATUS (Sistem) ekleme için aşağıdaki logic'i kurgulamak gerekiyor. 
//         //311 li id'de itiraz statüsü Accept Sistem olmalıydı,acik olarak geliyor. kurguda statü değiştirmiyor büyük ihtimal
//         //objectionState = ACCEPT && (val.lossStateChanger.length != 6 && val.lossStateChanger.charAt(0) != 'T')
//
//         val.lossState = lossStateTx;
//     }

     $scope.sqpData.splice(0, $scope.sqpData.length); // clear data
     $scope.sqpData.push.apply($scope.sqpData, data); // set data
     $scope.sqpReportGrid.dataSource.data($scope.sqpData);
	
}
           

            /*for(var i = 0; i < $scope.sqpReportGridOptions.columns.length; i++) {
             $scope.sqpReportGrid.autoFitColumn(i);
             }*/
        });

    };

};
sqpReportController.$inject = ["$scope", "$http", "tfContextRootService", "$translate", "$q",
    "commonDataSourceService", "commonSelectionOptions"];

angular.module("ykyReportsApp")
    .controller("sqpReportController", sqpReportController);