// abstract crud list controller object, can be used with ngResource
var tfBaseCrudListController = function(scope, state, ResourceService, modal){
    scope.$state = state;

    scope.items = ResourceService.query();
    scope.confirmDelete = (function(item) {

        var modalInstance = modal.open({
            templateUrl : '/ng/view/common/delete-modal-yes-no.html',
            controller : 'deleteModalYesNoController',
            resolve : {
                deletedItem : function() {
                    return item;
                }
            }
        });

        modalInstance.result.then(function(item) {
            scope.deleteItem(item);
        }, function() {
            console.log('item deletion is canceled');
        });
    });

    scope.deleteItem = function(item) {
        item.$delete(function() {
            scope.items = ResourceService.query();
        });
    };
};

// abstract crud edit controller object, can be used with ngResource
var tfBaseCrudEditController = function(scope, state, ResourceService, getParams, returnState){
    scope.$state = state;

    scope.item = ResourceService.get(getParams);
    scope.updateItem = function() {
        scope.item.$update(function(){
            state.go(returnState, {}, {reload: true});
        });
    };
};

// abstract crud new controller object, can be used with ngResource
var tfBaseCrudNewController = function(scope, state, ResourceService, initParams, returnState){
    scope.$state = state;

    scope.item = new ResourceService(initParams);
    scope.saveItem = function() {
        scope.item.$save({}, function(){
            state.go(returnState, {}, {reload: true});
        });
    };
};

angular.module('tfCrud', [])

    //yes-no-modal controller
    .controller('deleteModalYesNoController', ["$scope", "$modalInstance", "deletedItem", "$translate", function($scope, $modalInstance, deletedItem, $translate) {
        $scope.deletedItem = deletedItem;

        $scope.ok = function() {
            $modalInstance.close($scope.deletedItem);
        };

        $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
        };
    }])

    .constant("tfBaseCrudListController", tfBaseCrudListController)
    .constant("tfBaseCrudNewController", tfBaseCrudNewController)
    .constant("tfBaseCrudEditController", tfBaseCrudEditController);