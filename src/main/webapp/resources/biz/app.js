 angular.module("ykyHomeApp", ["ncy-angular-breadcrumb", "ngMessages", "tfCommonModule", "tfDeveloperApp", "tfAdminApp", "tfAnnouncementApp",
    "ykyLossApp", "ykyBuyerApp", "ykySupplierApp", "ykyRefreeApp", "ykyReportsApp", "ykyDashboardApp", "datatables"])
    .config(["$stateProvider", "$urlRouterProvider", "$breadcrumbProvider",
        function ($stateProvider, $urlRouterProvider, $breadcrumbProvider) {
            $urlRouterProvider.otherwise("/");

            // bread crumb
            $breadcrumbProvider.setOptions({
                prefixStateName: 'home',
                templateUrl: '/ng/view/common/breadcrumb.html'
            });

    }])

    .run(["$rootScope", "$http", function($rootScope, $http){
            setInterval(function(){
                $http.get("/common/api/heart-beat").success(function(data){
                    console.log("alive");
                });
            }, (1000 * 60 * 10));

            $rootScope.currentYear = new Date().getFullYear();
            var disegno = undefined;
            $rootScope.sqp = {
                messages: {
                    sqpRule1: "SQP numarası 13 hane ve ilk 4 hanesi 2009 ile " + $rootScope.currentYear
                        + " aralığında olmalıdır!",
                    required: "Boş bırakılamaz",
                    min: "En az {1} veya daha fazla olmalı",
                    discardedPartCanNotBeSame: "Iskarta parça ile kayıp yaşanan parça aynı olamaz"
                },
                rules: {
                    sqpRule1: function(input){
                        if (input.is("[name=sqpno]")) {
                            var value = input.val();
                            var isValid = (!value);

                            if(!isValid) {
                                isValid = (value.toString().length == 0 || value.toString().length === 13);

                                if(isValid) {
                                    var first4Numbers = value.toString().substr(0, 4);
                                    isValid = (+first4Numbers >= 2009 && +first4Numbers <= $rootScope.currentYear);
                                }

                                if(!isValid) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    },
                    discardedPartCanNotBeSame: function(input){
                        if(input.is("[name=part]")){
                            disegno = input.val();
                        }

                        if(input.is("[data-discarded-part=true]")) {
                            var discardedDisegno = input.val();
                            if(disegno == discardedDisegno) {
                                return false;
                            }
                        }

                        return true;
                    }
                },
            };
        }])

    .directive("tfSqpNo", ["$compile", function($compile){
        return {
            restrict: 'A',
            require: 'ngModel',
            link: function($scope, $element, $attrs, ngModel) {

                $scope.$watch($attrs.ngModel, function(value) {
                    var isValid = (!value);

                    if(!isValid) {
                        isValid = (value.toString().length == 0 || value.toString().length === 13);

                        if(!isValid) {
                            $element[0].setCustomValidity("SQP Numarası 13 hane olmalı");
                        } else {
                            var currentYear = new Date().getFullYear();
                            var first4Numbers = value.toString().substr(0, 4);
                            isValid = (+first4Numbers >= 2009 && +first4Numbers <= currentYear);

                            if(!isValid) {
                                $element[0].setCustomValidity("SQP Numarasının ilk 4 hanesi 2009 veya " + currentYear + " aralığında olmalıdır.");
                            }
                        }
                    }

                    if(isValid) {
                        $element[0].setCustomValidity("");
                    }

                    ngModel.$setValidity($attrs.ngModel, isValid);
                });
            }
        };
    }])

    .directive('tfHideIfSupplier', ['tfAuthService', function (tfAuthService) {
        return {
            restrict: 'EA',
            link: function (scope, element, attr) {
                scope.$watch(function(){
                    return tfAuthService.getRoles();
                }, function(newValue, oldValue){
                    if(tfAuthService.hasAnyRole(["TFG_YKY_BUYER", "TFG_YKY_REF", "TFG_YKY_LC_PRODUCTION",
                                "TFG_YKY_REPORT", "TFG_YKY_LC_LOGISTICS", "TFG_YKY_APPROVAL_QUALITY",
                                "TFG_YKY_APPROVAL_LOGISTICS", "TFG_YKY_LC_QUALITY", "TFG_YKY_LC_QUALITY_LAB"])){
                        element.show();
                    } else {
                        element.hide();
                    }
                });
            }
        };
    }])

    .directive('tfIsSupplierUser', ['tfAuthService', function (tfAuthService) {
        return {
            restrict: 'EA',
            link: function (scope, element, attr) {
                scope.$watch(function(){
                    return tfAuthService.getUserName();
                }, function(newValue, oldValue){
                    var username = tfAuthService.getUserName();
                    if(username && username.length > 0 && username.charAt(0) == 'S'){
                        element.show();
                    } else {
                        element.hide();
                    }
                });
            }
        };
    }])

    // Setup the filter
    .filter('tfTryCurrency', function() {

        // Create the return function
        // set the required parameter name to **number**
        return function(number) {

            // Ensure that the passed in data is a number
            if(isNaN(number)) {

                // If the data is not a number or is less than one (thus not having a cardinal value) return it unmodified.
                return number;

            } else {

                // If the data we are applying the filter to is a number, set fixed numbers and add try sign.
                //number = number.toFixed(2);
                return kendo.toString(number, 'n2') + ' \u20BA';
            }
        }
    })

    // setup euro filter
    .filter('tfEurCurrency', function() {

        // Create the return function
        // set the required parameter name to **number**
        return function(number) {

            // Ensure that the passed in data is a number
            if(isNaN(number)) {

                // If the data is not a number or is less than one (thus not having a cardinal value) return it unmodified.
                return number;

            } else {

                // If the data we are applying the filter to is a number, set fixed numbers and add try sign.
                //number = number.toFixed(2);
                return kendo.toString(number, 'n2') + ' \u20AC';
            }
        }
    })


    .directive("tfOption", [function(){
        return {
            require: '^tfSelect',
            restrict: 'E',
            replace: true,
            scope: {},
            link: function(scope, element, attribute, tfSelectController, transclude){
                scope.value = attribute.value;
                scope.text = attribute.text;


                scope.selectElement = function(value, text){
                    tfSelectController.selectElement(value, text);
                };

                scope.isItemSelected = function(value){
                    return tfSelectController.isItemSelected(value);
                };
            },
            template: '<li data-value="{{value}}"><a ng-click="selectElement(value, text)">' +
                        '<span class="glyphicon glyphicon-ok" ng-show="isItemSelected(value)"></span>&nbsp;{{text}}</a></li>'
        };
    }])

    .directive("tfSelect", [function(){
        return {
            restrict: 'E',
            transclude: true,
            scope: {
                filterParam : "=",
                multiple: "=?",
                tfModel: "="
            },
            controller: function($scope, $element, $transclude){
                $element.find('span').replaceWith($transclude());

                var vm = this;
                vm.selectedElements = {};


                vm.selectElement = function(value, text){
                    if($scope.isMultiple()) {
                        if(vm.selectedElements[value] != undefined) {
                            delete vm.selectedElements[value];
                            $scope.tfModel.splice($scope.tfModel.indexOf(value), 1);
                        } else {
                            vm.selectedElements[value] = text;
                            $scope.tfModel.push(value);
                        }
                    } else {
                        if(vm.selectedElements[value] == text) {
                            vm.selectedElements = [];
                            $scope.tfModel = undefined;
                        } else {
                            vm.selectedElements = [];
                            vm.selectedElements[value] = text;
                            $scope.tfModel = value;
                        }
                    }
                };

                vm.isItemSelected = function(value){
                    return vm.selectedElements[value] != undefined;
                };

                $scope.getSelectedText = function(){
                    if(Object.keys(vm.selectedElements).length == 0) {
                        return "Please select";
                    } else if(Object.keys(vm.selectedElements).length == 1) {
                        var selectedText = "";
                        for(var prop in vm.selectedElements) {
                            selectedText += vm.selectedElements[prop];
                        }
                        return selectedText;
                    } else {
                        return Object.keys(vm.selectedElements).length + " selected.";
                    }

                };

                $scope.isMultiple = function(){
                    return $scope.multiple;
                };


                if($scope.tfModel == undefined && $scope.isMultiple()) {
                    $scope.tfModel = [];
                }
            },
            template: '<div class="dropdown" style="width: %100;"><button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1"' +
                '       data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">{{getSelectedText()}} ' +
                '   <span class="caret"></span> </button> <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">' +
                '   <li><input type="text" ng-model="filterParam" class="form-control" /></li> <span></span>' +
                '   </ul>',
        };
    }]);