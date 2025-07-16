angular.module('tfAuth', ['tfConstants'])
    .factory('tfAuthService', ['$http', '$state', '$location', '$rootScope', '$cookies',
        'LOGIN_GET_USER', 'LOGIN_USER_LOGIN', 'LOGIN_USER_LOGOUT', "tfContextRootService", "$window",
        function ($http, $state, $location, $rootScope, $cookies,
                  LOGIN_GET_USER, LOGIN_USER_LOGIN, LOGIN_USER_LOGOUT, tfContextRootService, $window) {

        var factory = {};

        factory.isUserRequested = false;
        factory.username = null;
        factory.roles = [];
        factory.isAuthenticated = false;
        factory.loggedOut = false;

        factory.loginError = null;
        factory.requestedState = null;

        factory.isDeveloper = false;
        factory.isAdmin = false;

        factory.getUserName = function () {
            return factory.username;
        };

        factory.getRoles = function () {
            return factory.roles;
        };

        factory.getLoginError = function () {
            return factory.loginError;
        };

        factory.getAuthenticated = function () {
            return factory.isAuthenticated;
        };

        factory.getLoggedOut = function () {
            return factory.loggedOut;
        };

        factory.redirectPreviousState = function(){
            if(factory.requestedState) {
                var previouslyRequestedState = factory.requestedState;
                var pRequestedStateParams = factory.pRequestedStateParams;
                factory.requestedState = null;
                factory.pRequestedStateParams = null;
                $state.go(previouslyRequestedState, pRequestedStateParams);
            } else {
                if($state.current){
                    if($state.current.name == "login") {
                        $location.path("/");
                    }
                }
            }
        };

        factory.login = function (username, password, loginType) {

            $http.post(LOGIN_USER_LOGIN, {'username': username, 'password': password, 'loginType': loginType})
                .success(function (data) {
                    if (data.status) {
                        factory.setSuccessUserInfo(data);
                    } else {
                        factory.setFailUserInfo(data);
                    }
                });
        };

        factory.retrieveUserInfo = function () {
            $http.get(LOGIN_GET_USER)
                .success(function (data) {
                    factory.isUserRequested = true;
                    if (data.status) {
                        factory.setSuccessUserInfo(data);
                    } else if(factory.requestedState) {
                        factory.ssoLogin();
                    }
                });
        };

        factory.logout = function () {
            $http.post(LOGIN_USER_LOGOUT, {}).success(function (data) {
                factory.clearUserInfo();
                $state.go('home');
            });
        };

        factory.setSuccessUserInfo = function (serverResponse) {
            // broadcast a login event
            $rootScope.$broadcast("tfLoginEvent", {
                "username": serverResponse.username
            });

            factory.username = serverResponse.username;
            factory.loginError = null;
            factory.roles = serverResponse.roles;
            factory.isAuthenticated = true;
            factory.loggedOut = false;
            factory.isDeveloper = serverResponse.developer;
            factory.isAdmin = serverResponse.admin;

            factory.redirectPreviousState();
        };

        factory.setFailUserInfo = function (serverResponse) {
            factory.clearUserInfo();
            factory.loginError = serverResponse.message;
            factory.loggedOut = false;
        };

        factory.clearUserInfo = function () {
            // broadcast a login event
            if(factory.username != '') {
                $rootScope.$broadcast("tfLogoutEvent", {});
            }

            factory.username = '';
            factory.loginError = null;
            factory.roles = [];
            factory.isAuthenticated = false;
            factory.loggedOut = true;
            factory.isAdmin = false;
            factory.isDeveloper = false;
        };

        factory.containsRole = function(role){
            return factory.roles.indexOf(role) >= 0
        };

        factory.hasRole = function(role){
            return factory.containsRole(role) || factory.isAdmin || factory.isDeveloper;
        };

        factory.hasAnyRole = function(roles) {
            var hasRole = false;
            angular.forEach(roles, function(value){
                hasRole = hasRole || factory.hasRole(value);
            });
            return hasRole;
        }

        factory.handleUnAuthorizedAccess = function(pRequestedState, pRequestedStateParams){
            if(factory.getAuthenticated()) {
                $state.go('403');
            } else {
                factory.requestedState = pRequestedState;
                factory.pRequestedStateParams = pRequestedStateParams;
                if(factory.isUserRequested) {
                    factory.ssoLogin();
                }
            }
        };

        factory.ssoLogin = function(){
            $window.location.href = tfContextRootService.getUrl(("/login/?url=" + $location.url()));
        };

        factory.hasDeveloperRole = function(){
            return factory.isDeveloper;
        };

        factory.hasAdminRole = function(){
            return factory.isAdmin;
        };

        factory.getCsrfToken = function(){
            return $cookies["CSRF-TOKEN"];
        };

        factory.getCsrfTokenHeaderName = function(){
            return "X-CSRF-TOKEN";
        };

        factory.retrieveUserInfo();


        return factory;
    }])

    .directive("tfHasDeveloperRole", ["tfAuthService", function(tfAuthService){
        return {
            restrict: 'EA',
            scope: false,
            link: function (scope, element, attr) {
                scope.$watch(function(){
                    return tfAuthService.hasDeveloperRole();
                }, function(newValue, oldValue){
                    if(!newValue){
                        element.hide();
                    } else {
                        element.show();
                    }
                });
            }
        };
    }])

    .directive("tfNotHaveDeveloperRole", ["tfAuthService", function(tfAuthService){
        return {
            restrict: 'EA',
            scope: false,
            link: function (scope, element, attr) {
                scope.$watch(function(){
                    return tfAuthService.hasDeveloperRole();
                }, function(newValue, oldValue){
                    if(newValue){
                        element.hide();
                    } else {
                        element.show();
                    }
                });
            }
        };
    }])

    .directive("tfHasAdminRole", ["tfAuthService", function(tfAuthService){
        return {
            restrict: 'EA',
            scope: false,
            link: function (scope, element, attr) {
                scope.$watch(function(){
                    return tfAuthService.hasAdminRole();
                }, function(newValue, oldValue){
                    if(!newValue){
                        element.hide();
                    } else {
                        element.show();
                    }
                });
            }
        };
    }])

    .directive("tfNotHaveAdminRole", ["tfAuthService", function(tfAuthService){
        return {
            restrict: 'EA',
            scope: false,
            link: function (scope, element, attr) {
                scope.$watch(function(){
                    return tfAuthService.hasAdminRole();
                }, function(newValue, oldValue){
                    if(newValue){
                        element.hide();
                    } else {
                        element.show();
                    }
                });
            }
        };
    }])

    .directive('tfHasRole', ['tfAuthService', function (tfAuthService) {
        return {
            restrict: 'EA',
            link: function (scope, element, attr) {
                var role = attr.tfHasRole;
                scope.$watch(function(){
                    return tfAuthService.getRoles();
                }, function(newValue, oldValue){
                    if(tfAuthService.hasRole(role)){
                        element.show();
                    } else {
                        element.hide();
                    }
                });
            }
        };
    }])

    .directive('tfHasRoles', ['tfAuthService', function (tfAuthService) {
        return {
            restrict: 'EA',
            scope: {
                roles : '='
            },
            link: function (scope, element, attr) {
                scope.$watch(function(){
                    return tfAuthService.getRoles();
                }, function(newValue, oldValue){
                    if(tfAuthService.hasAnyRole(scope.roles)){
                        element.show();
                    } else {
                        element.hide();
                    }
                });
            }
        };
    }])



    .directive('tfIsAuthenticated', ['tfAuthService', function(tfAuthService){
        return {
            restrict: 'EA',
            scope: false,
            link: function (scope, element, attr) {
                scope.$watch(function(){
                    return tfAuthService.getAuthenticated();
                }, function(newValue, oldValue){
                    if(!newValue){
                        element.hide();
                    } else {
                        element.show();
                    }
                });
            }
        };
    }])

    .directive('tfNotAuthenticated', ['tfAuthService', function(tfAuthService){
        return {
            restrict: 'EA',
            link: function (scope, element, attr) {
                scope.$watch(function(){
                    return tfAuthService.getAuthenticated();
                }, function(newValue, oldValue){
                    if(newValue){
                        element.hide();
                    } else {
                        element.show();
                    }
                });
            }
        };
    }]);
