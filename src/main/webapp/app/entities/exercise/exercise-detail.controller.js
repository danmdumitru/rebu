(function() {
    'use strict';

    angular
        .module('rebuApp')
        .controller('ExerciseDetailController', ExerciseDetailController);

    ExerciseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Exercise', 'Training', 'Result'];

    function ExerciseDetailController($scope, $rootScope, $stateParams, previousState, entity, Exercise, Training, Result) {
        var vm = this;

        vm.exercise = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rebuApp:exerciseUpdate', function(event, result) {
            vm.exercise = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
