/**
 * Created by t40127 on 29.03.2017.
 */

var ykyMainReportController = function($scope, $http, tfContextRootService, $translate, $q,
                                       commonDataSourceService, commonSelectionOptions){
    $scope.lossStates = [];
    $scope.reportData = [];
    $scope.searchParams = {page: 0, pageSize:5};


    $http.get("/common/api/loss-states").success(function(data){
        $scope.lossStates = data;
    });

    $scope.tutOptions = commonSelectionOptions.tutOptions();
    $scope.modelSelectionOptions = commonSelectionOptions.modelOptions();
    $scope.lossSources = commonDataSourceService.getLossSources();
    $scope.approverRoles = commonDataSourceService.getApproverRoles();
    $scope.lossTypes = commonDataSourceService.getLossTypes();

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

    $scope.workTypes = [];

    $http.get("/report/api/get-work-types").success(function(data) {
        $scope.workTypes = data;

        $scope.mainReportData = new kendo.data.ObservableArray([]);

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
            field: "lossDate",
            title: "Kayıp Tarihi",
            filterable: { multi: true, search: true },
            width: 120
        }, {
            field: "insDate",
            title: "Kayıp Giriş Tarihi",
            filterable: { multi: true, search: true },
            width: 150
        }, {
            field: "approvalDate",
            title: "Onay Tarihi",
            filterable: { multi: true, search: true },
            width: 150
        },  {
            field: "approvalInsBy",
            title: "Onaylayan",
            filterable: { multi: true, search: true },
            width: 150
        },{
            field: "qualityLabApprover",
            title: "Onaylayan Birim",
            filterable: { multi: true, search: true },
            width: 150
        },{
            field: "lossState",
            title: "Kayıp Statü",
            filterable: { multi: true, search: true },
            width: 180
        },
        {
            field: "objectionLossDate",
            title: "İtiraz Tarihi",
            filterable: { multi: true, search: true },
            width: 180
        },
//        {
//            field: "objectionStatus",
//            title: "İtiraz Statü",
//            filterable: { multi: true, search: true },
//            width: 180
//        }
//        ,
        {
            field: "model",
            title: "Model",
            filterable: { multi: true, search: true },
            width: 80
        }, {
            field: "disegno",
            title: "Resim No",
            filterable: true,
            width: 120
        }, {
            field: "disegnoDesc",
            title: "Resim Açıklama",
            width: 200
        }, {
            field: "disegnoTableCode",
            title: "Masa No",
            width: 80
        }, {
            field: "sqpNo",
            title: "SQP No",
            width: 120
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
        }, {
            field: "tut",
            title: "TUT",
            filterable: true,
            width: 120
        }, {
            field: "lossSource",
            title: "Kayıp Kaynağı",
            filterable: true,
            width: 120
        }, {
            field: "insertedBy",
            title: "Kaybı Açan Kul.",
            filterable: { multi: true, search: true },
            width: 120
        }, {
            field: "usrDept",
            title: "Kaybı Açan Dep.",
            filterable: { multi: true, search: true },
            width: 200
        }
        ];


        angular.forEach(data, function(val, i){
            colDefs.push({
                field: 'durationDetails["' + val + '"]',
                title: val + " (dk)",
                sorting: true,
                aggregates: [ "sum"],
                groupFooterTemplate: "#= sum #",
                width: 100,
                attributes: {
                    style: "text-align: right;"
                }
            });
        });

        colDefs.push({
            field: "totalDuration",
            title: "Toplam Süre (dk)",
            aggregates: [ "sum"],
            groupFooterTemplate: "#= sum #",
            width: 100,
            attributes: {
                style: "text-align: right;"
            }
        });



        angular.forEach(data, function(val, i){

            colDefs.push({
                field: 'costDetails["' + val  +'"]',
                title: val + " \u20BA",
                sorting: true,
                template: "#= kendo.toString(costDetails['" + val  +"'], '0.00') # \u20BA",
                aggregates: [ "sum"],
                groupFooterTemplate: "#= kendo.toString(sum, '0.00') # \u20BA",
                width: 100,
                attributes: {
                    style: "text-align: right;"
                }
            });
        });



        colDefs.push({
            field: "totalCostOfWorkTypes",
            title: "Toplam Çalışma Türleri Tutarı \u20BA",
            template: "#= kendo.toString(totalCostOfWorkTypes, '0.00') # \u20BA",
            aggregates: [ "sum"],
            groupFooterTemplate: "#= kendo.toString(sum, '0.00') # \u20BA",
            width: 100,
            attributes: {
                style: "text-align: right;"
            }
        });
        
        colDefs.push({
            field: "qLabTestCost",
            title: "Kalite Lab. Tutar",
            template: "#= kendo.toString(qLabTestCost, '0.00') # \u20BA",
            aggregates: [ "sum"],
            groupFooterTemplate: "#= kendo.toString(sum, '0.00') # \u20BA",
            width: 100,
            attributes: {
                style: "text-align: right;"
            }
        });
     
        
        colDefs.push({
            field: "discardedPartCost",
            title: "Iskarta Parça Tutar",
            template: "#= kendo.toString(discardedPartCost, '0.00') # \u20BA",
            aggregates: [ "sum"],
            groupFooterTemplate: "#= kendo.toString(sum, '0.00') # \u20BA",
            width: 100,
            attributes: {
                style: "text-align: right;"
            }
        });
        
        colDefs.push({
            field: "otherCosts",
            title: "Diğer Masraflar",
            template: "#= kendo.toString(otherCosts, '0.00') # \u20BA",
            aggregates: [ "sum"],
            groupFooterTemplate: "#= kendo.toString(sum, '0.00') # \u20BA",
            width: 100,
            attributes: {
                style: "text-align: right;"
            }
        });
        

        colDefs.push({
            field: "totalCost",
            title: "Toplam Tutar \u20BA",
            template: "#= kendo.toString(totalCost, '0.00') # \u20BA",
            aggregates: [ "sum"],
            groupFooterTemplate: "#= kendo.toString(sum, '0.00') # \u20BA",
            width: 100,
            attributes: {
                style: "text-align: right;"
            }
        });

        $scope.mainReportGridOptions = {
            toolbar: [{name : "excel", text: "Excel"}],
            excel: {
                allPages: true,
                fileName: "YSKY - Ana Rapor.xlsx",
                filterable: true
            },
            autoBind: false,
            dataSource: {
                type: "json",
                data: $scope.mainReportData,
                pageSize: 5,
            },
            resizable: true,
            groupable: true,
            reorderable: true,
            pageable: {
                pageSizes: true,
            },
            filterable: true,
            columnMenu: true,
            columns: colDefs
        };

    });


    $scope.searchReport = function(){
        console.log("$scope.searchParams: ", $scope.searchParams)
        $http.post("/report/api/main-report", $scope.searchParams).success(function(data){

            var allPromises = [];

            for(var i = 0; i < data.length; i++) {
                var val = data[i];
                val.lossType = $translate.instant(val.lossType);
                val.lossSubType = $translate.instant(val.lossSubType);
               
                var lossStateTx = $translate.instant(val.lossState);
                var objectionStateTx=$translate.instant(val.objectionStatus);
                if(val.lossState == 'CANCELED'
                    && (val.lossStateChanger.length != 6 && val.lossStateChanger.charAt(0) != 'T')) {
                    lossStateTx += ' (Sistem)';
                }
                
                if(val.objectionStatus==null){
                	objectionStateTx='Firma İtirazı Yok';
                }else if(val.lossState == 'APPROVED' && val.objectionStatus == 'OPEN'){ //BU SÜREÇTEN EMİN DEGİLİM
                	objectionStateTx='İtiraza Cevap Bekleniyor';
                }else if(val.lossState == 'CANCELED' && val.objectionStatus == 'ACCEPT' && (val.lossStateChanger.length != 6 && val.lossStateChanger.charAt(0) != 'T')){
                	objectionStateTx += ' (Sistem)';
                }
                
                
                val.objectionStatus=$translate.instant(val.objectionStatus);
                //TODO OBJECTION STATUS (Sistem) ekleme için aşağıdaki logic'i kurgulamak gerekiyor. 
                //311 li id'de itiraz statüsü Accept Sistem olmalıydı,acik olarak geliyor. kurguda statü değiştirmiyor büyük ihtimal
                //objectionStatus = ACCEPT && (val.lossStateChanger.length != 6 && val.lossStateChanger.charAt(0) != 'T')
                
                val.lossState = lossStateTx;
                val.objectionStatus = objectionStateTx;
            }

            $scope.mainReportData.splice(0, $scope.mainReportData.length); // clear data
            $scope.mainReportData.push.apply($scope.mainReportData, data); // set data
            $scope.mainReportGrid.dataSource.data($scope.mainReportData);

            /*for(var i = 0; i < $scope.mainReportGridOptions.columns.length; i++) {
             $scope.mainReportGrid.autoFitColumn(i);
             }*/
        });

    };

};
ykyMainReportController.$inject = ["$scope", "$http", "tfContextRootService", "$translate", "$q",
    "commonDataSourceService", "commonSelectionOptions"];

angular.module("ykyReportsApp")
    .controller("ykyMainReportController", ykyMainReportController);