var tfAppRoleFactory = function($resource, DEV_API_ROLES){
    return $resource((DEV_API_ROLES + "/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    });
};
tfAppRoleFactory.$inject = ["$resource", "DEV_API_ROLES"];

var tfAppAuthFactory = function($resource, DEV_API_AUTHS){
    return $resource((DEV_API_AUTHS + "/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    });
};
tfAppAuthFactory.$inject = ["$resource", "DEV_API_AUTHS"];

var tfAppParamFactory = function($resource, DEV_API_PARAMS){
    return $resource((DEV_API_PARAMS + "/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    });
};
tfAppParamFactory.$inject = ["$resource", "DEV_API_PARAMS"];




angular.module('tfDeveloperServices', ["ngResource"])
    .factory("tfAppRoleFactory", tfAppRoleFactory)
    .factory("tfAppAuthFactory", tfAppAuthFactory)
    .factory("tfAppParamFactory", tfAppParamFactory);