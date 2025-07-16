/**
 * Created by t40127 on 26.05.2016.
 */

var commonTableOptions = function($translate){
    return {
        toolbar: [{name : "excel", text: "Excel"}],
        excel: {
            allPages: true,
            fileName: "YSKY - Press Rapor.xlsx",
            filterable: true
        },
        autoBind: false,
        dataSource: {
            type: "json",
            transport: {
                read: undefined
            },
            pageSize: 15
        },
        resizable: true,
        sortable: true,
        columns: [{
            field: 'id',
            title: 'Kayıp ID',
            width: 100
        }, {
            field: 'currentState',
            title: 'Statü',
            width: 100,
            template: function(dataItem) {
                return $translate.instant(dataItem.currentState);
            }
        }, {
            field: 'supplier.firmType',
            title: 'Firma Türü',
            template: function(dataItem){
                return $translate.instant(dataItem.firmType);
            },
            width: 50
        },  {
            field: 'supplier.firmType',
            title: 'Sorumlu Firma',
            template: function(dataItem){
                if(dataItem.firmType === 'FIRM') {
                    return dataItem.supplier.sapCode + ' - ' + dataItem.supplier.name;
                } else {
                    return dataItem.firmDescription;
                }
            },
            width: 150
        }, {
            field: 'rollDetail.rollNo',
            title: 'Rulo Numarası',
            width: 100
        }, {
            field: 'rollDetail.part.disegno',
            title: 'Resim No',
            width: 100
        }, {
            field: 'rollDetail.disegnoDetail.forma',
            title: 'Forma',
            width: 100
        }, {
            field: 'rollDetail.disegnoDetail.quality',
            title: 'Kalite',
            width: 100
        }, {
            field: 'rollDetail.disegnoDetail.width',
            title: 'Genişlik',
            width: 100
        }, {
            field: 'rollDetail.disegnoDetail.thickness',
            title: 'Kalınlık',
            width: 100
        }, {
            field: 'rollDetail.rollWeight',
            title: 'Rulo Ağırlık',
            width: 100
        }, {
            field: 'rollDetail.invoiceNo',
            title: 'Fatura No',
            width: 100
        }, {
            field: 'rollDetail.shipName',
            title: 'Gemi Adı',
            width: 100
        }, {
            field: 'rollDetail.factoryEntranceDate',
            title: 'Fabrika Giriş Tarihi',
            width: 100
        }, {
            field: 'rollDetail.supplier.supplierCode',
            title: 'Tedarikçi Firma',
            template: '#: rollDetail.supplier.supplierCode # - #: rollDetail.supplier.name #',
            width: 150
        }, {
            field: 'rollDetail.notes',
            title: 'Notlar',
            width: 150
        }]
    };
};

var pressReportService = function($http){
    var factory = {};

    factory.getPressLosses = function(url, params, losses, deletePreviousData, callback){
        $http.post(url, params).success(function(data){

            if(deletePreviousData) {
                losses.splice(0, losses.length);
            }


            for(var i = 0; i < data.length; i++) {
                var rowData = data[i];
                for(var j = 0; j < rowData.details.length; j++) {
                    var rowDataDetail = rowData.details[j];

                    var newRow = {};
                    angular.extend(newRow, rowData);
                    angular.extend(newRow, rowDataDetail);
                    newRow.id = rowData.id;

                    losses.push(newRow);
                }
            }

            callback();
        });
    };



    return factory;
};
pressReportService.$inject = ["$http"];

var pressQualityReportController = function($scope, $http, $translate, pressReportService){
    $scope.pressLosses = [];
    $scope.searchParams = {};

    $scope.pressReportGridOptionsTmp = {};
    angular.extend($scope.pressReportGridOptionsTmp, commonTableOptions($translate));

    var qualityColDefs = [{
        field: 'qualityTraceId',
        title: 'Kalite Takip ID',
        width: 100
    }, {
        field: 'cutDate',
        title: 'Kesim Tarihi',
        width: 100
    }, {
        field: 'moldNo',
        title: 'Kalıp No',
        width: 100
    }, {
        field: 'reportDate',
        title: 'Tutanak Tarihi',
        width: 100
    }, {
        field: 'reportKeeper',
        title: 'Tutanak Tutulan Kişi',
        width: 100
    }, {
        field: 'rollScrapWeight',
        title: 'Rulo Iskarta Ağırlık',
        width: 100
    }, {
        field: 'cutScrapQty',
        title: 'Kesili Iskarta Adet',
        width: 100
    }, {
        field: 'cutPartBaseWeight',
        title: 'Kesili Parça Birim Ağırlık',
        width: 100
    }, {
        field: '_cutPartWeight',
        title: 'Kesili Parça Ağırlığı (Kesili Iskarta Adet * Kesili Parça Birim Ağırlık )',
        width: 100
    }, {
        field: 'pressedScrapQty',
        title: 'Basılı Iskarta Adet',
        width: 100
    }, {
        field: 'pressedPartBaseWeight',
        title: 'Basılı Parça Birim Ağırlık',
        width: 100
    }, {
        field: '_pressedPartWeight',
        title: 'Basılı Parça Ağırlığı (Basılı Iskarta Adet * Basılı Parça Birim Ağırlık)',
        width: 100
    }, {
        field: 'repairQty',
        title: 'Tamir Adet',
        width: 100
    }, {
        field: 'scrapSoldOut',
        title: 'Fire Dış Firmaya Satılan',
        width: 100
    }, {
        field: '_totalScrap',
        title: 'Fire Total (Kesili Parça Ağırlık + Basılı Parça Ağırlık + Rulo Iskarta Ağırlık)',
        width: 100
    }, {
        field: '_scrapHrd03',
        title: 'Fire kg (HRD) (Fire Total - Fire Dış Firmaya Satılan)',
        width: 100
    }, {
        field: 'scrapSalePrice',
        title: 'Dış Firma Anlaşmalı Fire Satış Fiyatı',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(scrapSalePrice, '0.00') # \u20AC</span>"
    }, {
        field: 'scrapHrdParam',
        title: 'Hurda Satış (HRD Parametresi)',
        width: 100
    }, {
        field: 'disegnoBasePrice',
        title: 'Malzeme Birim Fiyat (€/ton)',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(disegnoBasePrice, '0.00') # \u20AC</span>"
    }, {
        field: 'disegnoBasePriceDiff',
        title: 'Malzeme Birim Fiyat Değişikliği (€/ton)',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(disegnoBasePriceDiff, '0.00') # \u20AC</span>"
    }, {
        field: 'customsCostParam',
        title: 'Gümrük Masrafları Kat Sayısı',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(disegnoBasePriceDiff, '0.00') # \u20AC</span>"
    }, {
        field: '_partCustomsBasePrice',
        title: 'Malzeme Gümrüklü Birim Fiyat (€/ton) ((Malzeme Birim Fiyat + Malzeme Birim Fiyat Değişikliği) * Gümrük Masrafları Kat Sayısı)',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_partCustomsBasePrice, '0.00') # \u20AC</span>"
    }, {
        field: 'logisticsLabourHrs',
        title: 'Lojistik İşçiliği Saat',
        width: 100
    }, {
        field: '_logisticsLabourCost',
        title: 'Lojistik İşçiliği Maliyeti',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_logisticsLabourCost, '0.00') # \u20AC</span>"
    }, {
        field: 'productionLabourHrs',
        title: 'Üretim İşçiliği Saat',
        width: 100
    }, {
        field: '_productionLabourCost',
        title: 'Üretim İşçiliği Maliyeti',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_productionLabourCost, '0.00') # \u20AC</span>"
    }, {
        field: '_scrapPartCost',
        title: 'Fire Malzeme Tutar (Fire Total * Malzeme Güm. Birim Fiyatı / 1000)',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_scrapPartCost, '0.00') # \u20AC</span>"
    }, {
        field: '_scrapCostDiscount',
        title: 'Fire Maliyet İndirimi ((Fire HRD * Hurda Satış Parametresi) + (Fire Dış Firmaya Satılan * Dış Firma Fire Satış))',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_scrapCostDiscount, '0.00') # \u20AC</span>"
    }, {
        field: 'logisticsCostParam',
        title: 'Lojistik Maliyeti Parametresi',
        width: 100
    }, {
        field: '_logisticsCost',
        title: 'Lojistik Maliyeti (Lojistik Maliyet Parametresi * Fire Total / 1000)',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_logisticsCost, '0.00') # \u20AC</span>"
    }, {
        field: '_totalCost',
        title: 'Toplam Maliyet (Lojistik Maliyet + Üretim İşçilik Maliyet + Lojistik İşçilik Maliyet + Fire Malzeme Tutar - Hurda Tutar Maliyet İndirimi)',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_totalCost, '0.00') # \u20AC</span>"
    }, {
        field: 'insertedBy',
        title: 'Kayıp Bildiren Kişi',
        width: 100
    }, {
        field: 'insertedDate',
        title: 'Kayıp Bildirim Tarihi',
        width: 150
    }, {
        field: 'sapOrderNo',
        title: 'SAP Sipariş No',
        width: 100
    }, {
        field: 'creditNote',
        title: 'Dekont Numarası',
        width: 100
    }, {
        field: 'creditNoteDate',
        title: 'Dekont Tarihi',
        width: 100
    }, {
        field: 'creditNoteAmount',
        title: 'Dekont Tutar',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(creditNoteAmount, '0.00') # \u20AC</span>"
    }, {
        field: 'paymentMailDate',
        title: 'Ödeme Bildirim Tarihi',
        width: 100
    }, {
        field: 'paymentInformer',
        title: 'Ödeme Bildiren Kişi',
        width: 100
    }];

    $scope.pressReportGridOptionsTmp.columns.push.apply($scope.pressReportGridOptionsTmp.columns, qualityColDefs);

    $scope.pressReportGridOptionsTmp.dataSource.transport.read = function(options){

        pressReportService.getPressLosses("/loss/press/report/api/quality", $scope.searchParams, $scope.pressLosses, true, function(){
                options.success($scope.pressLosses);
            });
    };

    // initialize table
    $scope.pressReportGridOptions = $scope.pressReportGridOptionsTmp;


    $scope.search = function(){
        $scope.pressReportGrid.dataSource.read();
    };
};
pressQualityReportController.$inject = ["$scope", "$http", "$translate", "pressReportService"];

var pressLogisticsReportController = function($scope, $http, $translate, pressReportService){
    $scope.pressLosses = [];
    $scope.searchParams = {};

    $scope.pressReportGridOptionsTmp = {};
    angular.extend($scope.pressReportGridOptionsTmp, commonTableOptions($translate));

    var coldefs = [{
        field: 'atrscField',
        title: 'A/T/S/R/C',
        width: 50
    }, {
        field: 'frmApprover',
        title: 'Firmadan Teyid Veren Kişi',
        width: 100
    }, {
        field: 'transportedFrom',
        title: 'Nereden Sevk Edildiği',
        width: 100
    }, {
        field: 'transportedTo',
        title: 'Sevk Yeri',
        width: 100
    }, {
        field: 'transportDate',
        title: 'Sevk Tarihi',
        width: 100
    }, {
        field: 'alternativePart.disegno',
        title: 'Alternatif Resim No',
        width: 100
    }, {
        field: 'altDisegnoDetail.thickness',
        title: 'Alternatif Malzeme Kalınlık',
        width: 100
    }, {
        field: 'altDisegnoSliceWidth',
        title: 'Alternatif Malzeme Dilme Ölçüsü',
        width: 100
    }, {
        field: 'altDisegnoDetail.quality',
        title: 'Alternatif Malzeme Kalitesi',
        width: 100
    }, {
        field: 'altDiscardReason',
        title: 'Alternatif Iskarta Nedeni',
        width: 100
    }, {
        field: 'rollTofasTransDate',
        title: 'Rulo TOFAŞ Sevk Tarihi',
        width: 100
    }, {
        field: '_oKPartWeight',
        title: 'Gelen OK Malzeme Ağırlık (Rulo Ağırlık * Alternatif Malzeme Dilme Ölçüsü / Genişlik)',
        width: 100
    }, {
        field: '_scrapWeight',
        title: 'Iskarta Ağırlık (Rulo Ağırlık - Gelen OK Malzeme Ağırlık)',
        width: 100
    }, {
        field: 'slicePerTonne',
        title: 'Ton Başı Dilme',
        width: 100
    }, {
        field: '_calculatedInvoiceAmount',
        title: 'Hesaplanan Fatura Tutar (Ton Başı Dilme * Rulo Ağırlık / 1000)',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_calculatedInvoiceAmount, '0.00') # \u20AC</span>"
    }, {
        field: 'scrapHrdParam',
        title: 'Hurda Satış (HRD Parametresi)',
        width: 100
    }, {
        field: 'disegnoBasePrice',
        title: 'Malzeme Birim Fiyat ($/ton)',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(disegnoBasePrice, '0.00') # \u20AC</span>"
    }, {
        field: 'disegnoBasePriceDiff',
        title: 'Malzeme Birim Fiyat Değişikliği ($/ton)',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(disegnoBasePriceDiff, '0.00') # \u20AC</span>"
    }, {
        field: 'altDisegnoBasePrice',
        title: 'Alternatif Malzeme Birim Fiyat ($/ton)',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(altDisegnoBasePrice, '0.00') # \u20AC</span>"
    }, {
        field: '_usedPartPriceDifference',
        title: 'Kullanılan Malzeme Fiyat Farkı ((Malzeme Birim Fiyat + Malzeme Birim Fiyat Değişimi - Alternatif Resim Fiyat) * Gelen OK Malzeme Ağırlık)',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_usedPartPriceDifference, '0.00') # \u20AC</span>"
    }, {
        field: 'firmTransportationCost',
        title: 'Lojistik Maliyeti',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(firmTransportationCost, '0.00') # \u20AC</span>"
    }, {
        field: 'warehouseCost',
        title: 'Ardiye Maliyeti',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(warehouseCost, '0.00') # \u20AC</span>"
    }, {
        field: 'logisticsLabourHrs',
        title: 'Lojistik İşçiliği Saat',
        width: 100
    }, {
        field: '_logisticsLabourCost',
        title: 'Lojistik İşçiliği Maliyeti',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_logisticsLabourCost, '0.00') # \u20AC</span>"
    }, {
        field: 'productionLabourHrs',
        title: 'Üretim İşçiliği Saat',
        width: 100
    }, {
        field: '_productionLabourCost',
        title: 'Üretim İşçiliği Maliyeti',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_productionLabourCost, '0.00') # \u20AC</span>"
    }, {
        field: '_scrapPartCost',
        title: 'Fire Malzeme Tutar (Iskarta Ağırlık * Malzeme Birim Fiyat)',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_scrapPartCost, '0.00') # \u20AC</span>"
    }, {
        field: '_scrapCostDiscountPrice',
        title: 'Fire Maliyet İndirimi (Iskarta Ağırlık * Hurda Satış Parametresi )',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_scrapCostDiscountPrice, '0.00') # \u20AC</span>"
    }, {
        field: '_totalCost',
        title: 'Toplam Maliyet (Lojistik Maliyeti + Kul. Malz. Fiy. Farkı + Hesaplanan Fatura Tutar + Üretim İşçilik Maliyet + Lojistik İşçilik Maliyet + Hurda Malzeme Maliyeti - Hurda Maliyet İndirim Tutarı)',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_totalCost, '0.00') # \u20AC</span>"
    }, {
        field: 'insertedBy',
        title: 'Kayıp Bildiren Kişi',
        width: 100
    }, {
        field: 'insertedDate',
        title: 'Kayıp Bildirim Tarihi',
        width: 150
    }, {
        field: 'sapOrderNo',
        title: 'SAP Sipariş No',
        width: 100
    }, {
        field: 'creditNote',
        title: 'Dekont Numarası',
        width: 100
    }, {
        field: 'creditNoteDate',
        title: 'Dekont Tarihi',
        width: 100
    }, {
        field: 'creditNoteAmount',
        title: 'Dekont Tutar',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(creditNoteAmount, '0.00') # \u20AC</span>"
    }, {
        field: 'paymentMailDate',
        title: 'Ödeme Bildirim Tarihi',
        width: 100
    }, {
        field: 'paymentInformer',
        title: 'Ödeme Bildiren Kişi',
        width: 100
    }];

    $scope.pressReportGridOptionsTmp.columns.push.apply($scope.pressReportGridOptionsTmp.columns, coldefs);

    $scope.pressReportGridOptionsTmp.dataSource.transport.read = function(options){

        pressReportService.getPressLosses("/loss/press/report/api/logistics", $scope.searchParams, $scope.pressLosses, true, function(){
            options.success($scope.pressLosses);
        });
    };

    // initialize table
    $scope.pressReportGridOptions = $scope.pressReportGridOptionsTmp;


    $scope.search = function(){
        $scope.pressReportGrid.dataSource.read();
    };
};
pressLogisticsReportController.$inject = ["$scope", "$http", "$translate", "pressReportService"];

var pressDetailsReportController = function($scope, $http, $translate, pressReportService){
    $scope.pressLosses = [];
    $scope.searchParams = {};

    $scope.pressReportGridOptionsTmp = {};
    angular.extend($scope.pressReportGridOptionsTmp, commonTableOptions($translate));

    var coldefs = [{
        field: 'scrapHrdParam',
        title: 'Hurda Satış (HRD Parametresi)',
        width: 100
    }, {
        field: 'logisticsLabourHrs',
        title: 'Lojistik İşçiliği Saat',
        width: 100
    }, {
        field: '_logisticsLabourCost',
        title: 'Lojistik İşçiliği Maliyeti',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_logisticsLabourCost, '0.00') # \u20AC</span>"
    }, {
        field: 'productionLabourHrs',
        title: 'Üretim İşçiliği Saat',
        width: 100
    }, {
        field: '_productionLabourCost',
        title: 'Üretim İşçiliği Maliyeti',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_productionLabourCost, '0.00') # \u20AC</span>"
    }, {
        field: '_totalCost',
        title: 'Toplam Maliyet',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(_totalCost, '0.00') # \u20AC</span>"
    }, {
        field: 'insertedBy',
        title: 'Kayıp Bildiren Kişi',
        width: 100
    }, {
        field: 'insertedDate',
        title: 'Kayıp Bildirim Tarihi',
        width: 150
    }, {
        field: 'sapOrderNo',
        title: 'SAP Sipariş No',
        width: 100
    }, {
        field: 'creditNote',
        title: 'Dekont Numarası',
        width: 100
    }, {
        field: 'creditNoteDate',
        title: 'Dekont Tarihi',
        width: 100
    }, {
        field: 'creditNoteAmount',
        title: 'Dekont Tutar',
        width: 100,
        template: "<span style='float:right'>#= kendo.toString(creditNoteAmount, '0.00') # \u20AC</span>"
    }, {
        field: 'paymentMailDate',
        title: 'Ödeme Bildirim Tarihi',
        width: 100
    }, {
        field: 'paymentInformer',
        title: 'Ödeme Bildiren Kişi',
        width: 100
    }];

    $scope.pressReportGridOptionsTmp.columns.push.apply($scope.pressReportGridOptionsTmp.columns, coldefs);

    $scope.pressReportGridOptionsTmp.dataSource.transport.read = function(options){

        $scope.pressLosses.splice(0, $scope.pressLosses.length);

        if(!$scope.searchParams.pressLossType || $scope.searchParams.pressLossType == "QUALITY") {
            pressReportService.getPressLosses("/loss/press/report/api/quality", $scope.searchParams, $scope.pressLosses, false, function(){

                if(!$scope.searchParams.pressLossType || $scope.searchParams.pressLossType == "LOGISTICS") {
                    pressReportService.getPressLosses("/loss/press/report/api/logistics", $scope.searchParams, $scope.pressLosses, false, function(){
                        options.success($scope.pressLosses);
                    });
                } else {
                    options.success($scope.pressLosses);
                }

            });
        } else if(!$scope.searchParams.pressLossType || $scope.searchParams.pressLossType == "LOGISTICS") {
            pressReportService.getPressLosses("/loss/press/report/api/logistics", $scope.searchParams, $scope.pressLosses, false, function(){
                options.success($scope.pressLosses);
            });
        } else {
            options.success([]);
        }

    };

    // initialize table
    $scope.pressReportGridOptions = $scope.pressReportGridOptionsTmp;


    $scope.search = function(){
        $scope.pressReportGrid.dataSource.read();
    };
};
pressDetailsReportController.$inject = ["$scope", "$http", "$translate", "pressReportService"];


var pressReportsSearchParamsDirective = function(){
    return {
        restrict: "AE",
        replace: true,
        scope: {
            searchParams: "=searchParams",
            searchFunc: "&",
            isDetails: "=isDetails"
        },
        templateUrl: '/ng/view/yky/report/press/search-params.html',
        controller: pressReportsSearchParamsDirectiveController
    };
};

var pressReportsSearchParamsDirectiveController = function($scope, commonDataSourceService){
    $scope.lossStates = ["OPEN", "PRESS_CLOSED", "CANCELED"];
    $scope.pressLossTypes = ["QUALITY", "LOGISTICS"];

    $scope.pressSuppliers = commonDataSourceService.getPressSuppliers();
};
pressReportsSearchParamsDirectiveController.$inject = ["$scope", "commonDataSourceService"];




// dashboard
var pressDashboardController = function($scope, $http, $translate){
    $scope.data = [];
    $scope.filteredData = new kendo.data.ObservableArray([]);
    $scope.pieChartData = new kendo.data.ObservableArray([]);
    $scope.years = new kendo.data.ObservableArray([]);
    $scope.suppliers = new kendo.data.ObservableArray([]);

    // selections
    $scope.selectedYears = [];
    $scope.selectedSuppliers = [];

    $http.get("/loss/press/report/api/dashboard").success(function(data){
        $scope.data = data;
        $scope.years.empty();
        $scope.filteredData.empty();

        for(var i = 0; i < data.length; i++) {
            var row = data[i];
            row.yearAsDate = new Date(row.year, 1, 1, 1, 1, 1, 1);
            row.balance = row.totalCost - row.supplierPayment;

            if($scope.years.indexOf(row.year) < 0) {
                $scope.years.push(row.year);
                $scope.selectedYears.push(row.year);
            }

            if($scope.selectedSuppliers.indexOf(row.supplierCode) < 0) {
                $scope.selectedSuppliers.push(row.supplierCode);
                $scope.suppliers.push({
                    supplierCode: row.supplierCode,
                    supplierLabel: (row.supplierCode + "-" + row.supplierName)
                });
            }
        }

        $scope.refreshFilteredData(true);
    });

    $scope.refreshFilteredData = function(isInitial){
        $scope.filteredData.empty();
        var tmpFilteredData = [];

        for(var i = 0; i < $scope.data.length; i++) {
            var row = $scope.data[i];

            if($scope.selectedYears.indexOf(row.year) >= 0
                && $scope.selectedSuppliers.indexOf(row.supplierCode) >= 0) {
                tmpFilteredData.push(row);
            }

        }

        // update pie chart
        $scope.pieChartData.empty();
        var dataSource = new kendo.data.DataSource({
            data: tmpFilteredData,
            group: {field: "supplierName", aggregates: [{
                field: "balance", aggregate: "sum"
            }]}
        });
        dataSource.read();

        var view = dataSource.view();
        for(var idx = 0; idx < view.length; idx++) {
            $scope.pieChartData.push({
                supplierName: view[idx].value,
                balance: view[idx].aggregates.balance.sum
            });
        }


        $scope.filteredData.push.apply($scope.filteredData, tmpFilteredData);

        if(!isInitial) {
            $scope.$apply();
        }

    };

    $scope.yearsOptions = {
        dataSource: {
            data: $scope.years
        },
        change: function(e){
            $scope.refreshFilteredData(false);
        }
    };

    $scope.supplierOptions = {
        dataTextField: "supplierLabel",
        dataValueField: "supplierCode",
        valuePrimitive: true,
        dataSource: {
            data: $scope.suppliers
        },
        change: function(e){
            $scope.refreshFilteredData(false);
        }
    };

    $scope.lineChartOptions = {
        theme: "bootstrap",
        title: {
            text: "Yıllar İçinde Değişim"
        },
        legend: {
            position: "right"
        },
        dataSource: {
            data: $scope.filteredData,
            group: {
                field: "supplierName"
            }
        },
        series: [
            {
                name: "Toplam Tutar",
                type: "line",
                style: "smooth",
                field: "totalCost",
                axis: "costAxis",
                tooltip: {
                    format: "{0:n2} €",
                    visible: true
                }
            }, {
                name: "Bakiye",
                type: "column",
                field: "balance",
                axis: "balanceAxis",
                tooltip: {
                    format: "{0:n2} €",
                    visible: true
                }
            }
        ],
        valueAxes: [
            {
                name: "costAxis",
                title: {
                    text: $translate.instant("totalCost")
                }
            }, {
                name: "balanceAxis",
                title: {
                    text: $translate.instant("balance")
                }
            }
        ],
        categoryAxis: {
            field: "yearAsDate",
            type: "date",
            baseUnit: "years",
            labels: {
                dateFormats: {
                    years: "yyyy"
                },
                visible: true
            },
            axisCrossingValues: [0, 10]
        }
    };

    $scope.pieChartOptions = {
        theme: "bootstrap",
        legend: {
            visible: false
        },
        title: {
            text: "Bakiye Dağılımı"
        },
        dataSource: {
            data: $scope.pieChartData,
        },
        series: [{
            type: "pie",
            field: "balance",
            categoryField: "supplierName",
            aggregate: "sum",
            labels: {
                visible: true,
                background: "transparent",
                template: "#= category #:  %#= kendo.toString(percentage*100, 'n1')#  #= kendo.toString(value, 'n2')# €"
            }
        }]
    }


};
pressDashboardController.$inject = ["$scope", "$http", "$translate"];

angular.module("pressReportControllers", [])
    .service("pressReportService", pressReportService)
    .controller("pressLogisticsReportController", pressLogisticsReportController)
    .controller("pressQualityReportController", pressQualityReportController)
    .controller("pressDetailsReportController", pressDetailsReportController)
    .controller("pressReportsSearchParamsDirectiveController", pressReportsSearchParamsDirectiveController)
    .directive("pressReportsSearchParams", pressReportsSearchParamsDirective)
    .controller("pressDashboardController", pressDashboardController);
