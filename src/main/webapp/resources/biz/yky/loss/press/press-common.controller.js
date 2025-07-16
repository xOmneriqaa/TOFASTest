/**
 * Created by t40127 on 17.03.2017.
 */


var pressCommonController = function($scope, tfAuthService, tfContextRootService, $http, $modal, commonDataSourceService, $state){
    $scope.lossDetails = [];
    $scope.uploadErrors = [];
    $scope.isValidated = false;

    $scope.hrdParams = [];
    $scope.pressLoss = {
        supplierDto: {
            sapCode: ''
        },
        firmType: undefined,
        firmDescription: '',
        lossDate: undefined,
        lossDescription: ''
    };



    var saveUrlVar = $scope.isLogistics ? tfContextRootService.getUrl('/loss/press/api/logistics/upload')
        : tfContextRootService.getUrl('/loss/press/api/quality/upload');


    $scope.addExcel = function(){
        $modal.open({
            templateUrl: "/ng/view/yky/loss/press/add-excel.html",
            controller: "pressUploadExcelModalController",
            resolve: {
                saveUrl: function(){
                    return saveUrlVar;
                }
            }
        }).result.then(function(){
            $scope.getDetails();
            $scope.getErrors();
            $scope.checkValidation();
        });
    };

    $http.get("/loss/press/api/hrd-params").success(function(data){
        $scope.hrdParams.push.apply($scope.hrdParams, data);
    });



    $scope.getDetails = function(){
        $http.get("/loss/press/api/get-details").success(function(data){
            $scope.lossDetails.push.apply($scope.lossDetails, data);
        });
    };

    $scope.getErrors = function(){
        $http.get("/loss/press/api/get-errors").success(function(data){
            $scope.uploadErrors.push.apply($scope.uploadErrors, data);
        });
    };

    $scope.updateDetails = function(){
        $scope.updateDetailsWithCallback(undefined);
    };

    $scope.updateDetailsWithCallback = function(callback){
        for(var i =0; i < $scope.lossDetails.length; i++) {
            $scope.lossDetails[i].type = $scope.isLogistics ? 'logistics' : 'quality';
        }

        if($scope.isLogistics) {
            console.log($scope.lossDetails);
            $http.post("/loss/press/api/update-details-l", $scope.lossDetails).success(function(data){
                $scope.lossDetails.splice(0, $scope.lossDetails.length);
                $scope.lossDetails.push.apply($scope.lossDetails, data);

                $scope.checkValidation();
                if(callback){
                    callback();
                }
            });
        } else {
            console.log($scope.lossDetails);
            $http.post("/loss/press/api/update-details-q", $scope.lossDetails).success(function(data){
                $scope.lossDetails.splice(0, $scope.lossDetails.length);
                $scope.lossDetails.push.apply($scope.lossDetails, data);

                $scope.checkValidation();
                if(callback){
                    callback();
                }
            });
        }
    };

    $scope.addDetail = function(){
        $scope.isValidated = false;
        $scope.lossDetails.push({
            disegnoBasePriceDiff: 0
        });
    };

    $scope.save = function(){
        $scope.updateDetailsWithCallback(function(){
            var lossObject = $scope.pressLoss;
            lossObject.details = $scope.lossDetails;
            lossObject.pressLossType = $scope.isLogistics ? 'LOGISTICS' : 'QUALITY';



            $http.post("/loss/press/api/save", lossObject).success(function(data){
                if(data){
                    $state.go("losses.view", {id: data});
                }
            });

        });
    };

    $scope.isValid = function(){
        return $scope.isValidated;// && $scope.lossDetails.length > 0 && $scope.validator.validate();
    };

    $scope.checkValidation = function(){
        $scope.isValidated = $scope.lossDetails.length > 0 && $scope.validator.validate();
    };

    // run validation for one time to display error messages at start
    setTimeout(function(){
        $scope.validator.validate();
    }, 1000);
};
pressCommonController.$inject = ["$scope", "tfAuthService", "tfContextRootService", "$http", "$modal", "commonDataSourceService", "$state"];


angular.module("ykyPressModule")
    .controller("pressCommonController", pressCommonController);