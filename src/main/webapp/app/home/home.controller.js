(function() {
    'use strict';

    angular
        .module('rebuApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', '$http', 'UradMonitor', 'UradMonitorForecast'];

    function HomeController ($scope, Principal, LoginService, $state, $http, UradMonitor, UradMonitorForecast) {
        var vm = this;
        vm.save = save;
        vm.forecast = forecast;
        vm.displaySave = 'Save';
        vm.forecastResult = null;
        var lengthRecords;
        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
        
        	var userid = "www"; // set a valid user id from the uRADMonitor Dashboard!
        	var userkey= "global";
        	var time = 24 * 3600; // last 24 hours of data

        	// HELPER FUNCTIONS FOR URADMONITOR API

        	function getUnit(sensor) {
        		switch (sensor) {
        			case "temperature": return "°C";
        			case "cpm": return "CPM";
        			case "voltage": return "Volts";
        			case "duty": return "‰";
        			case "pressure": return "Pa";
        			case "humidity": return "% RH";
        			case "gas1": return "ppm";
        			case "gas2": return "ppm";
        			case "gas3": return "ppm";
        			case "gas4": return "ppm";
        			case "dust": return "mg/m³";
        			case "co2" : return "ppm";
        			case "ch2o" : return "ppm";
        			case "pm25" : return "µg/m³";
        			case "pm10" : return "µg/m³";
        			case "noise" : return "dBA";
        			case "voc" : return "voc";
        		}
        	}
        	
        	function downloadUnits(u, k) {
        		$("#status").html('loading').css('color', 'magenta');
        		$.ajax({
        	    		type: 'GET',
            			url: "http://data.uradmonitor.com/api/v1/devices/" ,
        	    		dataType: 'json',
        	    		headers: { 'Content-Type' : 'text/plain', 'X-User-id': u, 'X-User-hash': k },
        			success: function(data) { 
        			    // status
        			    if (Object.keys(data)[0] == 'error') 
        			    	$("#status").html(data['error']).css('color', 'red');
        				else 
        					$("#status").html('Units ok').css('color', 'green');
        			    // populate select
        				$.each(data, function(key, value) {
        					$('#units').append($("<option />").val(value['id']).text(value['id']));
        				});
        				// first trigger
        				if (data.length > 0)
        					$('#units').trigger('change');
        				
        			},
        			async: true
        		});
        	}


        	// populate sensors
        	function downloadCapabilities(id, u, k) {
        		$("#status").html('loading').css('color', 'magenta');
        		$.ajax({
        	    		type: 'GET',
            			url: "http://data.uradmonitor.com/api/v1/devices/" + id ,
        	    		dataType: 'json',
        	    		headers: { 'Content-Type' : 'text/plain', 'X-User-id': u, 'X-User-hash': k },
        			success: function(data) { 
        				console.log(data);
        				vm.forecastResult = null;
        				vm.displaySave = 'Save';
        			    // status
        			    if (Object.keys(data)[0] == 'error') 
        			    	$("#status").html(data['error']).css('color', 'red');
        				else 
        					$("#status").html('Sensors ok').css('color', 'green');
        			    // populate select
        				$.each(data, function(key, value) {
        					$('#sensors').append($("<option />").val(key).text(value));
        					console.log(key + " " + value);
        				});
        				// first trigger
        				if (data.length > 0)
        					$('#sensors').trigger('change');
        			},
        			async: true
        		});
        	}

        	function downloadData(time, id, sensor, u, k) {
        		console.log("download :" +  "http://data.uradmonitor.com/api/v1/devices/" + id + "/" + sensor + "/" + time);
        		var urlUrad;
        		if(sensor === 'all'){
        			urlUrad = "http://data.uradmonitor.com/api/v1/devices/" + id + "/" + sensor;
        		}else{
        			urlUrad = "http://data.uradmonitor.com/api/v1/devices/" + id + "/" + sensor + "/" + time;
        		}
        		$("#status").html('loading').css('color', 'magenta');
        		$.ajax({
        		    	type: 'GET',
        		    	url: urlUrad,
        		    	dataType: 'json',
        			headers: { 'Content-Type' : 'text/plain', 'X-User-id': u, 'X-User-hash': k },
        		    	success: function(data, status) { 
        				if (status != 'success') {
        					$("#status").html('error').css('color', 'red');
        				} else {
        					if (Object.keys(data)[0] == 'error')
        						$("#status").html(data['error']).css('color', 'red');
        					else {
        						var stringified = JSON.stringify(data);
        						$("#status").html('ok ' + (data.length?(data.length+' row(s) '):' ') + (stringified.length/1000) + ' KB').css('color', 'green');
        						
        						drawChart(id, data, sensor);
        					}
        				}
        				vm.forecastResult = null;
        		    	},
        		    	async: true
        		});
        	}
        	
        	function drawChart(id, data, sensor) {
        		var plot = [];
        		for(var index in data) {
        			if(!data.hasOwnProperty(index)) continue;
        			var unit = data[index];
        			var value = parseFloat(unit[sensor]);
        			
        			plot[index] = [new Date(unit.time * 1000), value];
        		}
                if (sensor == "cpm") sensor = "dose";
        		chart.updateOptions({ 
        		    	colors: ['#0d0dff', '0dff0d'],
        	            labels: ['Time', sensor],
        		        'file': plot,
        		        ylabel: getUnit(sensor),
        		    }
        		);
        	}
        	
        	function save () {
        		var u = userid,
        			k = userkey,
        			id = $("#units").val();
        		$.ajax({
	    		    	type: 'GET',
	    		    	url:  "http://data.uradmonitor.com/api/v1/devices/" + id + "/all",
	    		    	dataType: 'json',
	    			headers: { 'Content-Type' : 'text/plain', 'X-User-id': u, 'X-User-hash': k },
	    		    	success: function(data, status) {
	    		    		if (data !== undefined  && data.success !== "[]") {
	    		    			for(var index in data) {
		    		        		var unit = data[index];
		    		        		unit.unitId = id;
		    	        		}	
	    		    			lengthRecords = data.length;
		    		    		UradMonitor.save(data, onSaveSuccess, onSaveError);
	    						
	    						function onSaveSuccess (result) {
	    				            $scope.$emit('rebuApp:uradMonitorUpdate', result);
	    				            vm.displaySave = 'Saved'
	    				        }
	    		
	    				        function onSaveError () {
	    				        }
							}
	    		    	},
	    		    	async: true
	    		});
            }
        	
        	function forecast () {
        		UradMonitorForecast.save({id: lengthRecords, unitId: $("#units").val(), detector: $("#sensors").val()}, onSaveSuccess, onSaveError);
				
				function onSaveSuccess (result) {
		            $scope.$emit('rebuApp:uradMonitorUpdate', result);
		            vm.forecastResult = result.forecast;
		        }

		        function onSaveError () {
		        }
            }
        	
        	// START EXECUTION
        	if (typeof userid == 'undefined' || typeof userkey == 'undefined')
        		$("#status").html('Configure variables userid and userkey in the code, using your credentials, as presented in the dashboard').css('color', 'red');
        	else {
        		downloadUnits(userid, userkey);
        	}

        	$('#units').on('change', function() {
        		$('#sensors').empty();
        		downloadCapabilities(this.value, userid, userkey );
        	});

        	$('#sensors').on('change', function() {
        		downloadData(time, $("#units").val(), $("#sensors").val(), userid, userkey );
        	});

        	var chart = new Dygraph(
        		'graphdiv', [], {
        			axisLabelColor: '#555',
        			legend: 'always',
                    labelsDivStyles: { 'textAlign': 'right' },
                    connectSeparatedPoints: true,
                    colors: [],
        	        labels: []
        		}
        	);
       
     
    }
})();
