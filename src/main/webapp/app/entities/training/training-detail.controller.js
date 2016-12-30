(function() {
    'use strict';

    angular
        .module('rebuApp')
        .controller('TrainingDetailController', TrainingDetailController);

    TrainingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Training', 'Exercise', 'User'];

    function TrainingDetailController($scope, $rootScope, $stateParams, previousState, entity, Training, Exercise, User) {
        var vm = this;

        vm.training = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rebuApp:trainingUpdate', function(event, result) {
            vm.training = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
