/**
 * Created by t40127 on 17.03.2017.
 */


var ykyCommonLossController = function($scope) {

    // abstract
    $scope.getTotalDiscardedPartCost = function(){ return 0; };
    $scope.getQualityLabTestCost = function(){ return 0; };
    $scope.getDiscardedPartCount = function(){ return 0; };
    $scope.getTotalWorkCost = function(){ return 0; };
    $scope.getTotalCostOfSteps = function(){ return 0; };


    // loss date config
    var minDate = new Date();
    minDate.setMonth(minDate.getMonth() - 1);

    var maxDate = new Date();
    maxDate.setDate(maxDate.getDate() + 1);
    $scope.lossDateOptions = {
        min: minDate,
        max: maxDate
    };

    $scope.isLossCostValid = function(){
        return $scope.getTotalLossCost() > 0 || $scope.getDiscardedPartCountIfExists() > 0;
    };

    $scope.getDiscardedPartCountIfExists = function(){
        var discardedPartCount = $scope.getDiscardedPartCount();
        discardedPartCount = discardedPartCount ? discardedPartCount : 0;
        return discardedPartCount;
    };

    $scope.getTotalLossCost = function(){
        var workCost = $scope.getTotalWorkCost();
        workCost = workCost ? workCost : 0;

        var discardCost = $scope.getTotalDiscardedPartCost();
        discardCost = discardCost ? discardCost : 0;

        var qualityLabTestCost = $scope.getQualityLabTestCost();
        qualityLabTestCost = qualityLabTestCost ? qualityLabTestCost : 0;

        var costOfSteps = $scope.getTotalCostOfSteps();
        costOfSteps = costOfSteps ? costOfSteps : 0;

        var totalOtherCosts = $scope.getTotalOtherCosts();
        totalOtherCosts = totalOtherCosts ? totalOtherCosts : 0;

        return workCost + discardCost + qualityLabTestCost + costOfSteps + totalOtherCosts;
    };
};
ykyCommonLossController.$inject = ["$scope"];


angular.module("ykyLossCommonComponents")
    .controller("ykyCommonLossController", ykyCommonLossController)