var tfAnnouncementsIndexController = function($scope, tfAnnouncementService){
    $scope.announcements = function(){
        return tfAnnouncementService.announcements;
    };

    $scope.getClassForAnnouncement = function(announcement) {
        var classVar = "list-group-item ";
        if(!announcement.read) {
            classVar += " list-group-item-info ";
        }

        return classVar;
    };

    $scope.forceToCheckAnnouncements = function(){
        tfAnnouncementService.forceRefresh();
    };
};
tfAnnouncementsIndexController.$inject = ["$scope", "tfAnnouncementService"];


var tfAnnouncementsViewController = function($scope, $stateParams, $state, tfAnnouncementService){
    $scope.selectedAnnouncement = tfAnnouncementService.getFromId($stateParams.id);

    tfAnnouncementService.markAsRead($scope.selectedAnnouncement);

    $scope.selectedAnnouncementHasAttachments = function(){
        return $scope.selectedAnnouncement.attachments.length > 0;
    };

    $scope.markAsUnread = function(announcement) {
        tfAnnouncementService.markAsUnread($scope.selectedAnnouncement);
        $state.go("tfAnnouncementList");
    };

};
tfAnnouncementsViewController.$inject = ["$scope", "$stateParams", "$state", "tfAnnouncementService"];

angular.module("tfAnnouncementsControllers", [])
    .controller("tfAnnouncementsIndexController", tfAnnouncementsIndexController)
    .controller("tfAnnouncementsViewController", tfAnnouncementsViewController);