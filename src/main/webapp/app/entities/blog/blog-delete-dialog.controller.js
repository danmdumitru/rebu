(function() {
    'use strict';

    angular
        .module('rebuApp')
        .controller('BlogDeleteController',BlogDeleteController);

    BlogDeleteController.$inject = ['$uibModalInstance', 'entity', 'Blog'];

    function BlogDeleteController($uibModalInstance, entity, Blog) {
        var vm = this;

        vm.blog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Blog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
