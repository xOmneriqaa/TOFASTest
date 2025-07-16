/**
 * Created by t40127 on 17.03.2017.
 */

tfAdminLogisticsAcceptencePointsController = function ($scope, $state, $modal, ykyLogisticsAcceptencePointFactory, tfBaseCrudListController) {
    return tfBaseCrudListController($scope, $state, ykyLogisticsAcceptencePointFactory, $modal);
};
tfAdminLogisticsAcceptencePointsController.$inject = ["$scope", "$state", "$modal", "ykyLogisticsAcceptencePointFactory", "tfBaseCrudListController"];

tfAdminLogisticsAcceptencePointsNewController = function ($scope, $state, ykyLogisticsAcceptencePointFactory, tfBaseCrudNewController) {
    return tfBaseCrudNewController($scope, $state, ykyLogisticsAcceptencePointFactory, {}, "tfAdmin.logisticsAcceptencePoints");
};
tfAdminLogisticsAcceptencePointsNewController.$inject = ["$scope", "$state", "ykyLogisticsAcceptencePointFactory", "tfBaseCrudNewController"];

tfAdminLogisticsAcceptencePointsEditController = function ($scope, $state, ykyLogisticsAcceptencePointFactory, tfBaseCrudEditController, $stateParams) {
    return tfBaseCrudEditController($scope, $state, ykyLogisticsAcceptencePointFactory, {id: $stateParams.id}, "tfAdmin.logisticsAcceptencePoints");
};
tfAdminLogisticsAcceptencePointsEditController.$inject = ["$scope", "$state", "ykyLogisticsAcceptencePointFactory", "tfBaseCrudEditController", "$stateParams"];


angular.module("ykyAdminApp")

    .controller("tfAdminLogisticsAcceptencePointsController", tfAdminLogisticsAcceptencePointsController)
    .controller("tfAdminLogisticsAcceptencePointsNewController", tfAdminLogisticsAcceptencePointsNewController)
    .controller("tfAdminLogisticsAcceptencePointsEditController", tfAdminLogisticsAcceptencePointsEditController);