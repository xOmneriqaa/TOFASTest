/**
 * Created by t40127 on 17.03.2017.
 */


var commonDataSourceService = function(tfContextRootService, $http, $rootScope){
    var factory = {};

    factory.parts = undefined;
    factory.models = undefined;
    factory.tuts = undefined;
    factory.standartSteps = undefined;
    factory.steps = undefined;
    factory.qLossTables = undefined;
    factory.workTypes = undefined;
    factory.lossCodes = undefined;
    factory.logisticsLossTypes = undefined;
    factory.logisticsAcceptencePoints = undefined;
    factory.qualityLabTests = undefined;
    factory.productionSubLossTypes = undefined;
    factory.lossSources = undefined;
    factory.blueCollarWage = undefined;
    factory.suppliers = undefined;
    factory.lossTypes = undefined;
    factory.approverRoles = undefined;

    // press
    factory.atrscParams = undefined;
    factory.pressSuppliers = undefined;
    factory.firmTypes = undefined;

    factory.getFirmTypes = function(){
        if(factory.firmTypes == undefined) {
            factory.firmTypes = [];
            $http.get("/loss/press/api/firm-types").success(function(data){
                factory.firmTypes.push.apply(factory.firmTypes, data);
            });
        }

        return factory.firmTypes;
    };

    factory.getAtrscParams = function(){
        if(factory.atrscParams == undefined) {
            factory.atrscParams = new kendo.data.ObservableArray([]);
            $http.get("/loss/press/api/atrsc-params").success(function(data){
                factory.atrscParams.push.apply(factory.atrscParams, data);
            });
        }

        return factory.atrscParams;
    };

    factory.getPressSuppliers = function(){
        if(factory.pressSuppliers == undefined) {
            factory.pressSuppliers = new kendo.data.ObservableArray([]);
            $http.get("/loss/press/api/suppliers").success(function(data){
                factory.pressSuppliers.push.apply(factory.pressSuppliers, data);
            });
        }

        return factory.pressSuppliers;
    };

    $http.get("/report/api/get-suppliers").success(function(data){
        factory.suppliers = new kendo.data.ObservableArray(data);
        $rootScope.isSupsReady = true;
    });

    $http.get("/common/api/parts").success(function(data){
        factory.parts = new kendo.data.ObservableArray(data);
        $rootScope.isArrsReady = true;
    });

    factory.getSuppliers = function(){
        return factory.suppliers;
    };

    factory.getLossTypes = function(){
        if(factory.lossTypes == undefined) {
            factory.lossTypes = new kendo.data.ObservableArray([]);
            $http.get("/common/api/loss-types").success(function(data){
                factory.lossTypes.push.apply(factory.lossTypes, data);
            });
        }

        return factory.lossTypes;
    };

    factory.getBlueCollarWage = function(){
        if(factory.blueCollarWage == undefined) {
            factory.blueCollarWage = 0;
            $http.get("/loss/api/blue-collar-wage").success(function(data){
                factory.blueCollarWage = data;
            });
        }

        return factory.blueCollarWage;
    };

    factory.getLossSources = function(){
        if(factory.lossSources == undefined) {
            factory.lossSources = new kendo.data.ObservableArray([]);
            $http.get("/common/api/loss-sources").success(function(data){
                factory.lossSources.push.apply(factory.lossSources, data);
            });
        }

        return factory.lossSources;
    };

    factory.getProductionSubLossTypes = function(){
        if(factory.productionSubLossTypes == undefined) {
            factory.productionSubLossTypes = new kendo.data.ObservableArray([]);
            $http.get("/common/api/production-sub-loss-types").success(function(data){
                factory.productionSubLossTypes.push.apply(factory.productionSubLossTypes, data);
            });
        }

        return factory.productionSubLossTypes;
    };

    factory.getQualityLabTests = function(){
        if(factory.qualityLabTests == undefined) {
            factory.qualityLabTests = new kendo.data.ObservableArray([]);
            $http.get("/common/api/quality-lab-tests").success(function(data){
                factory.qualityLabTests.push.apply(factory.qualityLabTests, data);
            });
        }

        return factory.qualityLabTests;
    };

    factory.getLogisticsAcceptencePoints = function(){
        if(factory.logisticsAcceptencePoints == undefined) {
            factory.logisticsAcceptencePoints = new kendo.data.ObservableArray([]);
            $http.get("/common/api/logistics-acceptence-points").success(function(data){
                factory.logisticsAcceptencePoints.push.apply(factory.logisticsAcceptencePoints, data);
            });
        }

        return factory.logisticsAcceptencePoints;
    };

    factory.getLogisticsLossTypes = function(){
        if(factory.logisticsLossTypes == undefined) {
            factory.logisticsLossTypes = new kendo.data.ObservableArray([]);
            $http.get("/common/api/logistics-loss-types").success(function(data){
                factory.logisticsLossTypes.push.apply(factory.logisticsLossTypes, data);
            });
        }

        return factory.logisticsLossTypes;
    };

    factory.getModels = function(){
        if(factory.models == undefined) {
            factory.models = new kendo.data.ObservableArray([]);
            $http.get("/common/api/models").success(function(data){
                factory.models.push.apply(factory.models, data);
            });
        }

        return factory.models;
    };

    factory.getTuts = function(){
        if(factory.tuts == undefined) {
            factory.tuts = new kendo.data.ObservableArray([]);
            $http.get("/common/api/tuts").success(function(data){
                factory.tuts.push.apply(factory.tuts, data);
            });
        }

        return factory.tuts;
    };

    factory.getStandartSteps = function(){
        if(factory.standartSteps == undefined) {
            factory.standartSteps = new kendo.data.ObservableArray([]);
            $http.get("/common/api/standart-steps").success(function(data){
                factory.standartSteps.push.apply(factory.standartSteps, data);
            });
        }

        return factory.standartSteps;
    };

    factory.getSteps = function(){
        if(factory.steps == undefined) {
            factory.steps = new kendo.data.ObservableArray([]);
            $http.get("/common/api/steps").success(function(data){
                factory.steps.push.apply(factory.steps, data);
            });
        }

        return factory.steps;
    };

    factory.getQualityLossTables = function(){
        if(factory.qLossTables == undefined) {
            factory.qLossTables = new kendo.data.ObservableArray([]);
            $http.get("/common/api/quality-loss-tables").success(function(data){
                factory.qLossTables.push.apply(factory.qLossTables, data);
            });
        }

        return factory.qLossTables;
    };

    factory.getWorkTypes = function(){
        if(factory.workTypes == undefined) {
            factory.workTypes = new kendo.data.ObservableArray([]);
            $http.get("/common/api/work-types").success(function(data){
                factory.workTypes.push.apply(factory.workTypes, data);
            });
        }

        return factory.workTypes;
    };

    factory.getLossCodes = function(){
        if(factory.lossCodes == undefined) {
            factory.lossCodes = new kendo.data.ObservableArray([]);
            $http.get("/common/api/loss-codes").success(function(data){
                factory.lossCodes.push.apply(factory.lossCodes, data);
            });
        }
        return factory.lossCodes;
    };

    factory.getApproverRoles = function(){
        if(factory.approverRoles === undefined) {
            factory.approverRoles = new kendo.data.ObservableArray([]);
            $http.get("/loss/api/approver-roles").success(function(data){
                for(var i = 0; i < data.length; i++){
                    var name = ""
                    if(data[i].approverRole.includes("DESK")) name = "Montaj Masası"
                    else if(data[i].approverRole.includes("SAMPLE")) name = "Numune Masası"
                    else if(data[i].approverRole.includes("AFTERSALES")) name = "Satış Sonrası Masası"
                    else if(data[i].approverRole.includes("SUSPENSION")) name = "Süspansiyon Masası"
                    else if(data[i].approverRole.includes("BODY")) name = "Gövde Masası"
                    data[i] = { name:name, id:data[i].id}
                }
                factory.approverRoles.push.apply(factory.approverRoles, data);
            });
        }
        return factory.approverRoles;
    };


    factory.modelsDS = function () {
        return {
            type: "json",
            data: factory.getModels()
        };
    };

    factory.tutsDS = function () {
        return {
            type: "json",
            data: factory.getTuts()
        };
    };

    factory.standartStepsDS = function () {
        return {
            type: "json",
            data: factory.getStandartSteps()
        };
    };

    factory.stepsDS = function () {
        return {
            type: "json",
            data: factory.steps
        };
    };

    factory.qualityLossTablesDS = function () {
        return {
            type: "json",
            data: factory.getQualityLossTables()
        };
    };

    factory.workTypesDS = function () {

        return {
            type: "json",
            data: factory.getWorkTypes()
        };
    };

    factory.lossCodesDS = function () {
        return {
            type: "json",
            data: factory.getLossCodes()
        };
    };



    factory.partsDS = function () {
        return {
            type: "json",
            data: factory.parts
        };
    };

    return factory;
};
commonDataSourceService.$inject = ["tfContextRootService", "$http", "$rootScope", "$q"];

angular.module("ykyLossCommonComponents")
    .factory("commonDataSourceService", commonDataSourceService);