var ykyStandartStepsFactory = function($resource){
    return $resource(("/admin/api/standart-steps/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    });
};
ykyStandartStepsFactory.$inject = ["$resource"];

var ykyModelsFactory = function($resource) {
    return $resource(("/admin/api/models/:id"), {
        id: "@code"
    }, {
        update: {
            method: "PUT"
        }
    });
};
ykyModelsFactory.$inject = ["$resource"];

var ykyStepsFactory = function($resource) {
    return $resource(("/admin/api/steps/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    });
};
ykyStepsFactory.$inject = ["$resource"];

var ykyWorkTypeFactory = function($resource) {
    return $resource(("/admin/api/work-types/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    });
};
ykyWorkTypeFactory.$inject = ["$resource"];

var ykyQualityTableFactory = function($resource) {
    return $resource(("/admin/api/quality-loss-tables/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    });
};
ykyQualityTableFactory.$inject = ["$resource"];


var ykyLogisticsLossTypeFactory = function($resource){
    return $resource(("/admin/api/logistics-loss-types/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    });
};
ykyLogisticsLossTypeFactory.$inject = ["$resource"];

var ykyLogisticsAcceptencePointFactory = function($resource) {
    return $resource(("/admin/api/logistics-acceptence-points/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    });
};
ykyLogisticsAcceptencePointFactory.$inject = ["$resource"];

ykyQualityLabTestFactory = function($resource){
    return $resource(("/admin/api/quality-lab-tests/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    });
};
ykyQualityLabTestFactory.$inject = ["$resource"];

var ykyLossSourceFactory = function($resource){
    return $resource(("/admin/api/loss-source/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    });
};
ykyLossSourceFactory.$inject = ["$resource"];

var ykyObjectionTypeFactory = function($resource){
    return $resource(("/admin/api/objection-types/:id"), {
        id: "@id"
    }, {
        update: {
            method: "PUT"
        }
    })
};
ykyObjectionTypeFactory.$inject = ["$resource"];


angular.module("ykyAdminService", [])
    .factory("ykyStandartStepsFactory", ykyStandartStepsFactory)
    .factory("ykyModelsFactory", ykyModelsFactory)
    .factory("ykyStepsFactory", ykyStepsFactory)
    .factory("ykyWorkTypeFactory", ykyWorkTypeFactory)
    .factory("ykyQualityTableFactory", ykyQualityTableFactory)
    .factory("ykyLogisticsLossTypeFactory", ykyLogisticsLossTypeFactory)
    .factory("ykyLogisticsAcceptencePointFactory", ykyLogisticsAcceptencePointFactory)
    .factory("ykyQualityLabTestFactory", ykyQualityLabTestFactory)
    .factory("ykyLossSourceFactory", ykyLossSourceFactory)
    .factory("ykyObjectionTypeFactory", ykyObjectionTypeFactory);