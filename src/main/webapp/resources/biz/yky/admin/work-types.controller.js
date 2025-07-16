/**
 * Created by t40127 on 17.03.2017.
 */

var tfAdminWorkTypesController = function($scope, $state, $modal, ykyWorkTypeFactory, tfBaseCrudListController){
    return tfBaseCrudListController($scope, $state, ykyWorkTypeFactory, $modal);
};
tfAdminWorkTypesController.$inject = ["$scope", "$state", "$modal", "ykyWorkTypeFactory", "tfBaseCrudListController"];

var tfAdminWorkTypesNewController = function ($scope, $state, ykyWorkTypeFactory, tfBaseCrudNewController) {
    return tfBaseCrudNewController($scope, $state, ykyWorkTypeFactory, {}, "tfAdmin.workTypes");
};
tfAdminWorkTypesNewController.$inject = ["$scope", "$state", "ykyWorkTypeFactory", "tfBaseCrudNewController"];

var tfAdminWorkTypesEditController = function ($scope, $state, ykyWorkTypeFactory, tfBaseCrudEditController, $stateParams) {
    return tfBaseCrudEditController($scope, $state, ykyWorkTypeFactory, {id: $stateParams.id}, "tfAdmin.workTypes");
};
tfAdminWorkTypesEditController.$inject = ["$scope", "$state", "ykyWorkTypeFactory", "tfBaseCrudEditController", "$stateParams"];

angular.module("ykyAdminApp")

    .controller("tfAdminWorkTypesController", tfAdminWorkTypesController)
    .controller("tfAdminWorkTypesNewController", tfAdminWorkTypesNewController)
    .controller("tfAdminWorkTypesEditController", tfAdminWorkTypesEditController);