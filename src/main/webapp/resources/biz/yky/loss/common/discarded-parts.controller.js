/**
 * Created by t40127 on 17.03.2017.
 */


var ykyDiscardedPartsController = function($scope, $http, commonSelectionOptions){
    $scope.discardedParts = [];
    $scope.baseUnitPrices = {};

    $scope.discardedPartsSelectionOptions = function(){
        return commonSelectionOptions.partOptions({
            change : function(e){
                // clear discarded part cost
                var selectionIndex = this.element[0].attributes["data-index"].value;
                $scope.discardedParts[selectionIndex].baseUnitPrice = null;

                var selectedVal = e.sender.value();
                if(selectedVal) {
                    $scope.getBaseUnitPrice(selectedVal);
                }
            }
        });
    };

    $scope.addDiscardedPart = function(){
        $scope.discardedParts.push({
            isVoucherValid: false,
            qty: 1,
            disegno: "",
            totalPrice: function(){
                var vm = this;
                if(vm.baseUnitPrice) {
                    return vm.baseUnitPrice.baseUnitPriceInTl * vm.qty;
                } else {
                    return 0;
                }
            }
        });
    };

    $scope.removeDiscardedPart = function(index) {
        $scope.discardedParts.splice(index, 1);
    };

    $scope.checkDiscardPartVoucherNo = function(discardedPart) {
        if(discardedPart.discardedPartVoucherNo) {
            $http.get("/loss/api/discard-vocuher-no/"+discardedPart.discardedPartVoucherNo).success(function(data){
                discardedPart.isVoucherValid = data;
            });
        }
    };

    $scope.onBaseUnitPriceSelect = function(index){
        var disegno = $scope.discardedParts[index].disegno;
        var baseUnitPriceId = $scope.discardedParts[index].basePriceId;
        var baseUnitPrices = $scope.baseUnitPrices[disegno];

        for(var i = 0; i < baseUnitPrices.length; i++) {
            if(baseUnitPrices[i].id == baseUnitPriceId) {
                $scope.discardedParts[index].baseUnitPrice = baseUnitPrices[i];
                return;
            }
        }

        $scope.discardedParts[index].baseUnitPrice = null;
    };

    $scope.getBaseUnitPrice = function(disegno) {
        if(!$scope.baseUnitPrices[disegno]) {
            $http.get("/loss/api/get-part-base-price/" + disegno).success(function(data){
                $scope.baseUnitPrices[disegno] = data;
            });
        }
    };

    $scope.getBaseUnitPricesFor = function(index) {
        var disegno = $scope.getDisegnoAtRow(index);
        if(disegno && $scope.baseUnitPrices[disegno]){
            return $scope.baseUnitPrices[disegno];
        }
    };

    $scope.getDisegnoAtRow = function(index){
        var disegno = $scope.discardedParts[index].disegno;
        if(disegno) {
            return disegno;
        } else {
            "";
        }
    };

    $scope.isBasePricePresent = function(index){
        var disegno = $scope.getDisegnoAtRow(index);
        var unitPrices = $scope.getBaseUnitPricesFor(index);
        return disegno && unitPrices && unitPrices.length > 0;
    };

    $scope.getTotalDiscardedPartCost = function(){
        var totalDisCost = 0;
        angular.forEach($scope.discardedParts, function(val, i){
            totalDisCost += val.totalPrice();
        });

        return totalDisCost;
    };

    $scope.getDiscardedPartCount = function(){
        return $scope.discardedParts.length;
    };

};
ykyDiscardedPartsController.$inject = ["$scope", "$http", "commonSelectionOptions"];


angular.module("ykyLossCommonComponents")
    .controller("ykyDiscardedPartsController", ykyDiscardedPartsController)