{
    var tfDeveloperInfoController = function($scope, $http) {
        $http.get("/tf-developer/api/list-all-parameters").success(function(data){
            $scope.allParams = data;
        });
    };
    tfDeveloperInfoController.$inject = ["$scope", "$http"];

    var tfDeveloperEntitiesController = function($scope, $http) {
        $http.get("/tf-developer/api/list-entities").success(function(data){
            $scope.entities = data;
        });
    };
    tfDeveloperEntitiesController.$inject = ["$scope", "$http"];

    var tfDeveloperControllersController = function($scope, $http) {
        $http.get("/tf-developer/api/list-controllers").success(function(data){
            $scope.controllers = [];
            for(var controllerIndex in data) {
                var controller = data[controllerIndex];
                var controllerMapCount = -1;
                for(var controllerMapIndex in controller.urlMappings) {
                    var controllerMap = controller.urlMappings[controllerMapIndex];
                    controllerMapCount++;
                    var methodCount = -1;
                    for(var methodIndex in controller.methodInfos) {
                        var method = controller.methodInfos[methodIndex];
                        methodCount++;
                        var methodMapCount = -1;
                        for(var methodMapIndex in method.urlMappings) {
                            var methodMap = method.urlMappings[methodMapIndex];
                            methodMapCount++;
                            $scope.controllers.push({
                                "className": controller.className,
                                "controllerMap": controllerMap,
                                "methodName": method.methodName,
                                "methodMap": methodMap,
                                "fullMap": (controllerMap + methodMap),
                                "isFirstControllerMap": (controllerMapCount == 0 && methodCount == 0 && methodMapCount == 0),
                                "isFirstMethodMap": (methodCount == 0 && methodMapCount == 0),
                                "isFirstMethod": (methodMapCount == 0)
                            });
                        }
                    }
                }
            }
        });
    };
    tfDeveloperControllersController.$inject = ["$scope", "$http"];

    var tfDeveloperServicesController = function($scope, $http) {
        $http.get("/tf-developer/api/list-services").success(function(data){
            $scope.services = data;
        });
    };
    tfDeveloperServicesController.$inject = ["$scope", "$http"];

    var tfDeveloperRepositoriesController = function($scope, $http) {
        $http.get("/tf-developer/api/list-repositories").success(function(data){
            $scope.repositories = data;
        });
    };
    tfDeveloperRepositoriesController.$inject = ["$scope", "$http"];

    var tfDeveloperDbLogsController = function($scope, $http, $modal, DEV_DB_LOGS){

        $scope.gridOptions = {
            autoBind: false,
            dataSource: {
                type: "json",
                transport: {
                    read: function(options) {
                        $http.post(DEV_DB_LOGS, $scope.searchParams).success(function(data){
                            options.success(data);
                        });
                    }
                },
                pageSize: 10
            },
            columns: [
                {
                    field: "exceptionId",
                    title: "{{'exceptionId'| translate}}",
                    filterable: { multi: true }
                }, {
                    field: "exceptionLevel",
                    title: "{{'exceptionLevel'| translate}}",
                    filterable: { multi: true }
                }, {
                    field: "exceptionName",
                    title: "{{'exceptionName'| translate}}",
                    filterable: { multi: true }
                }, {
                    field: "exceptionUrl",
                    title: "{{'exceptionUrl'| translate}}",
                    filterable: { multi: true }
                }, {
                    field: "insertedBy",
                    title: "{{'insertedBy'| translate}}",
                    filterable: { multi: true }
                }, {
                    field: "insertedDate",
                    title: "{{'insertedDate'| translate}}",
                    filterable: { multi: true }
                }
            ]
        };

        $scope.searchParams = {};
        $scope.getDbLogs = function(){
            $scope.tfDbLogs.dataSource.read();
        };

    };
    tfDeveloperDbLogsController.$inject = ["$scope", "$http", "$modal", "DEV_DB_LOGS"];
}



// roles
{
    var tfDeveloperRolesController = function($scope, $state, $modal, tfAppRoleFactory, tfBaseCrudListController){
        return tfBaseCrudListController($scope, $state, tfAppRoleFactory, $modal);
    };
    tfDeveloperRolesController.$inject = ["$scope", "$state", "$modal", "tfAppRoleFactory", "tfBaseCrudListController"];

    var tfDeveloperRolesNewController = function($scope, $state, tfAppRoleFactory, tfBaseCrudNewController){
        return tfBaseCrudNewController($scope, $state, tfAppRoleFactory, {}, "tfDeveloper.roles");
    };
    tfDeveloperRolesNewController.$inject = ["$scope", "$state", "tfAppRoleFactory", "tfBaseCrudNewController"];

    var tfDeveloperRolesEditController = function($scope, $state, tfAppRoleFactory, tfBaseCrudEditController, $stateParams){
        return tfBaseCrudEditController($scope, $state, tfAppRoleFactory, {id: $stateParams.id}, "tfDeveloper.roles");
    };
    tfDeveloperRolesEditController.$inject = ["$scope", "$state", "tfAppRoleFactory", "tfBaseCrudEditController", "$stateParams"];
}



// auths
{
    var tfDeveloperAuthsController = function($scope, $state, $modal, tfAppAuthFactory, tfBaseCrudListController){
        return tfBaseCrudListController($scope, $state, tfAppAuthFactory, $modal);
    };
    tfDeveloperAuthsController.$inject = ["$scope", "$state", "$modal", "tfAppAuthFactory", "tfBaseCrudListController"];

    var tfDeveloperAuthsNewController = function($scope, $state, tfAppAuthFactory, tfBaseCrudNewController, tfAppRoleFactory){
        angular.extend($scope, {
            roles: [],
            getRoles : function(){
                $scope.roles = tfAppRoleFactory.query();
            }
        });
        $scope.getRoles();
        return tfBaseCrudNewController($scope, $state, tfAppAuthFactory, {}, "tfDeveloper.auths");
    };
    tfDeveloperAuthsNewController.$inject = ["$scope", "$state", "tfAppAuthFactory", "tfBaseCrudNewController", "tfAppRoleFactory"];

    var tfDeveloperAuthsEditController = function($scope, $state, tfAppAuthFactory, tfBaseCrudEditController, $stateParams, tfAppRoleFactory){
        angular.extend($scope, {
            roles: [],
            getRoles : function(){
                $scope.roles = tfAppRoleFactory.query();
            }
        });
        $scope.getRoles();
        return tfBaseCrudEditController($scope, $state, tfAppAuthFactory, {id: $stateParams.id}, "tfDeveloper.auths");
    };
    tfDeveloperAuthsEditController.$inject = ["$scope", "$state", "tfAppAuthFactory", "tfBaseCrudEditController", "$stateParams", "tfAppRoleFactory"];
}




// params
{
    var tfDeveloperParamsController = function($scope, $state, $modal, tfAppParamFactory, tfBaseCrudListController){
        return tfBaseCrudListController($scope, $state, tfAppParamFactory, $modal);
    };
    tfDeveloperParamsController.$inject = ["$scope", "$state", "$modal", "tfAppParamFactory", "tfBaseCrudListController"];

    var tfDeveloperParamsNewController = function($scope, $state, tfAppParamFactory, tfBaseCrudNewController){
        return tfBaseCrudNewController($scope, $state, tfAppParamFactory, {}, "tfDeveloper.params");
    };
    tfDeveloperParamsNewController.$inject = ["$scope", "$state", "tfAppParamFactory", "tfBaseCrudNewController"];

    var tfDeveloperParamsEditController = function($scope, $state, tfAppParamFactory, tfBaseCrudEditController, $stateParams){
        return tfBaseCrudEditController($scope, $state, tfAppParamFactory, {id: $stateParams.id}, "tfDeveloper.params");
    };
    tfDeveloperParamsEditController.$inject = ["$scope", "$state", "tfAppParamFactory", "tfBaseCrudEditController", "$stateParams"];
}



// console controller
{
    var tfDeveloperConsoleController = function($scope, $http, $interval, $state){
        $scope.logs = [];

        $scope.toggleCapture = function(){
            $http.get("/tf-developer/api/console/toggle-capture").success(function(data){
                if(data) {
                    $scope.refreshStatus();
                }
            });
        };

        $scope.refreshStatus = function(){
            $http.get("/tf-developer/api/console/status").success(function(data){
                $scope.status = data;

                if($scope.status.capturing) {
                    $scope.refreshLogs();
                }
            });
        };

        $scope.refreshLogs = function(){
            $http.get("/tf-developer/api/console/logs").success(function(data){
                if(data) {
                    if(!(data.length == 1 && +data[0] == -1)) {
                        angular.forEach(data, function(value, key){
                            $scope.logs.unshift({"value": value});
                        });
                    }

                    if($state.$current.name == 'tfDeveloper.console' && $scope.status.capturing) {
                        $interval($scope.refreshLogs, 5000, 1);
                    }
                }
            });
        };

        $scope.changeLevel = function(level) {
            $http.post("/tf-developer/api/console/change-level", level).success(function(data){
                if(data) {
                    $scope.refreshStatus();
                }
            });
        };

        $scope.getClassFromLogLevel = function(){
            if($scope.status) {
                if($scope.status.error) {
                    return "label label-danger";
                } else if($scope.status.warning) {
                    return "label label-warning";
                } else if($scope.status.info) {
                    return "label label-info";
                } else if($scope.status.debug) {
                    return "label label-primary";
                } else {
                    return "label label-default"
                }
            }
        };

        $scope.clearConsole = function(){
            $scope.logs = [];
        };

        $scope.strFilter = "";
        $scope.refreshStatus();

    };
    tfDeveloperConsoleController.$inject = ["$scope", "$http", "$interval", "$state"];
}









angular.module("tfDeveloperController", [])
    .controller("tfDeveloperInfoController", tfDeveloperInfoController)
    .controller("tfDeveloperEntitiesController", tfDeveloperEntitiesController)
    .controller("tfDeveloperControllersController", tfDeveloperControllersController)
    .controller("tfDeveloperServicesController", tfDeveloperServicesController)
    .controller("tfDeveloperRepositoriesController", tfDeveloperRepositoriesController)
    .controller("tfDeveloperDbLogsController", tfDeveloperDbLogsController)
    .controller("tfDeveloperRolesController", tfDeveloperRolesController)
        .controller("tfDeveloperRolesNewController", tfDeveloperRolesNewController)
        .controller("tfDeveloperRolesEditController", tfDeveloperRolesEditController)
    .controller("tfDeveloperAuthsController", tfDeveloperAuthsController)
        .controller("tfDeveloperAuthsNewController", tfDeveloperAuthsNewController)
        .controller("tfDeveloperAuthsEditController", tfDeveloperAuthsEditController)
    .controller("tfDeveloperParamsController", tfDeveloperParamsController)
        .controller("tfDeveloperParamsNewController", tfDeveloperParamsNewController)
        .controller("tfDeveloperParamsEditController", tfDeveloperParamsEditController)
    .controller("tfDeveloperConsoleController", tfDeveloperConsoleController);