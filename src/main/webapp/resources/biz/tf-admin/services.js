var tfAdminAppParamFactory = function($resource, ADM_PRMS){
    return $resource((ADM_PRMS + "/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    });
};
tfAdminAppParamFactory.$inject = ["$resource", "ADM_PRMS"];


var tfAdminAnnouncementFactory = function($resource, ADM_ANS){
    return $resource((ADM_ANS + "/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    });
};
tfAdminAnnouncementFactory.$inject = ["$resource", "ADM_ANS"];




angular.module('tfAdminServices', ["ngResource", "tfConstants"])
    .factory("tfAdminAppParamFactory", tfAdminAppParamFactory)
    .factory("tfAdminAnnouncementFactory", tfAdminAnnouncementFactory);