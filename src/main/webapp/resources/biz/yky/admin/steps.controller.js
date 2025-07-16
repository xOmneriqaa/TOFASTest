/**
 * Created by t40127 on 17.03.2017.
 */
tfAdminStepsController = function ($scope, $state, $modal, ykyStepsFactory, tfBaseCrudListController) {
    return tfBaseCrudListController($scope, $state, ykyStepsFactory, $modal);
};
tfAdminStepsController.$inject = ["$scope", "$state", "$modal", "ykyStepsFactory", "tfBaseCrudListController"];

tfAdminStepsNewController = function ($scope, $state, ykyStepsFactory, tfBaseCrudNewController, $http, tfContextRootService) {
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
    return tfBaseCrudNewController($scope, $state, ykyStepsFactory, {}, "tfAdmin.steps");
};
tfAdminStepsNewController.$inject = ["$scope", "$state", "ykyStepsFactory", "tfBaseCrudNewController", "$http", "tfContextRootService"];

tfAdminStepsEditController = function ($scope, $state, ykyStepsFactory, tfBaseCrudEditController, $stateParams, $http, tfContextRootService) {
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
    return tfBaseCrudEditController($scope, $state, ykyStepsFactory, {id: $stateParams.id}, "tfAdmin.steps");
};
tfAdminStepsEditController.$inject = ["$scope", "$state", "ykyStepsFactory", "tfBaseCrudEditController", "$stateParams", "$http", "tfContextRootService"];

angular.module("ykyAdminApp")

    .controller("tfAdminStepsController", tfAdminStepsController)
    .controller("tfAdminStepsNewController", tfAdminStepsNewController)
    .controller("tfAdminStepsEditController", tfAdminStepsEditController);