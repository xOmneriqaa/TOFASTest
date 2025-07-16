/**
 * Created by t40127 on 17.03.2017.
 */


tfAdminObjectionTypesController = function ($scope, $state, $modal, ykyObjectionTypeFactory, tfBaseCrudListController) {
    return tfBaseCrudListController($scope, $state, ykyObjectionTypeFactory, $modal);
};
tfAdminObjectionTypesController.$inject = ["$scope", "$state", "$modal", "ykyObjectionTypeFactory", "tfBaseCrudListController"];

tfAdminObjectionTypesNewController = function ($scope, $state, ykyObjectionTypeFactory, tfBaseCrudNewController) {
    $scope.addResponsible = function(){
        $scope.item.responsibles.push("");
    };

    $scope.removeResponsible = function(index){
        $scope.item.responsibles = $scope.item.responsibles.splice(index, 1);
    };

    return tfBaseCrudNewController($scope, $state, ykyObjectionTypeFactory, {}, "tfAdmin.objectionTypes");
};
tfAdminObjectionTypesNewController.$inject = ["$scope", "$state", "ykyObjectionTypeFactory", "tfBaseCrudNewController"];

tfAdminObjectionTypesEditController = function ($scope, $state, ykyObjectionTypeFactory, tfBaseCrudEditController, $stateParams) {
    $scope.addResponsible = function(){
        $scope.item.responsibles.push("");
    };

    $scope.removeResponsible = function(index){
        $scope.item.responsibles = $scope.item.responsibles.splice(index, 1);
    };

    return tfBaseCrudEditController($scope, $state, ykyObjectionTypeFactory, {id: $stateParams.id}, "tfAdmin.objectionTypes");
};
tfAdminObjectionTypesEditController.$inject = ["$scope", "$state", "ykyObjectionTypeFactory", "tfBaseCrudEditController", "$stateParams"];


angular.module("ykyAdminApp")

    .controller("tfAdminObjectionTypesController", tfAdminObjectionTypesController)
    .controller("tfAdminObjectionTypesNewController", tfAdminObjectionTypesNewController)
    .controller("tfAdminObjectionTypesEditController", tfAdminObjectionTypesEditController);