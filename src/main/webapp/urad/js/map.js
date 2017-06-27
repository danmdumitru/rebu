

/* init two source layers: one made of a features array and another one made of cluster. The latter uses the former as a source */
function initDevicesLayer() {
	var vectorSource = new ol.source.Vector({}); // empty vector

	jQuery.ajaxSetup({
		headers: {
			'Content-Type' : 'text/plain',
			'X-User-id':'',
			'X-User-hash':''
		}
	});
	jQuery.getJSON("http://data.uradmonitor.com/api/v1/devices", function(data) {
		for(var index in data) {
			if(!data.hasOwnProperty(index)) continue;
			// get all unit parameters	
			var device = data[index];
			// ignore devices without coordinates, these also include those in shipping
			if (device.latitude == null || device.longitude == null) continue;			

			// add to map	
			var feature = new ol.Feature({
				geometry: new ol.geom.Point(ol.proj.fromLonLat([parseFloat(device.longitude), parseFloat(device.latitude)])),
				id: device.id,
				timefirst: device.timefirst,
				timelast: device.timelast,
				timelocal: device.timelocal,
				latitude: device.latitude,
				longitude: device.longitude,
				altitude: device.altitude,
				city: device.city,
				country: device.country,
				versionhw: device.versionhw,
				versionsw: device.versionsw,
				status: device.status,
				mobile: device.mobile,
				avg_cpm: device.avg_cpm,
				detector: device.detector,
				factor: device.factor
			});

			vectorSource.addFeature(feature);
		}
	});
	return new ol.layer.Vector({
		name: 'units',
        source: vectorSource,
	  	style: function(feature, resolution) {
	  		if (feature.get("mobile"))
  				return [new ol.style.Style({image:  new ol.style.RegularShape({points: 3, radius: 6, fill: new ol.style.Fill({color: 'rgba(50,255,50,0.5)' }), stroke: new ol.style.Stroke({color: 'rgba(0,0,0,0.8)', width: 1}) }) })];
  			else 
  				return [new ol.style.Style({image:  new ol.style.RegularShape({points: 6, radius: 5, fill: new ol.style.Fill({color: 'rgba(255,255,50,0.5)' }), stroke: new ol.style.Stroke({color: 'rgba(0,0,0,0.8)', width: 1}) }) })];
			}
		});
}

/* open street map layer, will be interchanged with the Sat layer based on resolution treshold */
function initOSMLayer() {
	var resolution_threshold = 195000; // used by layerOSM and layerSat to interchange

	var layerOSM = new ol.layer.Tile({
		preload: 4,
		source: new ol.source.OSM(),
		maxResolution: resolution_threshold,
		name: 'OpenStreetMap'
	});
	return layerOSM;
}	 

/* open street map layer, will be interchanged with the Sat layer based on resolution treshold */
function initSATLayer() {
	var resolution_threshold = 195000; // used by layerOSM and layerSat to interchange

	var layerSat = new ol.layer.Tile({
		preload: 4,
		source: new ol.source.MapQuest({layer: 'sat'}),
		minResolution: resolution_threshold,
		name: "Sat"
	});
	return layerSat;
}


/* define the way the user can interact with the map, using mouse and keyboard */
function initInteractions() {
	var interactions = [        
	    // iterate only the interactions that we need        
	    new ol.interaction.DoubleClickZoom({                /* zoom on double click */
	        duration: 400
	    }),
	    new ol.interaction.DragPan({                        /* drag map */
	        kinetic: new ol.Kinetic(-0.01, 0.1, 200)
	    }),
	    new ol.interaction.DragZoom(),                     	/* shift-click to select zoom area */
	    new ol.interaction.MouseWheelZoom()
	   
	];
	return interactions;
}


/* targetName can be map-container */
function initMap(targetName, center) {
	var view = new ol.View({
		logo: false,
		center: center[0], 
		zoom: center[1],
		minZoom: 2,
		maxZoom: 15
		});
	

	console.log("creating map");
	// create map
	var map = new ol.Map({
		target: targetName,									// The DOM element that will contain the map
		renderer: 'canvas', 								// Force the renderer to be used
		maxResolution: 'auto',
		layers: [	initSATLayer(), 
					initOSMLayer(),
					initDevicesLayer()
				],
		loadTilesWhileAnimating: true,
		interactions: initInteractions(),
		fractionalZoom: true,
		view: view
	});
	return map;
}



