(function() {
    'use strict';
    angular
        .module('rebuApp')
        .factory('Exercise', Exercise);

    Exercise.$inject = ['$resource'];

    function Exercise ($resource) {
        var resourceUrl =  'api/exercises/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
