/**
 * Created by t40127 on 17.03.2017.
 */

var ykyAttachmentsController = function ($scope, $rootScope, $http, tfAuthService, tfContextRootService) {

    $scope.attachmentOptions = {
        saveUrl: tfContextRootService.getUrl('/loss/api/upload-file'),
        removeUrl: tfContextRootService.getUrl('/loss/api/delete-cached'),
        autoUpload: true
    };

    $scope.onUpload = function (e) {
        console.log("upload triggered");

        var xhr = e.XMLHttpRequest;
        if (xhr) {
            xhr.addEventListener("readystatechange", function onReady(e) {
                if (xhr.readyState == 1 /* OPENED */) {
                    xhr.setRequestHeader("X-CSRF-TOKEN", tfAuthService.getCsrfToken());
                }
            });

        }
    };
};

ykyAttachmentsController.$inject = ["$scope", "$rootScope", "$http", "tfAuthService", "tfContextRootService"];

var ykyLossViewAttachmentController = function ($scope, $controller, $modalInstance) {
    $controller("ykyAttachmentsController", {$scope: $scope});

    $scope.ok = function () {
        $modalInstance.close();
    };


    $scope.cancel = function () {
        $modalInstance.dismiss();
    };

};
ykyLossViewAttachmentController.$inject = ["$scope", "$controller", "$modalInstance"];


angular.module("ykyLossCommonComponents")
    .controller("ykyAttachmentsController", ykyAttachmentsController)
    .controller("ykyLossViewAttachmentController", ykyLossViewAttachmentController);