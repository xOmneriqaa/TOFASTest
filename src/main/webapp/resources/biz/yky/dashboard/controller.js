/**
 * Created by T40127 on 26.11.2015.
 */


var ykyDashboardController = function($scope, $http, $translate, $filter, tfAuthService){
    $scope.annualStatusBasedOptions = undefined;
    $scope.quarterStatusBasedOptions = undefined;
    $scope.monthStatusBasedOptions = undefined;

    var chartRoles = ['TFG_YKY_ADMIN', 'TFG_YKY_BUYER', 'TFG_YKY_REF', 'TFG_YKY_LC_PRODUCTION',
        'TFG_YKY_DURATION_DEF', 'TFG_YKY_PRESS_USER', 'TFG_YKY_REPORT', 'TFG_YKY_DEVELOPER',
        'TFG_YKY_LC_LOGISTICS', 'TFG_YKY_APPROVAL_QUALITY', 'TFG_YKY_APPROVAL_LOGISTICS',
        'TFG_YKY_LC_QUALITY', 'TFG_YKY_LC_QUALITY_LAB'];

    $scope.canSeeCharts = function () {
        return tfAuthService.hasAnyRole(chartRoles);
    };

    var getDefaultChartOptions = function (title, withFormatted, isStacked, isCost) {

        var templateStr = "#= kendo.toString(value, 'n') # \u20BA";
        if(!isCost) {
            templateStr = "#= kendo.toString(value, 'n0') # (dk)";
        }

        var basicChartOptions = {
            theme: "bootstrap",
            title: {
                text: title
            },
            legend: {
                visible: false,
                position: "bottom"
            },
            seriesDefaults: {
                type: "column"
            },
            series: [],
            valueAxis: {
                minorGridLines: {
                    visible: true
                },
                labels: {
                    template: templateStr
                }
            },
            categoryAxis: {
                categories: [],
                labels: {
                    rotation: "auto"
                }
            },
            tooltip: {
                visible: true,
                template: templateStr
            }
        };

        if (isStacked) {
            basicChartOptions.seriesDefaults.stack = true;
        }

        if (withFormatted) {
            basicChartOptions.tooltip.template = templateStr;
        } else {
            if(isCost) {
                basicChartOptions.tooltip.template = function (dataItem) {
                    return dataItem.series.name + ": " + kendo.toString(dataItem.value, 'n') + " \u20BA";
                };
            } else {
                basicChartOptions.tooltip.template = function (dataItem) {
                    return dataItem.series.name + ": " + kendo.toString(dataItem.value, 'n0') + " (dk)";
                };
            }


            basicChartOptions.legend.visible = true;
        }


        return basicChartOptions;
    };

    var mapToBarChartData = function (data) {
        var seriesDataArray = [];
        var categoryDataTranslated = [];
        angular.forEach(data, function (val, i) {
            seriesDataArray.push(val.paramValue);
            categoryDataTranslated.push(val.paramSubName);

            categoryDataTranslated[i] = $translate.instant(val.paramSubName);
        });

        return {
            seriesData: [{
                name: "Durum Bazlı",
                data: seriesDataArray
            }],
            categories: categoryDataTranslated
        }
    };

    var mapToStackedBarChartData = function (data) {
        var allLossTypes = []; // category
        var allStateTypes = []; // serie

        angular.forEach(data, function (val, i) {
            if (allLossTypes.indexOf(val.paramName) < 0) {
                allLossTypes.push(val.paramName);
            }

            if (allStateTypes.indexOf(val.paramSubName) < 0) {
                allStateTypes.push(val.paramSubName);
            }
        });

        allLossTypes.sort();
        allStateTypes.sort();

        var seriesDataArray = [];

        angular.forEach(allStateTypes, function (valState, iState) {
            seriesDataArray[iState] = {};
            seriesDataArray[iState].name = valState;
            seriesDataArray[iState].data = [];

            $translate(valState).then(function (translation) {
                if (translation) {
                    seriesDataArray[iState].name = translation;
                } else {
                    seriesDataArray[iState].name = valState;
                }

            });

            angular.forEach(allLossTypes, function (valLoss, iLoss) {
                seriesDataArray[iState].data[iLoss] = 0;
                angular.forEach(data, function (valData, iData) {
                    if (valData.paramName === valLoss && valData.paramSubName === valState) {
                        var calculatedValue = valData.paramValue.toFixed(2);
                        seriesDataArray[iState].data[iLoss] = +calculatedValue;
                    }
                });
            });

        });

        var categoryDataTranslated = [];
        angular.forEach(allLossTypes, function (val, i) {
            categoryDataTranslated[i] = $translate.instant(val);
        });


        return {
            seriesData: seriesDataArray,
            categories: categoryDataTranslated
        };
    };

    var generateChartOptions = function (options) {
        options.url = "/api/dashboard/get-data/" + options.url;

        $http.get(options.url).success(function (data) {
            var chartData = options.dataMapFunction(data);
            var chartOptions = getDefaultChartOptions(options.title, options.withFormatted, options.isStacked, options.isCost);

            chartOptions.series = chartData.seriesData;
            chartOptions.categoryAxis.categories = chartData.categories;

            options.callback(chartOptions);
        });
    };

    var generateStatusBasedChartOptions = function (period, isCost, callback) {
        generateChartOptions({
            url: (period + "/STATUS_BASED"),
            dataMapFunction: isCost ? mapToBarChartData : mapToStackedBarChartData,
            title: isCost ? "Durum Bazlı Toplam Tutarlar" : "Durum Bazlı Toplam Süreler",
            withFormatted: isCost,
            isStacked: !isCost,
            isCost: isCost,
            callback: callback
        });
    };

    var generateLossBasedChartOptions = function (period, isCost, callback) {
        generateChartOptions({
            url: (period + "/LOSS_BASED"),
            dataMapFunction: mapToStackedBarChartData,
            title: isCost ? "Kayıp/Statü Bazlı Toplam Tutarlar" : "Kayıp/Statü Bazlı Toplam Süreler",
            withFormatted: false,
            isStacked: true,
            isCost: isCost,
            callback: callback
        });
    };

    var generateSupplierBasedChartOptions = function (period, isCost, callback) {
        generateChartOptions({
            url: (period + "/SUPPLIER_BASED"),
            dataMapFunction: mapToStackedBarChartData,
            title: isCost ? "Tedarikçi/Tutar Türü Bazlı Toplam Tutarlar" : "Tedarikçi/Tutar Türü Bazlı Toplam Süreler",
            withFormatted: false,
            isStacked: true,
            isCost: isCost,
            callback: callback
        });
    };

    $scope.getAnnualData = function () {
        if (!$scope.annualStatusBasedOptions) {

            generateStatusBasedChartOptions("YEAR", true, function (chartOptions) {
                $scope.annualStatusBasedOptions = chartOptions;
            });

            generateLossBasedChartOptions("YEAR", true, function (chartOptions) {
                $scope.annualLossBasedOptions = chartOptions;
            });

            generateSupplierBasedChartOptions("YEAR", true, function (chartOptions) {
                $scope.annualSupplierBasedOptions = chartOptions;
            });


            generateStatusBasedChartOptions("YEAR_DURATION", false, function (chartOptions) {
                $scope.annualStatusBasedDurationOptions = chartOptions;
            });

            generateLossBasedChartOptions("YEAR_DURATION", false, function (chartOptions) {
                $scope.annualLossBasedDurationOptions = chartOptions;
            });

            generateSupplierBasedChartOptions("YEAR_DURATION", false, function (chartOptions) {
                $scope.annualSupplierBasedDurationOptions = chartOptions;
            });
        }
    };

    $scope.getQuarterData = function () {
        if (!$scope.quarterStatusBasedOptions) {

            generateStatusBasedChartOptions("QUARTER", true, function (chartOptions) {
                $scope.quarterStatusBasedOptions = chartOptions;
            });

            generateLossBasedChartOptions("QUARTER", true, function (chartOptions) {
                $scope.quarterLossBasedOptions = chartOptions;
            });

            generateSupplierBasedChartOptions("QUARTER", true, function (chartOptions) {
                $scope.quarterSupplierBasedOptions = chartOptions;
            });



            generateStatusBasedChartOptions("QUARTER_DURATION", false, function (chartOptions) {
                $scope.quarterStatusBasedDurationOptions = chartOptions;
            });

            generateLossBasedChartOptions("QUARTER_DURATION", false, function (chartOptions) {
                $scope.quarterLossBasedDurationOptions = chartOptions;
            });

            generateSupplierBasedChartOptions("QUARTER_DURATION", false, function (chartOptions) {
                $scope.quarterSupplierBasedDurationOptions = chartOptions;
            });
        }
    };

    $scope.getMonthData = function () {
        if (!$scope.monthStatusBasedOptions) {

            generateStatusBasedChartOptions("MONTH", true, function (chartOptions) {
                $scope.monthStatusBasedOptions = chartOptions;
            });

            generateLossBasedChartOptions("MONTH", true, function (chartOptions) {
                $scope.monthLossBasedOptions = chartOptions;
            });

            generateSupplierBasedChartOptions("MONTH", true, function (chartOptions) {
                $scope.monthSupplierBasedOptions = chartOptions;
            });



            generateStatusBasedChartOptions("MONTH_DURATION", false, function (chartOptions) {
                $scope.monthStatusBasedDurationOptions = chartOptions;
            });

            generateLossBasedChartOptions("MONTH_DURATION", false, function (chartOptions) {
                $scope.monthLossBasedDurationOptions = chartOptions;
            });

            generateSupplierBasedChartOptions("MONTH_DURATION", false, function (chartOptions) {
                $scope.monthSupplierBasedDurationOptions = chartOptions;
            });
        }
    };



};
ykyDashboardController.$inject = ["$scope", "$http", "$translate", "$filter", "tfAuthService"];

angular.module("ykyDashboardControllers", [])
    .controller("ykyDashboardController", ykyDashboardController);