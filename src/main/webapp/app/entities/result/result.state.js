(function() {
    'use strict';

    angular
        .module('rebuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('result', {
            parent: 'entity',
            url: '/result',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rebuApp.result.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/result/results.html',
                    controller: 'ResultController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('result');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('result-detail', {
            parent: 'entity',
            url: '/result/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rebuApp.result.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/result/result-detail.html',
                    controller: 'ResultDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('result');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Result', function($stateParams, Result) {
                    return Result.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'result',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('result-detail.edit', {
            parent: 'result-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/result/result-dialog.html',
                    controller: 'ResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Result', function(Result) {
                            return Result.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('result.new', {
            parent: 'result',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/result/result-dialog.html',
                    controller: 'ResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                rep: null,
                                sets: null,
                                dueDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('result', null, { reload: 'result' });
                }, function() {
                    $state.go('result');
                });
            }]
        })
        .state('result.edit', {
            parent: 'result',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/result/result-dialog.html',
                    controller: 'ResultDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Result', function(Result) {
                            return Result.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('result', null, { reload: 'result' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('result.delete', {
            parent: 'result',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/result/result-delete-dialog.html',
                    controller: 'ResultDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Result', function(Result) {
                            return Result.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('result', null, { reload: 'result' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
