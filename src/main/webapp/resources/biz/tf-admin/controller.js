var tfAdminParamsController = function($scope, $state, $modal, tfAdminAppParamFactory, tfBaseCrudListController){
    return tfBaseCrudListController($scope, $state, tfAdminAppParamFactory, $modal);
};
tfAdminParamsController.$inject = ["$scope", "$state", "$modal", "tfAdminAppParamFactory", "tfBaseCrudListController"];

var tfAdminParamsEditController = function($scope, $state, tfAdminAppParamFactory, tfBaseCrudEditController, $stateParams){
    return tfBaseCrudEditController($scope, $state, tfAdminAppParamFactory, {id: $stateParams.id}, "tfAdmin.appParams");
};
tfAdminParamsEditController.$inject = ["$scope", "$state", "tfAdminAppParamFactory", "tfBaseCrudEditController", "$stateParams"];


// announcements
{
    var tfAdminAnnouncementUploadOptions =  {
        saveUrl: '/tf-admin/api/announcements/upload',
        removeUrl: '/tf-admin/api/announcements/delete-cached',
        autoUpload: true
    };

    var tfAdminAnnouncementRoleOptions = {
        dataTextField: "name",
        dataValueField: "id",
        dataSource: {
            type: "json",
            transport: {
                read: {
                    url: "/tf-admin/api/announcements/list-roles"
                }
            }
        }
    };

    var tfAdminAnnouncementsController = function($scope, $state, $modal, tfAdminAnnouncementFactory, tfBaseCrudListController){
        return tfBaseCrudListController($scope, $state, tfAdminAnnouncementFactory, $modal)
    };
    tfAdminAnnouncementsController.$inject = ["$scope", "$state", "$modal", "tfAdminAnnouncementFactory", "tfBaseCrudListController"];

    var tfAdminAnnouncementsNewController = function($scope, $state, tfAdminAnnouncementFactory, tfBaseCrudNewController, tfAuthService){
        angular.extend($scope, {
            roleOptions: tfAdminAnnouncementRoleOptions,
            uploadOptions: tfAdminAnnouncementUploadOptions,
            onUpload : function(e){
                console.log("upload triggered");

                var xhr = e.XMLHttpRequest;
                if (xhr) {
                    xhr.addEventListener("readystatechange", function onReady(e) {
                        if (xhr.readyState == 1 /* OPENED */) {
                            xhr.setRequestHeader("X-CSRF-TOKEN", tfAuthService.getCsrfToken());
                        }
                    });

                }
            }
        });
        return tfBaseCrudNewController($scope, $state, tfAdminAnnouncementFactory, {}, "tfAdmin.announcements");
    };
    tfAdminAnnouncementsNewController.$inject = ["$scope", "$state", "tfAdminAnnouncementFactory", "tfBaseCrudNewController", "tfAuthService"];

    var tfAdminAnnouncementsEditController = function($scope, $state, tfAdminAnnouncementFactory, tfBaseCrudEditController, $stateParams, $modal, $http, tfAuthService){
        angular.extend($scope, {
            roleOptions: tfAdminAnnouncementRoleOptions,

            confirmAttachmentDelete : function(item){
                var modalInstance = $modal.open({
                    templateUrl : '/ng/view/common/delete-modal-yes-no.html',
                    controller : 'deleteModalYesNoController',
                    resolve : {
                        deletedItem : function() {
                            return item;
                        }
                    }
                });
                modalInstance.result.then(function(item) {
                    $scope.deleteAttachment(item);
                }, function() {
                    console.log('attachment deletion is canceled');
                });
            },

            deleteAttachment: function(item) {
                $http.post("/tf-admin/api/announcements/delete", item).success(function(data){
                    if(data) {
                        $state.reload();
                    }
                });
            },

            uploadOptions: tfAdminAnnouncementUploadOptions,
            onUpload : function(e){
                console.log("upload triggered");

                var xhr = e.XMLHttpRequest;
                if (xhr) {
                    xhr.addEventListener("readystatechange", function onReady(e) {
                        if (xhr.readyState == 1 /* OPENED */) {
                            xhr.setRequestHeader("X-CSRF-TOKEN", tfAuthService.getCsrfToken());
                        }
                    });

                }
            }
        });

        return tfBaseCrudEditController($scope, $state, tfAdminAnnouncementFactory, {id: $stateParams.id}, "tfAdmin.announcements");
    };
    tfAdminAnnouncementsEditController.$inject = ["$scope", "$state", "tfAdminAnnouncementFactory", "tfBaseCrudEditController", "$stateParams", "$modal", "$http", "tfAuthService"];


    var tfAdminAnnouncementViewController = function($scope, $state, $stateParams, tfAdminAnnouncementFactory){
        $scope.item = {};

        tfAdminAnnouncementFactory.get({id: $stateParams.id}, function(data){
            $scope.item = data;
        });
    };
    tfAdminAnnouncementViewController.$inject = ["$scope", "$state", "$stateParams", "tfAdminAnnouncementFactory"];
}


angular.module("tfAdminController", ["ngCookies"])
    .controller("tfAdminParamsController", tfAdminParamsController)
    .controller("tfAdminParamsEditController", tfAdminParamsEditController)
    .controller("tfAdminAnnouncementsController", tfAdminAnnouncementsController)
        .controller("tfAdminAnnouncementViewController", tfAdminAnnouncementViewController)
        .controller("tfAdminAnnouncementsNewController", tfAdminAnnouncementsNewController)
        .controller("tfAdminAnnouncementsEditController", tfAdminAnnouncementsEditController);