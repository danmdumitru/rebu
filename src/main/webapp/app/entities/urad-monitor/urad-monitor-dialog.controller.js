(function() {
    'use strict';

    angular
        .module('rebuApp')
        .controller('UradMonitorDialogController', UradMonitorDialogController);

    UradMonitorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UradMonitor'];

    function UradMonitorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UradMonitor) {
        var vm = this;

        vm.uradMonitor = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.uradMonitor.id !== null) {
                UradMonitor.update(vm.uradMonitor, onSaveSuccess, onSaveError);
            } else {
                UradMonitor.save(vm.uradMonitor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rebuApp:uradMonitorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.time = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
