angular.module("tfAnnouncementApp", ["tfAnnouncementsControllers"])
    .config(["$stateProvider", function($stateProvider){
        $stateProvider
            .state("tfAnnouncementList", {
                url: "/tf-announcements",
                templateUrl: "/ng/view/tf-announcements/index.html",
                controller: "tfAnnouncementsIndexController"
            })
            .state("tfAnnouncementList.view", {
                url: "/:id",
                templateUrl: "/ng/view/tf-announcements/view.html",
                controller: "tfAnnouncementsViewController"
            })
    }]);