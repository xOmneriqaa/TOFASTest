angular.module("tfAdminApp", ["tfAdminController", "tfAdminServices", "ykyAdminApp"])
    .config(["$stateProvider", function($stateProvider){

        $stateProvider
            .state("tfAdmin", {
                url: "/tf-admin/",
                templateUrl: "/ng/view/tf-admin/index.html",
                data: {
                    roles: ['TFG_YKY_ADMIN', 'TFG_YKY_DURATION_DEF', 'TFG_YKY_PRESS_USER']
                },
                ncyBreadcrumb: {
                    label: "{{'admin'|translate}}"
                }
            })
            .state("tfAdmin.appParams", {
                url: "app-params/",
                templateUrl: "/ng/view/tf-developer/params/index.html",
                controller: "tfAdminParamsController"
            })
                .state("tfAdmin.appParams.edit", {
                    url: ":id",
                    templateUrl: "/ng/view/tf-developer/params/edit.html",
                    controller: "tfAdminParamsEditController"
                })
            .state("tfAdmin.announcements", {
                url: "announcements/",
                templateUrl: "/ng/view/tf-admin/tf-announcements/index.html",
                controller: "tfAdminAnnouncementsController"
            })
                .state("tfAdmin.announcements.new", {
                    url: "new/",
                    templateUrl: "/ng/view/tf-admin/tf-announcements/new.html",
                    controller: "tfAdminAnnouncementsNewController"
                })
                .state("tfAdmin.announcements.view", {
                    url: "view/:id",
                    templateUrl: "/ng/view/tf-admin/tf-announcements/view.html",
                    controller: "tfAdminAnnouncementViewController"
                })
                .state("tfAdmin.announcements.edit", {
                    url: "edit/:id",
                    templateUrl: "/ng/view/tf-admin/tf-announcements/edit.html",
                    controller: "tfAdminAnnouncementsEditController"
                })

    }]);