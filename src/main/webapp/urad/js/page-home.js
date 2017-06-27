'use strict';

jQuery(function() {
    $ = jQuery;
	
	<!-- hotspots organized as (lon,lat) coordinates and default zoom level -->
	var world = [ol.proj.fromLonLat([-26.368186, 34.741361]), 2];

	var map = initMap('map-container', world);

});

	
	

