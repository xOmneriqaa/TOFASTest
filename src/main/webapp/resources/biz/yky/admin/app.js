angular.module("ykyAdminApp", ["ykyAdminService"])
    .config(["$stateProvider", function ($stateProvider) {

        $stateProvider.state("tfAdmin.standartSteps", {
            url: "standart-steps",
            templateUrl: "/ng/view/yky/admin/standart-steps/index.html",
            controller: "tfAdminStandartStepsController",
            ncyBreadcrumb: {
                label: "{{'standartSteps'|translate}}"
            }
        })
            .state("tfAdmin.standartSteps.new", {
                url: "/new",
                templateUrl: "/ng/view/yky/admin/standart-steps/new.html",
                controller: "tfAdminStandartStepsNewController",
                ncyBreadcrumb: {
                    label: "{{'addStandartStep'|translate}}"
                }
            })
            .state("tfAdmin.standartSteps.edit", {
                url: "/:id",
                templateUrl: "/ng/view/yky/admin/standart-steps/edit.html",
                controller: "tfAdminStandartStepsEditController",
                ncyBreadcrumb: {
                    label: "{{'editStandartStep'|translate}}"
                }
            })

            .state("tfAdmin.workTypes", {
                url: "work-types",
                templateUrl: "/ng/view/yky/admin/work-types/index.html",
                controller: "tfAdminWorkTypesController",
                ncyBreadcrumb: {
                    label: "{{'workTypes'|translate}}"
                }
            })
            .state("tfAdmin.workTypes.new", {
                url: "/new",
                templateUrl: "/ng/view/yky/admin/work-types/new.html",
                controller: "tfAdminWorkTypesNewController",
                ncyBreadcrumb: {
                    label: "{{'addWorkType'|translate}}"
                }
            })
            .state("tfAdmin.workTypes.edit", {
                url: "/:id",
                templateUrl: "/ng/view/yky/admin/work-types/edit.html",
                controller: "tfAdminWorkTypesEditController",
                ncyBreadcrumb: {
                    label: "{{'editWorkType'|translate}}"
                }
            })


            .state("tfAdmin.steps", {
                url: "steps",
                templateUrl: "/ng/view/yky/admin/steps/index.html",
                controller: "tfAdminStepsController",
                ncyBreadcrumb: {
                    label: "{{'steps'|translate}}"
                }
            })
            .state("tfAdmin.steps.new", {
                url: "/new",
                templateUrl: "/ng/view/yky/admin/steps/new.html",
                controller: "tfAdminStepsNewController",
                ncyBreadcrumb: {
                    label: "{{'addStep'|translate}}"
                }
            })
            .state("tfAdmin.steps.edit", {
                url: "/:id",
                templateUrl: "/ng/view/yky/admin/steps/edit.html",
                controller: "tfAdminStepsEditController",
                ncyBreadcrumb: {
                    label: "{{'editStep'|translate}}"
                }
            })


            .state("tfAdmin.qualityTables", {
                url: "quality-loss-tables",
                templateUrl: "/ng/view/yky/admin/quality-tables/index.html",
                controller: "tfAdminQualityTablesController",
                ncyBreadcrumb: {
                    label: "{{'qualityTables'|translate}}"
                }
            })
            .state("tfAdmin.qualityTables.new", {
                url: "/new",
                templateUrl: "/ng/view/yky/admin/quality-tables/new.html",
                controller: "tfAdminQualityTablesNewController",
                ncyBreadcrumb: {
                    label: "{{'addQualityTable'|translate}}"
                }
            })
            .state("tfAdmin.qualityTables.edit", {
                url: "/:id",
                templateUrl: "/ng/view/yky/admin/quality-tables/edit.html",
                controller: "tfAdminQualityTablesEditController",
                ncyBreadcrumb: {
                    label: "{{'editQualityTable'|translate}}"
                }
            })


            .state("tfAdmin.models", {
                url: "models",
                templateUrl: "/ng/view/yky/admin/models/index.html",
                controller: "tfAdminModelsController",
                ncyBreadcrumb: {
                    label: "{{'models'|translate}}"
                }
            })
            .state("tfAdmin.models.new", {
                url: "/new",
                templateUrl: "/ng/view/yky/admin/models/new.html",
                controller: "tfAdminModelsNewController",
                ncyBreadcrumb: {
                    label: "{{'addModels'|translate}}"
                }
            })
            .state("tfAdmin.models.edit", {
                url: "/:id",
                templateUrl: "/ng/view/yky/admin/models/edit.html",
                controller: "tfAdminModelsEditController",
                ncyBreadcrumb: {
                    label: "{{'editModels'|translate}}"
                }
            })


            // logistics acceptence points
            .state("tfAdmin.logisticsAcceptencePoints", {
                url: "logistics-acceptence-points",
                templateUrl: "/ng/view/yky/admin/logistics-acceptence-points/index.html",
                controller: "tfAdminLogisticsAcceptencePointsController"
            })
            .state("tfAdmin.logisticsAcceptencePoints.new", {
                url: "/new",
                templateUrl: "/ng/view/yky/admin/logistics-acceptence-points/new.html",
                controller: "tfAdminLogisticsAcceptencePointsNewController"
            })
            .state("tfAdmin.logisticsAcceptencePoints.edit", {
                url: "/:id",
                templateUrl: "/ng/view/yky/admin/logistics-acceptence-points/edit.html",
                controller: "tfAdminLogisticsAcceptencePointsEditController"
            })


            // logistics loss types
            .state("tfAdmin.logisticsLossTypes", {
                url: "logistics-loss-types",
                templateUrl: "/ng/view/yky/admin/logistics-loss-types/index.html",
                controller: "tfAdminLogisticsLossTypesController",
                data: {
                    title: "logisticsLossTypes"
                }
            })
            .state("tfAdmin.logisticsLossTypes.new", {
                url: "/new",
                templateUrl: "/ng/view/yky/admin/logistics-loss-types/new.html",
                controller: "tfAdminLogisticsLossTypesNewController"
            })
            .state("tfAdmin.logisticsLossTypes.edit", {
                url: "/:id",
                templateUrl: "/ng/view/yky/admin/logistics-loss-types/edit.html",
                controller: "tfAdminLogisticsLossTypesEditController"
            })


            // quality lab tests
            .state("tfAdmin.qualityLabTests", {
                url: "logistics-loss-types",
                templateUrl: "/ng/view/yky/admin/quality-lab-tests/index.html",
                controller: "tfAdminQualityLabTestsController",
                data: {
                    title: "qualityLabTests"
                }
            })
            .state("tfAdmin.qualityLabTests.new", {
                url: "/new",
                templateUrl: "/ng/view/yky/admin/quality-lab-tests/new.html",
                controller: "tfAdminQualityLabTestsNewController",
                ncyBreadcrumb: {
                    label: "{{'addQualityLabTes'|translate}}"
                }
            })
            .state("tfAdmin.qualityLabTests.edit", {
                url: "/:id",
                templateUrl: "/ng/view/yky/admin/quality-lab-tests/edit.html",
                controller: "tfAdminQualityLabTestsEditController",
                ncyBreadcrumb: {
                    label: "{{'editQualityLabTest'|translate}}"
                }
            })


            // loss source
            .state("tfAdmin.lossSources", {
                url: "loss-sources",
                templateUrl: "/ng/view/yky/admin/loss-sources/index.html",
                controller: "tfAdminLossSourcesController",
                ncyBreadcrumb: {
                    label: "{{'lossSources'|translate}}"
                }
            })
            .state("tfAdmin.lossSources.new", {
                url: "/new",
                templateUrl: "/ng/view/yky/admin/loss-sources/new.html",
                controller: "tfAdminLossSourcesNewController",
                ncyBreadcrumb: {
                    label: "{{'addLossSource'|translate}}"
                }
            })
            .state("tfAdmin.lossSources.edit", {
                url: "/:id",
                templateUrl: "/ng/view/yky/admin/loss-sources/edit.html",
                controller: "tfAdminLossSourcesEditController",
                ncyBreadcrumb: {
                    label: "{{'editLossSource'|translate}}"
                }
            })


            .state("tfAdmin.durations", {
                url: "durations",
                templateUrl: "/ng/view/yky/admin/durations/index.html",
                controller: "tfAdminDurationsController",
                ncyBreadcrumb: {
                    label: "{{'durations'|translate}}"
                },
                data: {
                    roles: ['TFG_YKY_ADMIN', 'TFG_YKY_DURATION_DEF']
                }
            })
            .state("tfAdmin.durations.new", {
                url: "/new",
                templateUrl: "/ng/view/yky/admin/durations/new.html",
                controller: "tfAdminDurationsNewController",
                ncyBreadcrumb: {
                    label: "{{'addDuration'|translate}}"
                }
            })
            .state("tfAdmin.durations.edit", {
                url: "/:id",
                templateUrl: "/ng/view/yky/admin/durations/edit.html",
                controller: "tfAdminDurationsEditController",
                ncyBreadcrumb: {
                    label: "{{'editDuration'|translate}}"
                }
            })

            .state("tfAdmin.objectionTypes", {
                url: "objection-types",
                templateUrl: "/ng/view/yky/admin/objection-types/index.html",
                controller: "tfAdminObjectionTypesController",
                ncyBreadcrumb: {
                    label: "{{'objectionTypes'|translate}}"
                }
            })
            .state("tfAdmin.objectionTypes.new", {
                url: "/new",
                templateUrl: "/ng/view/yky/admin/objection-types/new.html",
                controller: "tfAdminObjectionTypesNewController",
                ncyBreadcrumb: {
                    label: "{{'addObjectionType'|translate}}"
                }
            })
            .state("tfAdmin.objectionTypes.edit", {
                url: "/:id",
                templateUrl: "/ng/view/yky/admin/objection-types/edit.html",
                controller: "tfAdminObjectionTypesEditController",
                ncyBreadcrumb: {
                    label: "{{'editObjectionType'|translate}}"
                }
            })

            .state("tfAdmin.mailLogs", {
                url: "/mail-logs",
                templateUrl: "/ng/view/yky/admin/mail-logs/index.html",
                controller: "tfAdminMailLogsController",
                ncyBreadcrumb: {
                    label: "{{'mailLogs'|translate}}"
                }
            })

            .state("tfAdmin.pressParameters", {
                url: "/press-params",
                templateUrl: "/ng/view/yky/admin/press/parameters.html",
                controller: "tfPressParametersController",
                ncyBreadcrumb: {
                    label: "{{'pressParameters'|translate}}"
                }
            })

        ;

    }]);
