angular.module('tfCommonModule', ['ui.bootstrap', 'pascalprecht.translate', 'ngCookies', 'ngSanitize', 'ui.router',
    'angularFileUpload', 'angular-loading-bar', 'ngAnimate', 'blockUI', 'kendo.directives', 'tfConstants', 'tfAuth', 'tfCrud', 'tfAnnouncements'])

    .value('CONTEXT_ROOT', '')


    .config(['$httpProvider', 'blockUIConfig', '$translateProvider', '$injector', 'COMMON_I18N_URL',
        function ($httpProvider, blockUIConfig, $translateProvider, $injector, COMMON_I18N_URL) {

            // basic states
            var stateProvider = $injector.get('$stateProvider');
            stateProvider
                .state('403', {
                    url: '/403',
                    templateUrl: '/ng/view/common/403.html'
                })
                .state('404', {
                    url: '/404',
                    templateUrl: '/ng/view/common/404.html'
                })
                .state('500', {
                    url: '/500/:exceptionId',
                    templateUrl: '/ng/view/common/500.html',
                    controller: 'tf500Controller'
                });



            //i18n setup
            $translateProvider.useUrlLoader(COMMON_I18N_URL);
            $translateProvider.preferredLanguage('tr_TR');
            $translateProvider.useCookieStorage();

            //enable CSRF
            $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
            $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';


            // handle basic http actions
            $httpProvider.interceptors.push(['$q', '$injector', 'tfContextRootService', function ($q, $injector, tfContextRootService) {
                return {
                    request: function (config) {
                        config.url = tfContextRootService.getUrl(config.url);
                        return config;
                    },

                    // redirect to error page
                    responseError: function (config) {
                        var state = $injector.get('$state');
                        if (config.status == 500) {
                            var urlExtension = 'null';
                            if (config.data.length < 20) {
                                urlExtension = config.data;
                            }
                            state.go('500', {exceptionId: urlExtension});
                            return $q.reject(config);
                        } else if (config.status == 401) {
                            state.go('login');
                            return $q.reject(config);
                        } else if (config.status == 404) {
                            state.go('404');
                            return $q.reject(config);
                        } else if(config.status == 403) {
                            state.go('403');
                            return $q.reject(config);
                        }

                        return config;
                    }
                };
            }]);


            // Fix IE caching
            //initialize get if not there
            if (!$httpProvider.defaults.headers.get) {
                $httpProvider.defaults.headers.get = {};
            }

            // Answer edited to include suggestions from comments
            // because previous version of code introduced browser-related errors

            //disable IE ajax request caching
            $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
            // extra
            $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
            $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';




            // Change the default overlay message
            blockUIConfig.delay = 20;
            blockUIConfig.templateUrl = '/ng/view/common/wait.html';

            // kendo ui default
            kendo.culture("tr-TR");
            kendo.ui.DatePicker.fn.options.format = "dd.MM.yyyy";
            kendo.ui.DateTimePicker.fn.options.format = "dd.MM.yyyy HH:mm:ss";
            kendo.ui.DateTimePicker.fn.options.timeFormat = "HH:mm";

            kendo.ui.Grid.fn.options.sortable = true;
            kendo.ui.Grid.fn.options.pageable = true;
            kendo.ui.Grid.fn.options.filterable = true;
        }])


    .controller('tfNavbarController', ['$scope', '$translate', 'tfAuthService', 'tfContextRootService', function ($scope, $translate, tfAuthService, tfContextRootService) {
        $scope.changeLanguage = function (langKey) {
            $translate.use(langKey);

            // kendo i18n
            if(langKey.indexOf("tr") >= 0) {
                kendo.culture("tr-TR");
            } else {
                kendo.culture("en-US");
            }
        };

        $scope.isLanguage = function (langKey) {
            return langKey == $translate.use();
        };

        $scope.getUserName = function () {
            return tfAuthService.getUserName();
        };

        $scope.logout = function () {
            tfAuthService.logout();
        };

    }])

    .controller("tfSsoLoginController", ["$scope", "$window", "$location", "tfContextRootService", function($scope, $window, $location, tfContextRootService){
        $scope.login = function(){
            $window.location.href = tfContextRootService.getUrl(("/login/?url=" + $location.url()));
        };
    }])

    .controller('tf500Controller', ['$scope', '$stateParams', function ($scope, $stateParams) {
        $scope.getExceptionId = function () {
            return $stateParams.exceptionId;
        };
    }])

    .filter('htmlToPlaintext', function() {
        return function(text) {
            return String(text).replace(/<[^>]+>/gm, '');
        };
    })

    .factory('tfContextRootService', ['CONTEXT_ROOT', function (CONTEXT_ROOT) {
        var factory = {};

        factory.contextRoot = '';

        factory.setContextRoot = function (contextRoot) {
            factory.contextRoot = contextRoot;
        };

        factory.getUrl = function (url) {
            if (url) {
                if (url.indexOf('/') == 0) {
                    if (factory.contextRoot.lastIndexOf('/') == (factory.contextRoot.length - 1)) {
                        return factory.contextRoot + url.substring(1);
                    } else {
                        return factory.contextRoot + url;
                    }
                }
            }

            return url;
        }

        return factory;
    }])

    // creates a fake form in order to download a form request (as excel or pfd stc.) from server
    .factory('tfDownloadService', ['$cookies', 'tfContextRootService', function ($cookies, tfContextRootService) {
        var factory = {};

        factory.download = function (postUrl, pFormParams) {
            var fakeForm = angular.element("#tfDownloadForm")[0];

            var csrfParam = angular.element("#tfCsrfParam")[0];
            csrfParam.value = $cookies['CSRF-TOKEN'];

            var formParams = angular.element("#tfFormParam")[0];
            formParams.value = JSON.stringify(pFormParams);

            fakeForm.onsubmit = null;
            fakeForm.action = tfContextRootService.getUrl(postUrl);
            fakeForm.submit();
        };

        return factory;
    }])

    .directive('tfHref', ['tfContextRootService', function (tfContextRootService) {
        return {
            restrict: 'EA',
            link: function (scope, element, attr) {
                scope.$watch(function () {
                    return attr.tfHref;
                }, function (newValue, oldValue) {
                    element[0].setAttribute('href', tfContextRootService.getUrl(attr.tfHref));
                });
            }
        };
    }])


    .run(['$rootScope', 'tfAuthService', 'tfContextRootService', '$cookies', function ($rootScope, tfAuthService, tfContextRootService, $cookies) {

        // set root context
        tfContextRootService.setContextRoot(angular.element(document.querySelector('#tf-base-pointer'))[0].href);

        // listen route changes
        $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
            var toData = toState.data;
            if (toData) {
                var canAccess = true;

                // check role access
                var accessibleRoles = toData.roles;
                if (accessibleRoles != null) {
                    canAccess &= tfAuthService.hasAnyRole(accessibleRoles);
                }

                // check developer can access
                var isDeveloperDefined = toData.isDeveloper;
                if(isDeveloperDefined) {
                    canAccess &= tfAuthService.hasDeveloperRole();
                }

                // check admin can access
                var isAdminDefined = toData.isAdmin;
                if(isAdminDefined) {
                    canAccess &= tfAuthService.hasAdminRole();
                }

                // check if it is authenticated
                var isAuthenticatedDefined = toData.isAuthenticated;
                if(isAuthenticatedDefined) {
                    canAccess &= tfAuthService.getAuthenticated();
                }

                // if does not match any rule
                if (!canAccess) {
                    event.preventDefault();
                    tfAuthService.handleUnAuthorizedAccess(toState.name, toParams);
                }
            }
        });

        // set kendo ui csrf
        $.ajaxSetup({
            headers: {
                    "X-CSRF-TOKEN": $cookies["CSRF-TOKEN"]
            }
        });
    }]);