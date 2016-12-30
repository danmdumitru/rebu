(function() {
    'use strict';

    angular
        .module('rebuApp')
        .controller('TrainingDeleteController',TrainingDeleteController);

    TrainingDeleteController.$inject = ['$uibModalInstance', 'entity', 'Training'];

    function TrainingDeleteController($uibModalInstance, entity, Training) {
        var vm = this;

        vm.training = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Training.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
