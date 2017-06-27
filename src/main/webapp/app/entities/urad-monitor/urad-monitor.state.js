(function() {
    'use strict';

    angular
        .module('rebuApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('urad-monitor', {
            parent: 'entity',
            url: '/urad-monitor',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rebuApp.uradMonitor.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/urad-monitor/urad-monitors.html',
                    controller: 'UradMonitorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uradMonitor');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('urad-monitor-detail', {
            parent: 'urad-monitor',
            url: '/urad-monitor/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rebuApp.uradMonitor.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/urad-monitor/urad-monitor-detail.html',
                    controller: 'UradMonitorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('uradMonitor');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UradMonitor', function($stateParams, UradMonitor) {
                    return UradMonitor.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'urad-monitor',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('urad-monitor-detail.edit', {
            parent: 'urad-monitor-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/urad-monitor/urad-monitor-dialog.html',
                    controller: 'UradMonitorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UradMonitor', function(UradMonitor) {
                            return UradMonitor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('urad-monitor.new', {
            parent: 'urad-monitor',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/urad-monitor/urad-monitor-dialog.html',
                    controller: 'UradMonitorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                unit_id: null,
                                latitude: null,
                                longitude: null,
                                altitude: null,
                                speed: null,
                                city: null,
                                country: null,
                                versionsw: null,
                                versionhw: null,
                                status: null,
                                mobile: null,
                                detector: null,
                                factor: null,
                                temperature: null,
                                cmp: null,
                                duty: null,
                                time: null,
                                voltage: null,
                                pressure: null,
                                humidity: null,
                                luminosity: null,
                                co2: null,
                                ch2o: null,
                                pm_25: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('urad-monitor', null, { reload: 'urad-monitor' });
                }, function() {
                    $state.go('urad-monitor');
                });
            }]
        })
        .state('urad-monitor.edit', {
            parent: 'urad-monitor',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/urad-monitor/urad-monitor-dialog.html',
                    controller: 'UradMonitorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UradMonitor', function(UradMonitor) {
                            return UradMonitor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('urad-monitor', null, { reload: 'urad-monitor' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('urad-monitor.delete', {
            parent: 'urad-monitor',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/urad-monitor/urad-monitor-delete-dialog.html',
                    controller: 'UradMonitorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UradMonitor', function(UradMonitor) {
                            return UradMonitor.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('urad-monitor', null, { reload: 'urad-monitor' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
