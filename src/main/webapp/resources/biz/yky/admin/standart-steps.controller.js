/**
 * Created by t40127 on 17.03.2017.
 */

var tfAdminStandartStepsController = function ($scope, $state, $modal, ykyStandartStepsFactory, tfBaseCrudListController) {
    return tfBaseCrudListController($scope, $state, ykyStandartStepsFactory, $modal);
};
tfAdminStandartStepsController.$inject = ["$scope", "$state", "$modal", "ykyStandartStepsFactory", "tfBaseCrudListController"];

var tfAdminStandartStepsNewController = function ($scope, $state, ykyStandartStepsFactory, tfBaseCrudNewController, $http, tfContextRootService) {
    $http.get("/common/api/loss-types").success(function (data) {
        $scope.lossTypes = data;
    });

    $scope.subLossTypesSelectOptions = {
        itemTemplate: "{{'#:data#' | translate}}",
        tagTemplate: "{{'#:data#' | translate}}",
        dataSource: {
            type: "json",
            transport: {
                read: {
                    url: tfContextRootService.getUrl("/common/api/production-sub-loss-types"),
                }
            }
        }
    };

    return tfBaseCrudNewController($scope, $state, ykyStandartStepsFactory, {}, "tfAdmin.standartSteps");
};
tfAdminStandartStepsNewController.$inject = ["$scope", "$state", "ykyStandartStepsFactory", "tfBaseCrudNewController", "$http", "tfContextRootService"];

var tfAdminStandartStepsEditController = function ($scope, $state, ykyStandartStepsFactory, tfBaseCrudEditController, $stateParams, $http, tfContextRootService) {
    $http.get("/common/api/loss-types").success(function (data) {
        $scope.lossTypes = data;
    });

    $scope.subLossTypesSelectOptions = {
        dataSource: {
            type: "json",
            transport: {
                read: {
                    url: tfContextRootService.getUrl("/common/api/production-sub-loss-types"),
                }
            }
        }
    };
    return tfBaseCrudEditController($scope, $state, ykyStandartStepsFactory, {id: $stateParams.id}, "tfAdmin.standartSteps");
};
tfAdminStandartStepsEditController.$inject = ["$scope", "$state", "ykyStandartStepsFactory", "tfBaseCrudEditController", "$stateParams", "$http", "tfContextRootService"];

angular.module("ykyAdminApp")
    .controller("tfAdminStandartStepsNewController", tfAdminStandartStepsNewController)
    .controller("tfAdminStandartStepsController", tfAdminStandartStepsController)
    .controller("tfAdminStandartStepsEditController", tfAdminStandartStepsEditController);