/**
 * Created by t40127 on 17.03.2017.
 */

var runFunc = function($rootScope, $http){
    $rootScope.$on('$stateChangeStart',  function (event, toState, toParams, fromState, fromParams) {
        $http.get("/loss/api/delete-cached-all").success(function(data){
            console.log("cached upload files are cleared...");
        });
    });
};
runFunc.$inject = ["$rootScope", "$http"];

angular.module("ykyLossCommonComponents", [])
    .run(runFunc);