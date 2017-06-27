(function() {
    'use strict';

    angular
        .module('rebuApp')
        .controller('UradMonitorDetailController', UradMonitorDetailController);

    UradMonitorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UradMonitor'];

    function UradMonitorDetailController($scope, $rootScope, $stateParams, previousState, entity, UradMonitor) {
        var vm = this;

        vm.uradMonitor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rebuApp:uradMonitorUpdate', function(event, result) {
            vm.uradMonitor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
