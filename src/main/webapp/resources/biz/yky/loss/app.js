angular.module("ykyLossApp", ["ykyLossCommonComponents", "ykyPressModule"])
    .config(["$stateProvider", function ($stateProvider) {

        $stateProvider.state("losses", {
            url: "/losses",
            templateUrl: "/ng/view/yky/loss/index.html",
            ncyBreadcrumb: {
                label: "{{'losses'|translate}}"
            },
            data: {
                isAuthenticated: true,
            }
        }).state("losses.newQuality", {
            url: "/new/quality",
            templateUrl: "/ng/view/yky/loss/quality/new-quality-loss.html",
            controller: "newQualityLossController",
            ncyBreadcrumb: {
                label: "{{'addQualityLoss'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_LC_QUALITY']
            }
        }).state("losses.newLogistics", {
            url: "/new/logistics",
            templateUrl: "/ng/view/yky/loss/logistics/new-logistics-loss.html",
            controller: "newLogisticLossController",
            ncyBreadcrumb: {
                label: "{{'addLogisticsLoss'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_LC_LOGISTICS']
            }
        }).state("losses.newQualityLab", {
            url: "/new/quality-lab",
            templateUrl: "/ng/view/yky/loss/quality-lab/new-quality-lab-loss.html",
            controller: "newQualityLabLossController",
            ncyBreadcrumb: {
                label: "{{'addQualityLabLoss'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_LC_QUALITY_LAB']
            }
        }).state("losses.newProduction", {
            url: "/new/production",
            templateUrl: "/ng/view/yky/loss/production/new-production-loss.html",
            controller: "newProductionLossController",
            ncyBreadcrumb: {
                label: "{{'addProductionLoss'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_LC_PRODUCTION']
            }
        }).state("losses.newPressQuality", {
            url: "/new/press/quality",
            templateUrl: "/ng/view/yky/loss/press/new-press-quality-loss.html",
            controller: "newPressQualityLossController",
            ncyBreadcrumb: {
                label: "{{'addPressQualityLoss'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_PRESS_USER']
            }
        }).state("losses.newPressLogistics", {
            url: "/new/press/logistics",
            templateUrl: "/ng/view/yky/loss/press/new-press-logistics-loss.html",
            controller: "newPressLogisticsLossController",
            ncyBreadcrumb: {
                label: "{{'addPressLogisticsLoss'|translate}}"
            },
            data: {
                roles: ['TFG_YKY_PRESS_USER']
            }
        })

            // view
            .state("losses.view", {
                url: "/view/:id",
                controller: "ykyLossViewController",
                templateUrl: "/ng/view/yky/loss/view.html",
                ncyBreadcrumb: {
                    label: "{{'viewLoss'|translate}}"
                },
                data: {
                    isAuthenticated: true
                }
            }).state("losses.view.quality", {
                url: "/quality",
                templateUrl: "/ng/view/yky/loss/quality/view.html",
                controller: "ykyLossViewController",
                ncyBreadcrumb: {
                    label: "{{'viewQualityLoss'|translate}}"
                }
            }).state("losses.view.production", {
                url: "/production",
                templateUrl: "/ng/view/yky/loss/production/view.html",
                controller: "ykyLossViewController",
                ncyBreadcrumb: {
                    label: "{{'viewProductionLoss'|translate}}"
                }
            }).state("losses.view.logistic", {
                url: "/logistic",
                templateUrl: "/ng/view/yky/loss/logistics/view.html",
                controller: "ykyLossViewController",
                ncyBreadcrumb: {
                    label: "{{'viewLogisticsLoss'|translate}}"
                }
            }).state("losses.view.qualityLab", {
                url: "/quality-lab",
                templateUrl: "/ng/view/yky/loss/quality-lab/view.html",
                controller: "ykyLossViewController",
                ncyBreadcrumb: {
                    label: "{{'viewQualityLabLoss'|translate}}"
                }
            }).state("losses.view.press", {
                url: "/press",
                templateUrl: "/ng/view/yky/loss/press/view.html",
                controller: "ykyLossViewController",
                ncyBreadcrumb: {
                    label: "{{'viewPressLoss'|translate}}"
                }
            }).state("losses.notFound", {
                url: "/not-found/:id",
                templateUrl: "/ng/view/yky/loss/not-found.html",
                controller: "ykyLossesNotFoundController",
                ncyBreadcrumb: {
                    label: "{{'lossNotFound'|translate}}"
                }
            })

            //non approved and un-completed losses
            .state("losses.unApprovedLosses", {
                url: "/un-approved-losses",
                templateUrl: "/ng/view/yky/loss/listing/list-un-approved.html",
                controller: "ykyUnApprovedLossesController",
                ncyBreadcrumb: {
                    label: "{{'listUnApprovedLosses'|translate}}"
                },
                data: {
                    roles: ['TFG_YKY_APPROVAL_QUALITY', 'TFG_YKY_APPROVAL_LOGISTICS']
                }
            }).state("losses.inCompletedLosses", {
                url: "/un-completed-losses",
                templateUrl: "/ng/view/yky/loss/listing/list-in-completed.html",
                controller: "ykyInCompletedLossesController",
                ncyBreadcrumb: {
                    label: "{{'listInCompletedLosses'|translate}}"
                },
                data: {
                    roles: ['TFG_YKY_LC_PRODUCTION']
                }
            })

            // list open press losses
            .state("losses.listOpenPressLosses", {
                url: "/press-open-losses",
                templateUrl: "/ng/view/yky/loss/press/press-open-losses.html",
                controller: "ykyPressOpenLossesController",
                ncyBreadcrumb: {
                    label: "{{'listOpenPressLosses'|translate}}"
                },
                data: {
                    roles: ['TFG_YKY_PRESS_USER']
                }
            })

            .state("losses.editPressLoss", {
                url: "/press/edit/:id",
                templateUrl: "/ng/view/yky/loss/press/edit.html",
                controller: "editPressController",
                ncyBreadcrumb: {
                    label: "{{'editPress'|translate}}"
                },
                data: {
                    roles: ['TFG_YKY_PRESS_USER']
                }
            })
            ;
    }]);
