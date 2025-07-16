/**
 * Created by t40127 on 17.03.2017.
 */


var pressUploadExcelModalController = function($scope, $modalInstance, tfContextRootService, tfAuthService, saveUrlVar){

    $scope.attachmentOptions = {
        removeUrl: tfContextRootService.getUrl('/loss/api/delete-cached'),
        autoUpload: true,
        saveUrl: saveUrlVar
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

    $scope.close = function(){
        $modalInstance.close();
    };

    $scope.onSuccess = function(e){
        $scope.close();
    };


};
pressUploadExcelModalController.$inject = ["$scope", "$modalInstance", "tfContextRootService", "tfAuthService", "saveUrl"];


angular.module("ykyPressModule")
    .controller("pressUploadExcelModalController", pressUploadExcelModalController);