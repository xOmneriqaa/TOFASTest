/**
 * Created by t40127 on 17.03.2017.
 */

var tfAdminDurationsController = function($http, $scope, $modal, $state){

    $scope.searchParams = {
        disegno : "",
        model: ""
    };

    $scope.deleteItem = function(item){
        var modalInstance = $modal.open({
            templateUrl: "durationDeleteModal.html",
            controller: "DurationDeleteModalController",
            resolve: {
                selectedItem : function(){
                    return item;
                }
            }
        });

        modalInstance.result.then(function(selectedItem){
            $http.delete(("/admin/api/durations/" + selectedItem.id)).success(function(){
                $state.reload();
            });
        });
    };

    $scope.durationGridOptions = {
        autoBind: false,
        dataSource: {
            type: "json",
            transport: {
                read: function(options) {
                    $http.post("/admin/api/durations/", $scope.searchParams).success(function(data){
                        options.success(data);
                    });
                }
            },
            pageSize: 10
        },
        columns: [
            {
                field: "id",
                title: "#ID",
                filterable: { multi: true }
            }, {
                field: "part.disegno",
                title: "{{'disegno'| translate}}",
                filterable: { multi: true }
            }, {
                field: "part.models",
                title: "{{'models'| translate}}",
                filterable: { multi: true },
                template: function(dataItem){
                    var modelsStr = '';
                    for(var i = 0; i < dataItem.models.length; i++) {
                        modelsStr += '<span class="label label-default">' + dataItem.models[i].code + '</span>&nbsp;'
                    }
                    return modelsStr;
                }
            }, {
                field: "insertedBy",
                title: "{{'insertedBy'| translate}}",
                filterable: { multi: true }
            }, {
                field: "insertedDate",
                title: "{{'insertedDate'| translate}}",
                filterable: { multi: true }
            }, {
                field: "updatedBy",
                title: "{{'updatedBy'| translate}}",
                filterable: { multi: true }
            }, {
                field: "updatedDate",
                title: "{{'updatedDate'| translate}}",
                filterable: { multi: true }
            }
        ]
    };

    $scope.getDurations = function(){
        $scope.durationsGrid.dataSource.read();
    };

};
tfAdminDurationsController.$inject = ["$http", "$scope", "$modal", "$state"];

var DurationDeleteModalController = function($scope, $modalInstance, selectedItem){
    $scope.selectedItem = selectedItem;

    $scope.ok = function(){
        $modalInstance.close($scope.selectedItem);
    };

    $scope.cancel = function(){
        $modalInstance.dismiss();
    };
};
DurationDeleteModalController.$inject = ["$scope", "$modalInstance", "selectedItem"];


var tfAdminDurationsNewController = function($scope, $http, $state, commonSelectionOptions){
    $scope.durationExists = false;
    $scope.stSteps = [];
    $scope.stepDurations = [];

    $http.get("/common/api/standart-steps").success(function(data){
        $scope.stSteps = data;
    });




    $http.get("/admin/api/durations/new").success(function(data){
        $scope.item = data;
        $scope.stepDurations = data.stepDurations;
    });

    $scope.saveItem = function(){
        $scope.item.stepDurations = $scope.stepDurations;
        $http.post("/admin/api/durations/save", $scope.item).success(function(data){
            if(data) {
                $state.go("tfAdmin.durations", null, {reload: true});
            } else {
                $scope.durationExists = true;
            }
        });
    };

    $scope.partSelectionOptions = function () {
        return commonSelectionOptions.partOptions({});
    };

    $scope.modelSelectionOptions = function () {
        return commonSelectionOptions.modelOptions();
    };



};
tfAdminDurationsNewController.$inject = ["$scope", "$http", "$state", "commonSelectionOptions"];


var tfAdminDurationsEditController = function($scope, $http, $state, $stateParams,
                                              tfContextRootService, commonSelectionOptions){
    $scope.partSelectionOptions = function () {
        return commonSelectionOptions.partOptions({});
    };

    $scope.modelSelectionOptions = function () {
        return commonSelectionOptions.modelOptions();
    };



    $scope.stSteps = [];
    $scope.stepDurations = [];

    $http.get("/common/api/standart-steps").success(function(data){
        $scope.stSteps = data;
    });

    $http.get(("/admin/api/durations/" + $stateParams.id)).success(function(data){
        $scope.item = data;
        $scope.stepDurations = data.stepDurations;
    });

    $scope.updateItem = function(){
        $scope.item.stepDurations = $scope.stepDurations;
        $http.post("/admin/api/durations/update", $scope.item).success(function(data){
            $state.go("tfAdmin.durations", null, {reload: true});
        });
    };
};
tfAdminDurationsEditController.$inject = ["$scope", "$http", "$state", "$stateParams",
    "tfContextRootService", "commonSelectionOptions"];


angular.module("ykyAdminApp")

    .controller("tfAdminDurationsController", tfAdminDurationsController)
    .controller("tfAdminDurationsNewController", tfAdminDurationsNewController)
    .controller("tfAdminDurationsEditController", tfAdminDurationsEditController)
    .controller("DurationDeleteModalController", DurationDeleteModalController);