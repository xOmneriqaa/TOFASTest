/**
 * Created by t40127 on 17.03.2017.
 */

var ykyWorkDurationsController = function ($scope, $http, commonDataSourceService) {
    $scope.workTypes = commonDataSourceService.getWorkTypes();
    $scope.workDurations = [];

    // biz
    $scope.addNewWorkDuration = function () {
        $scope.workDurations.push({
            qty: 1,
            workDuration: 0
        });
    };

    $scope.removeWorkDuration = function (i) {
        $scope.workDurations.splice(i, 1);
    };

    $scope.getTotalWorkTime = function () {
        var totalTime = 0;
        for (var i = 0; i < $scope.workDurations.length; i++) {
            totalTime += ($scope.workDurations[i].qty * $scope.workDurations[i].workDuration);
        }
        return totalTime;
    };

    $scope.getTotalWorkCost = function () {
        var totalCost = 0;
        for (var i = 0; i < $scope.workDurations.length; i++) {
            for (var j = 0; j < $scope.workTypes.length; j++) {
                if ($scope.workDurations[i].workType == $scope.workTypes[j].id) {
                    totalCost += ($scope.workTypes[j].wageInMinutes * $scope.workDurations[i].qty * $scope.workDurations[i].workDuration);
                    break;
                }
            }
        }
        totalCost = totalCost / 60;
        return totalCost;
    };

    $scope.getLaborPanelClass = function () {
        var totalCost = $scope.getTotalWorkCost();

        if (totalCost) {
            if (totalCost > 10000) {
                return "panel panel-danger";
            } else if (totalCost > 1000) {
                return "panel panel-warning";
            } else {
                return "panel panel-info";
            }
        } else {
            return "panel panel-default";
        }
    };
};
ykyWorkDurationsController.$inject = ["$scope", "$http", "commonDataSourceService"];

angular.module("ykyLossCommonComponents")
    .controller("ykyWorkDurationsController", ykyWorkDurationsController);