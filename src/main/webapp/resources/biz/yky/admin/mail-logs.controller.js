/**
 * Created by t40127 on 17.03.2017.
 */

var tfAdminMailLogsController = function($scope, $http){
    $scope.gridOptions = {
        autoBind: false,
        dataSource: {
            type: "json",
            transport: {
                read: function(options) {
                    $http.post("/admin/api/mail-logs/list", $scope.searchParams).success(function(data){
                        options.success(data);
                    });
                }
            },
            pageSize: 10
        },
        columns: [
            {
                field: "subject",
                title: "{{'subject'| translate}}",
                filterable: true
            }, {
                field: "to",
                title: "{{'toAddress'| translate}}",
                filterable: true
            }, {
                field: "cc",
                title: "{{'ccAddress'| translate}}",
                filterable: true
            }, {
                field: "mailDate",
                title: "{{'mailDate'| translate}}",
                filterable: true
            }
        ]
    };

    $scope.searchParams = {};

    $scope.getMailLogs = function(){
        $scope.tfMailLogs.dataSource.read();
    };
};
tfAdminMailLogsController.$inject = ["$scope", "$http"];

angular.module("ykyAdminApp")

    .controller("tfAdminMailLogsController", tfAdminMailLogsController);
