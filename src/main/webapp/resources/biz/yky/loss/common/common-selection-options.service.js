/**
 * Created by t40127 on 17.03.2017.
 */


var commonSelectionOptions = function(commonDataSourceService, $http){
    var factory = {};

    factory.partOptions = function(defaultOpts){
        var baseOptions = {
            dataTextField: "disegno",
            dataValueField: "disegno",
            optionLabel: "...",
            minLength: 3,
            filter: "contains",
            template: "#:data.disegno# - #:data.description#",
            dataSource: commonDataSourceService.partsDS(),
            virtual: {
                itemHeight: 26,
                valueMapper: function(options) {
                    if(options.value) {
                        $http.get("/common/api/get-part/" + options.value).success(function (data){
                            options.success(data);
                        });
                    } else {
                        options.success(-1);
                    }

                }
            }
        };

        if(defaultOpts) {
            angular.extend(baseOptions, defaultOpts);
        }

        return baseOptions;
    };

    factory.modelOptions = function(){
        return {
            dataTextField: "name",
            dataValueField: "code",
            template: "#:data.code# - #:data.name#",
            optionLabel: "...",
            dataSource: commonDataSourceService.modelsDS()
        };
    };

    factory.tutOptions = function(){
        return {
            dataTextField: "orgMmm",
            dataValueField: "orgMmm",
            filter: "contains",
            template: "#:data.orgMmm# - #:data.description#",
            optionLabel: "...",
            dataSource: commonDataSourceService.tutsDS()
        };
    };


    return factory;
};
commonSelectionOptions.$inject = ["commonDataSourceService", "$http"];

angular.module("ykyLossCommonComponents")
    .factory("commonSelectionOptions", commonSelectionOptions);