(function() {
    'use strict';

    angular
        .module('rebuApp')
        .controller('UradMonitorDeleteController',UradMonitorDeleteController);

    UradMonitorDeleteController.$inject = ['$uibModalInstance', 'entity', 'UradMonitor'];

    function UradMonitorDeleteController($uibModalInstance, entity, UradMonitor) {
        var vm = this;

        vm.uradMonitor = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UradMonitor.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
