/**
 * Created by t40127 on 17.03.2017.
 */


tfAdminQualityLabTestsController = function ($scope, $state, $modal, ykyQualityLabTestFactory, tfBaseCrudListController) {
    return tfBaseCrudListController($scope, $state, ykyQualityLabTestFactory, $modal);
};
tfAdminQualityLabTestsController.$inject = ["$scope", "$state", "$modal", "ykyQualityLabTestFactory", "tfBaseCrudListController"];

tfAdminQualityLabTestsNewController = function ($scope, $state, ykyQualityLabTestFactory, tfBaseCrudNewController) {
    return tfBaseCrudNewController($scope, $state, ykyQualityLabTestFactory, {}, "tfAdmin.qualityLabTests");
};
tfAdminQualityLabTestsNewController.$inject = ["$scope", "$state", "ykyQualityLabTestFactory", "tfBaseCrudNewController"];

tfAdminQualityLabTestsEditController = function ($scope, $state, ykyQualityLabTestFactory, tfBaseCrudEditController, $stateParams) {
    return tfBaseCrudEditController($scope, $state, ykyQualityLabTestFactory, {id: $stateParams.id}, "tfAdmin.qualityLabTests");
};
tfAdminQualityLabTestsEditController.$inject = ["$scope", "$state", "ykyQualityLabTestFactory", "tfBaseCrudEditController", "$stateParams"];

angular.module("ykyAdminApp")

    .controller("tfAdminQualityLabTestsController", tfAdminQualityLabTestsController)
    .controller("tfAdminQualityLabTestsNewController", tfAdminQualityLabTestsNewController)
    .controller("tfAdminQualityLabTestsEditController", tfAdminQualityLabTestsEditController);