(function() {
    'use strict';
    angular
        .module('rebuApp')
        .factory('UradMonitor', UradMonitor);

    UradMonitor.$inject = ['$resource', 'DateUtils'];

    function UradMonitor ($resource, DateUtils) {
        var resourceUrl =  'api/urad-monitors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.time = DateUtils.convertDateTimeFromServer(data.time);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('rebuApp')
        .factory('UradMonitorForecast', UradMonitorForecast);

    UradMonitorForecast.$inject = ['$resource', 'DateUtils'];

    function UradMonitorForecast ($resource, DateUtils) {
        var resourceUrl =  'api/urad-monitors/forecast/:forecastUrad';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.time = DateUtils.convertDateTimeFromServer(data.time);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
