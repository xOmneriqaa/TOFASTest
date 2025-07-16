angular.module("tfDeveloperApp", ["tfDeveloperController", "tfDeveloperServices"])
    .config(["$stateProvider", function($stateProvider){

        $stateProvider
            .state("tfDeveloper", {
                url: "/tf-developer/",
                templateUrl: "/ng/view/tf-developer/index.html",
                data: {
                    isDeveloper: true
                }
            })
            .state("tfDeveloper.info", {
                url: "info",
                templateUrl: "/ng/view/tf-developer/info.html",
                controller: "tfDeveloperInfoController"
            })
            .state("tfDeveloper.entities", {
                url: "entities",
                templateUrl: "/ng/view/tf-developer/entities.html",
                controller: "tfDeveloperEntitiesController"
            })
            .state("tfDeveloper.controllers", {
                url: "controllers",
                templateUrl: "/ng/view/tf-developer/controllers.html",
                controller: "tfDeveloperControllersController"
            })
            .state("tfDeveloper.services", {
                url: "services",
                templateUrl: "/ng/view/tf-developer/services.html",
                controller: "tfDeveloperServicesController"
            })
            .state("tfDeveloper.repositories", {
                url: "repositories",
                templateUrl: "/ng/view/tf-developer/repositories.html",
                controller: "tfDeveloperRepositoriesController"
            })
            .state("tfDeveloper.dbLogs", {
                url: "db-logs",
                templateUrl: "/ng/view/tf-developer/db-logs.html",
                controller: "tfDeveloperDbLogsController"
            })
            .state("tfDeveloper.roles", {
                url: "roles",
                templateUrl: "/ng/view/tf-developer/roles/index.html",
                controller: "tfDeveloperRolesController"
            })
                .state("tfDeveloper.roles.new", {
                    url: "/new",
                    templateUrl: "/ng/view/tf-developer/roles/new.html",
                    controller: "tfDeveloperRolesNewController"
                })
                .state("tfDeveloper.roles.edit", {
                    url: "/:id",
                    templateUrl: "/ng/view/tf-developer/roles/edit.html",
                    controller: "tfDeveloperRolesEditController"
                })
            .state("tfDeveloper.auths", {
                url: "auths",
                templateUrl: "/ng/view/tf-developer/auths/index.html",
                controller: "tfDeveloperAuthsController"
            })
                .state("tfDeveloper.auths.new", {
                    url: "/new",
                    templateUrl: "/ng/view/tf-developer/auths/new.html",
                    controller: "tfDeveloperAuthsNewController"
                })
                .state("tfDeveloper.auths.edit", {
                    url: "/:id",
                    templateUrl: "/ng/view/tf-developer/auths/edit.html",
                    controller: "tfDeveloperAuthsEditController"
                })
            .state("tfDeveloper.params", {
                url: "params",
                templateUrl: "/ng/view/tf-developer/params/index.html",
                controller: "tfDeveloperParamsController"
            })
                .state("tfDeveloper.params.new", {
                    url: "/new",
                    templateUrl: "/ng/view/tf-developer/params/new.html",
                    controller: "tfDeveloperParamsNewController"
                })
                .state("tfDeveloper.params.edit", {
                    url: "/:id",
                    templateUrl: "/ng/view/tf-developer/params/edit.html",
                    controller: "tfDeveloperParamsEditController"
                })
            .state("tfDeveloper.console", {
                url: "console",
                templateUrl: "/ng/view/tf-developer/console.html",
                controller: "tfDeveloperConsoleController"
            });

    }]);