(function() {
    'use strict';

    angular
        .module('rebuApp')
        .controller('TrainingDialogController', TrainingDialogController);

    TrainingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Training', 'Exercise', 'User'];

    function TrainingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Training, Exercise, User) {
        var vm = this;

        vm.training = entity;
        vm.clear = clear;
        vm.save = save;
        vm.exercises = Exercise.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.training.id !== null) {
                Training.update(vm.training, onSaveSuccess, onSaveError);
            } else {
                Training.save(vm.training, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rebuApp:trainingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
