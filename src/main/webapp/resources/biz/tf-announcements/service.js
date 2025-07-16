var tfAnnouncementService = function($http, $timeout, ANN_LIST, ANN_FORCE_LIST, ANN_MARK_AS_UN_READ, ANN_MARK_AS_READ){
    var factory = {};
    factory.refreshInstance = undefined;

    factory.announcements = [];

    factory.clearAnnouncements = function(){
        factory.announcements = [];
    };

    factory.cancelRefresh = function(){
        if(factory.refreshInstance != undefined) {
            $timeout.cancel(factory.refreshInstance);
            factory.refreshInstance = undefined;
        }
    };

    factory.startRefresh = function(){
        factory.cancelRefresh();
        factory.refreshInstance = $timeout(factory.refresh, (60 * 1000));
    };

    factory.refresh = function(){
        $http.get(ANN_LIST).success(function(data){
            factory.announcements = data;

            factory.startRefresh();
        });
    };

    factory.forceRefresh = function(){
        factory.cancelRefresh();

        $http.get(ANN_FORCE_LIST).success(function(data){
            factory.announcements = data;

            factory.startRefresh();
        });
    };

    factory.hasAnnouncements = function(){
        return factory.announcements.length > 0;
    };

    factory.unreadCount = function(){
        var count = 0;
        angular.forEach(factory.announcements, function(value, key){
            if(!value.read) {
                count++;
            }
        });

        return count;
    };

    factory.markAsUnread = function(announcement){
        $http.post(ANN_MARK_AS_UN_READ, announcement.id).success(function(data){
            if(data) {
                factory.forceRefresh();
            }
        });
    };

    factory.markAsRead = function(announcement){
        $http.post(ANN_MARK_AS_READ, announcement.id).success(function(data){
            if(data) {
                factory.forceRefresh();
            }
        });
    };

    factory.getFromId = function(id) {
        var ann = null;
        angular.forEach(factory.announcements, function(value, key){
            if(value.id == id) {
                ann = value;
            }
        });

        return ann;
    };

    return factory;
};
tfAnnouncementService.$inject = ["$http", "$timeout", "ANN_LIST", "ANN_FORCE_LIST", "ANN_MARK_AS_UN_READ", "ANN_MARK_AS_READ"];


var tfAnnouncementHeaderController = function($scope, tfAnnouncementService) {
    $scope.unreadCount = function(){
        return tfAnnouncementService.unreadCount();
    };

    $scope.hasUnread = function(){
        return $scope.unreadCount() > 0;
    };

    $scope.getFirst5Announcements = function(){
        return tfAnnouncementService.announcements.slice(0,5);
    };
};
tfAnnouncementHeaderController.$inject = ["$scope", "tfAnnouncementService"];



var tfHasAnnouncementDirective = function(tfAnnouncementService){
    return {
        restrict: 'EA',
        scope: false,
        link: function (scope, element, attr) {
            scope.$watch(function(){
                return tfAnnouncementService.hasAnnouncements();
            }, function(newValue, oldValue){
                if(!newValue){
                    element.hide();
                } else {
                    element.show();
                }
            });
        }
    };
};
tfHasAnnouncementDirective.$inject = ["tfAnnouncementService"];




angular.module("tfAnnouncements", [])
    .factory("tfAnnouncementService", tfAnnouncementService)
    .directive("tfHasAnnouncement", tfHasAnnouncementDirective)

    .controller("tfAnnouncementHeaderController", tfAnnouncementHeaderController)

    .run(["tfAnnouncementService", "$rootScope", function(tfAnnouncementService, $rootScope){

        // refresh announcements on login event
        $rootScope.$on("tfLoginEvent", function(event, data){
            tfAnnouncementService.forceRefresh();
        });

        // cancel refresh of announcements on logout event
        $rootScope.$on("tfLogoutEvent", function(event, data){
            tfAnnouncementService.clearAnnouncements();
            tfAnnouncementService.cancelRefresh();
        });
    }]);