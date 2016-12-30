(function() {
    'use strict';

    angular
        .module('rebuApp')
        .controller('ResultDetailController', ResultDetailController);

    ResultDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Result'];

    function ResultDetailController($scope, $rootScope, $stateParams, previousState, entity, Result) {
        var vm = this;

        vm.result = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rebuApp:resultUpdate', function(event, result) {
            vm.result = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
