/**
 * Created by t40127 on 17.03.2017.
 */

tfAdminLogisticsLossTypesController = function ($scope, $state, $modal, ykyLogisticsLossTypeFactory, tfBaseCrudListController) {
    return tfBaseCrudListController($scope, $state, ykyLogisticsLossTypeFactory, $modal);
};
tfAdminLogisticsLossTypesController.$inject = ["$scope", "$state", "$modal", "ykyLogisticsLossTypeFactory", "tfBaseCrudListController"];

tfAdminLogisticsLossTypesNewController = function ($scope, $state, ykyLogisticsLossTypeFactory, tfBaseCrudNewController) {
    return tfBaseCrudNewController($scope, $state, ykyLogisticsLossTypeFactory, {}, "tfAdmin.logisticsLossTypes");
};
tfAdminLogisticsLossTypesNewController.$inject = ["$scope", "$state", "ykyLogisticsLossTypeFactory", "tfBaseCrudNewController"];

tfAdminLogisticsLossTypesEditController = function ($scope, $state, ykyLogisticsLossTypeFactory, tfBaseCrudEditController, $stateParams) {
    return tfBaseCrudEditController($scope, $state, ykyLogisticsLossTypeFactory, {id: $stateParams.id}, "tfAdmin.logisticsLossTypes");
};
tfAdminLogisticsLossTypesEditController.$inject = ["$scope", "$state", "ykyLogisticsLossTypeFactory", "tfBaseCrudEditController", "$stateParams"];


angular.module("ykyAdminApp")

    .controller("tfAdminLogisticsLossTypesController", tfAdminLogisticsLossTypesController)
    .controller("tfAdminLogisticsLossTypesNewController", tfAdminLogisticsLossTypesNewController)
    .controller("tfAdminLogisticsLossTypesEditController", tfAdminLogisticsLossTypesEditController);