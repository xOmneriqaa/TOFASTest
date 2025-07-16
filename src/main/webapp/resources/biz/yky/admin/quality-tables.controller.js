/**
 * Created by t40127 on 17.03.2017.
 */

var tfAdminQualityTablesController = function($scope, $state, $modal, ykyQualityTableFactory, tfBaseCrudListController){
    return tfBaseCrudListController($scope, $state, ykyQualityTableFactory, $modal);
};
tfAdminQualityTablesController.$inject = ["$scope", "$state", "$modal", "ykyQualityTableFactory", "tfBaseCrudListController"];

var tfAdminQualityTablesNewController = function ($scope, $state, ykyQualityTableFactory, tfBaseCrudNewController) {
    return tfBaseCrudNewController($scope, $state, ykyQualityTableFactory, {}, "tfAdmin.qualityTables");
};
tfAdminQualityTablesNewController.$inject = ["$scope", "$state", "ykyQualityTableFactory", "tfBaseCrudNewController"];

var tfAdminQualityTablesEditController = function ($scope, $state, ykyQualityTableFactory, tfBaseCrudEditController, $stateParams) {
    return tfBaseCrudEditController($scope, $state, ykyQualityTableFactory, {id: $stateParams.id}, "tfAdmin.qualityTables");
};
tfAdminQualityTablesEditController.$inject = ["$scope", "$state", "ykyQualityTableFactory", "tfBaseCrudEditController", "$stateParams"];

angular.module("ykyAdminApp")

    .controller("tfAdminQualityTablesController", tfAdminQualityTablesController)
    .controller("tfAdminQualityTablesNewController", tfAdminQualityTablesNewController)
    .controller("tfAdminQualityTablesEditController", tfAdminQualityTablesEditController);