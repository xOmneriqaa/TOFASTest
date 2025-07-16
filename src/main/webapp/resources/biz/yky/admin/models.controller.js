/**
 * Created by t40127 on 17.03.2017.
 */

tfAdminModelsController = function ($scope, $state, $modal, ykyModelsFactory,
                                    tfBaseCrudListController) {
    return tfBaseCrudListController($scope, $state, ykyModelsFactory, $modal);
};
tfAdminModelsController.$inject = ["$scope", "$state", "$modal", "ykyModelsFactory",
    "tfBaseCrudListController"];

tfAdminModelsNewController = function ($scope, $state, ykyModelsFactory,
                                       tfBaseCrudNewController) {
    return tfBaseCrudNewController($scope, $state, ykyModelsFactory, {}, "tfAdmin.models");
};
tfAdminModelsNewController.$inject = ["$scope", "$state", "ykyModelsFactory",
    "tfBaseCrudNewController"];

tfAdminModelsEditController = function ($scope, $state, ykyModelsFactory,
                                        tfBaseCrudEditController, $stateParams) {
    return tfBaseCrudEditController($scope, $state, ykyModelsFactory,
        {id: $stateParams.id}, "tfAdmin.models");
};
tfAdminModelsEditController.$inject = ["$scope", "$state", "ykyModelsFactory",
    "tfBaseCrudEditController", "$stateParams"];

angular.module("ykyAdminApp")

    .controller("tfAdminModelsController", tfAdminModelsController)
    .controller("tfAdminModelsNewController", tfAdminModelsNewController)
    .controller("tfAdminModelsEditController", tfAdminModelsEditController);