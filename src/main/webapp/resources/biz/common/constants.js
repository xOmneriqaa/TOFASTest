angular.module('tfConstants', [])
    .constant('LOGIN_GET_USER', '/login/api/get-user')
    .constant('LOGIN_USER_LOGIN', '/login/api/login')
    .constant('LOGIN_USER_LOGOUT', '/login/api/logout')
    .constant('LOGIN_LOGIN_TYPES', '/login/api/get-login-types')

    .constant('COMMON_I18N_URL', '/ng/translation')

    .constant('NOT_LIST', '/tf-notifications/list')
    .constant('NOT_CLEAR', '/tf-notifications/clear')

    .constant('ADM_PRMS', '/tf-admin/api/params')
    .constant('ADM_ANS', '/tf-admin/api/announcements')

    .constant('ANN_LIST', '/tf-announcements/api/list')
    .constant('ANN_FORCE_LIST', '/tf-announcements/api/force-list')
    .constant('ANN_MARK_AS_UN_READ', '/tf-announcements/api/mark-as-un-read')
    .constant('ANN_MARK_AS_READ', '/tf-announcements/api/mark-as-read')

    .constant('DEV_DB_LOGS', '/tf-developer/api/list-db-logs')
    .constant('DEV_API_ROLES', '/tf-developer/api/roles')
    .constant('DEV_API_AUTHS', '/tf-developer/api/auths')
    .constant('DEV_API_PARAMS', '/tf-developer/api/params');
