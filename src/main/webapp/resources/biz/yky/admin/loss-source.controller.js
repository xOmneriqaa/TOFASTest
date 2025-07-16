/**
 * Created by t40127 on 17.03.2017.
 */

tfAdminLossSourcesController = function ($scope, $state, $modal, ykyLossSourceFactory, tfBaseCrudListController) {
    return tfBaseCrudListController($scope, $state, ykyLossSourceFactory, $modal);
};
tfAdminLossSourcesController.$inject = ["$scope", "$state", "$modal", "ykyLossSourceFactory", "tfBaseCrudListController"];

tfAdminLossSourcesNewController = function ($scope, $state, ykyLossSourceFactory, tfBaseCrudNewController) {
    return tfBaseCrudNewController($scope, $state, ykyLossSourceFactory, {}, "tfAdmin.lossSources");
};
tfAdminLossSourcesNewController.$inject = ["$scope", "$state", "ykyLossSourceFactory", "tfBaseCrudNewController"];

tfAdminLossSourcesEditController = function ($scope, $state, ykyLossSourceFactory, tfBaseCrudEditController, $stateParams) {
    return tfBaseCrudEditController($scope, $state, ykyLossSourceFactory, {id: $stateParams.id}, "tfAdmin.lossSources");
};
tfAdminLossSourcesEditController.$inject = ["$scope", "$state", "ykyLossSourceFactory", "tfBaseCrudEditController", "$stateParams"];

angular.module("ykyAdminApp")

    .controller("tfAdminLossSourcesController", tfAdminLossSourcesController)
    .controller("tfAdminLossSourcesNewController", tfAdminLossSourcesNewController)
    .controller("tfAdminLossSourcesEditController", tfAdminLossSourcesEditController);