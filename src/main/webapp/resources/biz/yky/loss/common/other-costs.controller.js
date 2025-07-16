/**
 * Created by t40127 on 31.03.2017.
 */

(function () {
    
    var ykyOtherCostsController = function ($scope) {
        $scope.otherCosts = [];

        $scope.addOtherCost = function () {
            $scope.otherCosts.push({
                description: "",
                cost: 0
            });
        };

        $scope.removeOtherCost = function (i) {
            $scope.otherCosts.splice(i, 1);
        };

        $scope.getTotalOtherCosts = function () {
            var totalOtherCost = 0;
            for(var i = 0; i < $scope.otherCosts.length; i++) {
                if($scope.otherCosts[i].cost) {
                    totalOtherCost += $scope.otherCosts[i].cost;
                }
            }

            return totalOtherCost;
        };

    };

    ykyOtherCostsController.$inject = ["$scope"];
    
    
    angular.module("ykyLossCommonComponents")
        .controller("ykyOtherCostsController", ykyOtherCostsController);
    
})();

