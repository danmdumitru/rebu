(function() {
    'use strict';
    angular
        .module('rebuApp')
        .factory('Training', Training);

    Training.$inject = ['$resource'];

    function Training ($resource) {
        var resourceUrl =  'api/trainings/:id';

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
