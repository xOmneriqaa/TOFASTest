/**
 * Created by t40127 on 17.03.2017.
 */


var ykyLossViewController = function($scope, $state, $stateParams, $http, $modal, tfAuthService, commonDataSourceService){

    if($scope.isDetailCalled == undefined) {
        $scope.isDetailCalled = 0
        $scope.totalLossCost = 0;
        $scope.loss = {};
        $scope.type = "";
        $scope.approveButtonActive = true;

        $scope.shiftCodes = [];                                             // production
        $scope.completion = {};                                             // production

        $scope.objectionTypes = [];                                         // supplier

        $scope.blueCollarWage = function(){
            return commonDataSourceService.getBlueCollarWage();
        };

        $http.get("/common/api/objection-types").success(function(data){
            $scope.objectionTypes = data;
        });

    } else {
        $scope.isDetailCalled++;
    }

    if($scope.isDetailCalled == 0) {



        $http.get(("/loss/api/type/" + $stateParams.id)).success(function(data){
            if(data) {
                $scope.type = data;

                $scope.approveButtonActive = true

                if(data == "QUALITY") {
                    $state.go("losses.view.quality", {id: $stateParams.id});
                    $http.get(("/loss/api/quality/get/" + $stateParams.id)).success(function(data){

                        if(!data) {
                            $state.go("losses.notFound", {id: $stateParams.id});
                        }

                        $scope.loss = data;
                    });
                } else if(data == "PRODUCTION") {
                    $state.go("losses.view.production", {id: $stateParams.id});
                    $http.get(("/loss/api/production/get/" + $stateParams.id)).success(function(data){

                        if(!data) {
                            $state.go("losses.notFound", {id: $stateParams.id});
                        }

                        $scope.loss = data;
                    });
                } else if(data == "LOGISTICS") {
                    $state.go("losses.view.logistic", {id: $stateParams.id});
                    $http.get(("/loss/api/logistic/get/" + $stateParams.id)).success(function(data){

                        if(!data) {
                            $state.go("losses.notFound", {id: $stateParams.id});
                        }

                        $scope.loss = data;
                    });
                } else if(data == "QUALITY_LAB") {
                    $state.go("losses.view.qualityLab", {id: $stateParams.id});
                    $http.get(("/loss/api/quality-lab/get/" + $stateParams.id)).success(function(data){

                        if(!data) {
                            $state.go("losses.notFound", {id: $stateParams.id});
                        }

                        if(data.qualityLabApprover){
                            data.qualityLabApprover.approverRoleName = $scope.getApproverRoleName(data.qualityLabApprover.approverRole)
                        }
                        $scope.loss = data;

                    });
                } else if(data == "PRESS") {
                    $state.go("losses.view.press", {id: $stateParams.id});
                    $http.get(("/loss/api/press/get/" + $stateParams.id)).success(function(data){

                        if(!data) {
                            $state.go("losses.notFound", {id: $stateParams.id});
                        }

                        $scope.loss = data;
                    });
                }



            } else {
                $state.go("losses.notFound", {id: $stateParams.id});
            }
        });
    }

    $scope.getApproverRoleName = function(role){
        if(role.includes("DESK")) return "Montaj Masası"
        else if(role.includes("SAMPLE")) return "Numune Masası"
        else if(role.includes("AFTERSALES")) return "Satış Sonrası Masası"
        else if(role.includes("SUSPENSION")) return "Süspansiyon Masası"
        else if(role.includes("BODY")) return "Gövde Masası"
    }

    $scope.refreshState = function(){
        $state.go("losses.view", {id: $scope.loss.id}, {reload: true});
    };

    $scope.getLossStateClass = function(){
        if($scope.loss) {

            if($scope.isLossHaveAnyOpenObjection()) {
                return "pull-right label label-danger";
            } else {
                if($scope.loss.currentState === "OPEN"
                    || $scope.loss.currentState === "PAUSED") {
                    return "pull-right label label-warning";
                } else if($scope.loss.currentState === "APPROVED") {
                    return "pull-right label label-info";
                } else if($scope.loss.currentState === "INVOICE_APPROVED") {
                    return "pull-right label label-primary";
                } else if($scope.loss.currentState === "INVOICED"
                    ||  $scope.loss.currentState === "PRESS_CLOSED"
                    || $scope.loss.currentState === "EX_FIRM_NOT_INVOICED") {
                    return "pull-right label label-success";
                } else if($scope.loss.currentState === "CANCELED") {
                    return "pull-right label label-danger";
                } else if($scope.loss.currentState === "SUPP_CHAIN") {
                    return "pull-right label label-default";
                }
            }


        } else {
            return "";
        }
    };

    $scope.isLossHaveAnyOpenObjection = function(){
        return $scope.loss && $scope.loss.currentObjection
            && $scope.loss.currentObjection.objectionStatus === "OPEN";
    };

    $scope.isLossPausable = function () {
        return $scope.loss
            && ($scope.loss.currentState === 'APPROVED'
                || $scope.loss.currentState === 'INVOICE_APPROVED'
                || $scope.loss.currentState === 'PAUSED')
            && tfAuthService.hasRole("TFG_YKY_ADMIN");
    };

    $scope.isLossPaused = function () {
        return $scope.loss && $scope.loss.currentState === 'PAUSED';
    };

    $scope.pauseLoss = function () {
        if($scope.isLossPausable()) {
            $modal.open({
                templateUrl: "/ng/view/yky/loss/common/edit/pause-loss-modal.html",
                controller: "ykyPauseLossModal",
                resolve: {
                    paused: function () {
                        return $scope.isLossPaused();
                    }
                }
            }).result.then(function(){
                $http.post(("/loss/api/pause/" + $scope.loss.id)).success(function(data){
                    $state.reload();
                });
            });
        }
    };

    $scope.isLossCancelable = function(){
        return $scope.loss
            &&  (($scope.loss.currentState == 'APPROVED'
                || $scope.loss.currentState == 'OPEN'
                || $scope.loss.currentState == 'PAUSED')

                && (tfAuthService.hasAnyRole(['TFG_YKY_APPROVAL_QUALITY', 'TFG_YKY_APPROVAL_LOGISTICS', 'TFG_YKY_ADMIN'])
                || ($scope.loss.lossType == 'PRESS' && tfAuthService.hasAnyRole(['TFG_YKY_PRESS_USER'])))
                || ($scope.loss.currentState == 'OPEN'

                && tfAuthService.hasAnyRole(['TFG_YKY_APPROVAL_QUALITY', 'TFG_YKY_APPROVAL_LOGISTICS', 'TFG_YKY_ADMIN']))
                || ($scope.loss.currentState == 'INVOICE_APPROVED' && tfAuthService.hasRole('TFG_YKY_ADMIN')   )
            );
    };

    $scope.cancelLoss = function(){
        if($scope.isLossCancelable()) {
            $modal.open({
                templateUrl: "/ng/view/yky/loss/common/edit/cancel-loss-modal.html",
                controller: "ykyCancelLossModal"
            }).result.then(function(cancelDesc){
                $http.post(("/loss/api/cancel/" + $scope.loss.id), cancelDesc).success(function(data){
                    $state.reload();
                });
            }, undefined);
        }
    };

    // attach file
    var fileAttachRoles = ['TFG_YKY_ADMIN', 'TFG_YKY_BUYER', 'TFG_YKY_REF', 'TFG_YKY_LC_PRODUCTION',
        'TFG_YKY_DURATION_DEF', 'TFG_YKY_DEVELOPER', 'TFG_YKY_LC_LOGISTICS', 'TFG_YKY_APPROVAL_QUALITY',
        'TFG_YKY_APPROVAL_LOGISTICS', 'TFG_YKY_LC_QUALITY', 'TFG_YKY_LC_QUALITY_LAB', 'TFG_YKY_SUPPLIER'];

    $scope.canAttachFile = function () {
        return tfAuthService.hasAnyRole(fileAttachRoles);
    };

    $scope.attachNewFile = function () {
        var successCallback = function () {
            $http.get("/loss/api/persist-files/" + $scope.loss.id).success(function (data) {
                $scope.refreshState();
            });
        };

        var dismissCallback = function () {
            $http.get("/loss/api/delete-cached").success(function (data) {
                // no need to refresh
            });
        };

        $modal.open({
            templateUrl: "/ng/view/yky/loss/common/view/add-attachment-later.html",
            controller: "ykyLossViewAttachmentController"
        }).result.then(successCallback, dismissCallback);
    };

    // end attach file


  $scope.showApproval = function(){
        if($scope.loss && $scope.loss.lossType !== "QUALITY_LAB"){
            if($scope.loss && $scope.loss.lossSource && !$scope.isLossApproved()
                        && !$scope.doesLossHaveUnpricedDiscard()) {
                if($scope.loss.lossSource.shortName == 'L') {
                    return tfAuthService.hasRole('TFG_YKY_APPROVAL_LOGISTICS');
                } else if($scope.loss.lossSource.shortName == 'K') {
                    return tfAuthService.hasRole('TFG_YKY_APPROVAL_QUALITY');
                }
            }
        } else if($scope.loss && $scope.loss.lossType === "QUALITY_LAB"){
            //return $scope.loss.currentState === "APPROVED" ? false : true;
            if($scope.loss.currentState === "APPROVED") return false;
            else {
                if($scope.loss.qualityLabApprover){
                    var roles = tfAuthService.getRoles();
                    var hasUserApproverRole = false;
                    roles.forEach(function(role){
                        if(role == $scope.loss.qualityLabApprover.approverRole)
                            hasUserApproverRole = true;
                    })
                    return hasUserApproverRole
                } else return true;
            }
        }

        return false;
    };
  /*  $scope.showApproval = function(){
        if($scope.loss && $scope.loss.lossSource && !$scope.isLossApproved()
            && !$scope.doesLossHaveUnpricedDiscard()) {
            if($scope.loss.lossSource.shortName == 'L') {
                return tfAuthService.hasRole('TFG_YKY_APPROVAL_LOGISTICS');
            } else if($scope.loss.lossSource.shortName == 'K') {
                return tfAuthService.hasRole('TFG_YKY_APPROVAL_QUALITY');
            }
        }

        return false;
    };*/

    $scope.doesLossHaveUnpricedDiscard = function() {
        if($scope.loss && $scope.loss.discardedParts && $scope.loss.discardedParts.length > 0) {
            for(var i = 0; i < $scope.loss.discardedParts.length; i++) {
                if($scope.loss.discardedParts[i].basePrice == null) {
                    return true;
                }
            }
        }

        return false;
    };

    $scope.isLossApproved = function(){
        if($scope.loss && $scope.loss.stateChanges) {
            for(var i = 0; i < $scope.loss.stateChanges.length; i++) {
                if($scope.loss.stateChanges[i].lossState == "APPROVED") {
                    return true;
                }
            }
        }

        return false;
    };

    $scope.insertComment = function(lossComment){
        var comment = {
            message: lossComment,
            lossId: $scope.loss.id
        };
        $http.post("/loss/api/add-comment/", comment).success(function(data){
            if(data) {
                $scope.refreshState();
            }
        });
    };


    // PRODUCTION

    $scope.approveLossModal = function(){
        var modalInstance = $modal.open({
            templateUrl: '/ng/view/yky/loss/production/approveModal.html',
            controller: "ykyApproveModalController",
            resolve: {
                disegno: function(){
                    return $scope.loss.part.disegno;
                },
                lossType: function(){
                    return $scope.loss.lossType;
                },
                willSqpOpen: function() {
                    return $scope.loss.willSqpOpen;
                }
            }
        });

        modalInstance.result.then(function (modalData) {
            if($scope.loss.lossType !== "QUALITY_LAB"){
                 if(modalData.sqpNo && modalData.supplierNo && $scope.loss.id) {
                    $http.get(("/loss/api/approve/" + $scope.loss.id + "/" + modalData.sqpNo + "/" + modalData.supplierNo
                    + "/" + modalData.isSuppChain + "/" + modalData.lossCodeId))
                        .success(function(data){
                            $scope.refreshState();
                        });
                }
            } else {
                if(modalData.sqpNo){
                    $http.get(("/loss/api/approve/" + $scope.loss.id + "/" + modalData.sqpNo )).success(function(data){
                        $scope.refreshState();
                    });
                } else {
                   $http.get(("/loss/api/approve/" + $scope.loss.id )).success(function(data){
                       $scope.refreshState();
                   });
               }
            }

        }, undefined);
    };

    $scope.updateShiftCodes = function(){
        $http.get(("/common/api/shift-codes/" + $scope.completion.completedTut)).success(function(data){
            $scope.shiftCodes = data;
        });
    };

    $scope.getAllTuts = function(){
        var allTuts = [];
        allTuts.push($scope.loss.tut.tut);
        angular.forEach($scope.loss.effectedTuts, function(tut, i){
            allTuts.push(tut);
        });
        return allTuts;
    };

    $scope.getCompletableSteps = function(){
        var allSteps = [];
        angular.forEach($scope.loss.standartDurations, function(duration, i){
            var step = duration.stdStep;

            step.combinedId = "s" + step.id;
            step.maxCompletableQty = duration.qty;

            allSteps.push(step);
        });
        angular.forEach($scope.loss.stepDurations, function(duration, i){
            var step = duration.step;

            step.combinedId = step.id;
            step.maxCompletableQty = duration.qty;

            allSteps.push(step);
        });
        return allSteps;
    };

    $scope.getMaximumCompletionQty = function(){
        if($scope.loss && $scope.completion && $scope.completion.completionStepId) {
            var completionQty = 0;

            var completableSteps = $scope.getCompletableSteps();
            for(var i = 0; i < completableSteps.length; i++){
                var step = completableSteps[i];
                if(step.combinedId == $scope.completion.completionStepId) {
                    completionQty = step.maxCompletableQty;
                    var stepId = $scope.completion.completionStepId;
                    var isStd = 0;

                    if($scope.completion.completionStepId.charAt(0) == 's'){
                        isStd = 1;
                        stepId = $scope.completion.completionStepId.substring(1);
                    }

                    for(var j = 0; j < $scope.loss.lossCompletions.length; j++) {
                        var completion = $scope.loss.lossCompletions[j];
                        if(completion.completionStepId == stepId && isStd == completion.completionStepIsStd){
                            completionQty = completionQty - completion.completionQty;
                        }
                    }
                }
            }
            return completionQty;
        } else {
            return 0;
        }
    };

    $scope.addCompletion = function(){
        $scope.completion.lossId = $scope.loss.id;
        $scope.completion.completionDuration = undefined;
        $scope.completion.completionStepIsStd = 0;
        if($scope.completion.completionStepId.charAt(0) == 's') {
            $scope.completion.completionStepId = $scope.completion.completionStepId.substring(1);
            $scope.completion.completionStepIsStd = 1;
        }


        $http.post("/loss/api/add-completion", $scope.completion).success(function(data){
            if(data) {
                $scope.refreshState();
            }
        });
    };

    $scope.isLossCompleted = function(){
        if($scope.loss){
            var totalCompletedQty = $scope.getTotalCompletedQty();
            var totalCompletableQty = $scope.getTotalCompletableQty();

            if(totalCompletableQty > totalCompletedQty) {
                return false;
            }
        }

        return true;
    };

    $scope.getTotalCompletableQty = function(){
        var totalCompletableQty = 0;
        angular.forEach($scope.loss.standartDurations, function(duration, i){
            totalCompletableQty += duration.qty;
        });

        angular.forEach($scope.loss.stepDurations, function(duration, i){
            totalCompletableQty += duration.qty;
        });

        return totalCompletableQty;
    };

    $scope.getTotalCompletedQty = function(){
        var totalCompletedQty = 0;
        angular.forEach($scope.loss.lossCompletions, function(val, i){
            totalCompletedQty += val.completionQty;
        });
        return totalCompletedQty;
    };

    $scope.canSeeLossCompletion = function(){
        return tfAuthService.hasAnyRole(["TFG_YKY_LC_PRODUCTION", "TFG_YKY_APPROVAL_QUALITY",
            "TFG_YKY_APPROVAL_LOGISTICS"]);
    };

    // END PRODUCTION


    // SUPPLIER



    $scope.doObjection = function(lossObjection){
        lossObjection.lossId = $scope.loss.id;
        lossObjection.lossType = $scope.loss.lossType;
        $http.post("/supplier/api/make-objection", lossObjection).success(function(data){
            $state.go("losses.view", {id: $scope.loss.id}, {reload: true});
        });
    };


    // END SUPPLIER


    // OBJECTION

    $scope.canUserSetObjection = function(objection){

        var userName = tfAuthService.getUserName();
        if(objection) {
            if(objection.objectionType.responsibles) {
                debugger;
                console.log(objection);
                if(objection.objectionType .responsibles.indexOf(userName) >= 0
                    && objection.objectionStatus == "OPEN") {
                    return true;
                }
            }
        }

        return false;
    };

    $scope.setObjectionResult = function(){
        var lossInstance = $scope.loss;
        var objectionTypesInstance = $scope.objectionTypes;

        var modalInstance = $modal.open({
            templateUrl: "/ng/view/yky/loss/common/view/set-objection-result.html",
            controller: "ykyObjectionModalController",
            resolve: {
                loss: function(){
                    return lossInstance;
                },
                objectionTypes: function(){
                    return objectionTypesInstance;
                }
            }
        });

        modalInstance.result.then(function(data){
            if(data) {
                if(data.isChange) {
                    $scope.changeObjection(data.newObjection);
                } else {
                    data.objectionResult.lossId = $scope.loss.id;
                    $scope.saveObjectionResult(data.objectionResult);
                }
            }
        }, function(){
            console.log("canceled");
        });
    };

    $scope.changeObjection = function(result) {
        result.objectionInstanceId = $scope.loss.currentObjection.id;
        $http.post("/refree/api/change-objection", result).success(function(data){
            $scope.refreshState();
        });
    };

    $scope.saveObjectionResult = function(result) {
        result.objectionInstanceId = $scope.loss.currentObjection.id;
        result.status = (result.result == "1");
        $http.post("/refree/api/set-objection-result", result).success(function(data){
            $scope.refreshState();
        });
    };

    $scope.showObjectionPanel = function(){
        return ($scope.loss && $scope.loss.objections && $scope.loss.objections.length) || tfAuthService.hasRole('TFG_YKY_SUPPLIER');
    };

    $scope.showAttachmentsPanel = function(){
        return $scope.loss && $scope.loss.attachments && $scope.loss.attachments.length;
    };



    // END OBJECTION


    // PRESS

    $scope.approvePress = function(){
        $modal.open({
            templateUrl: "/ng/view/yky/loss/press/approve-modal.html",
            controller: "ykyPressApproveModalController",
            resolve: {
                loss: function(){
                    return $scope.loss;
                }
            }
        }).result.then(function(lossData){
            $http.post(("/loss/press/api/approve/" + $scope.loss.id), $scope.loss.details).success(function(data){
                $state.reload();
            });
        });
    };

    // END PRESS
};

ykyLossViewController.$inject = ["$scope", "$state", "$stateParams", "$http", "$modal", "tfAuthService", "commonDataSourceService"];

angular.module("ykyLossApp")
    .controller("ykyLossViewController", ykyLossViewController);