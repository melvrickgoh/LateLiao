var EXPORTED_SYMBOLS = ["Put all functions for export here"];
var map;
var marker; 

function initializeMap(lat, long, zoomarea){
	
	// add layers of map servers
	var ggl = new L.Google();
	var osm = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
		attribution: ''
	});

    map = L.map('map', {
	    center: new L.LatLng(lat, long),
	    zoom: zoomarea,
	    layers: [ggl, osm]
	});
    
    baseMaps = {
    	'Google':ggl,
    	'OSM':osm
    };
    
    mapLayers = {

    };
    
    map.addControl(new L.Control.Layers(baseMaps, mapLayers));
    
  	//On base layer change, switch searching service
	map.on('baselayerchange',function(e){
		setSearchFunction(e.name,map,searchEngine);
	});
    
    return map;
}

function setSearchFunction(serviceProvider,map,searchEngine){
	if (searchEngine!=null){
		searchEngine.removeFrom(map);
	}
	
	var providerChoice = null;
	switch(serviceProvider){
		case "Google":
			providerChoice = new L.GeoSearch.Provider.Google();
			break;
		case "esri":
			providerChoice = new L.GeoSearch.Provider.Esri();
			break;
		case "OSM":
			providerChoice = new L.GeoSearch.Provider.OpenStreetMap();
			break;
		case "bing":
			providerChoice = new L.GeoSearch.Provider.Bing();
			break;
		default:
			providerChoice = new L.GeoSearch.Provider.OpenStreetMap();
		break;
	}
	
	searchEngine = new L.Control.GeoSearch({
        provider: providerChoice,
        position:'topcenter',
        showMarker:false
    });
	
	searchEngine.addTo(map);
	
	return searchEngine;
}

function activatePinning(pinning) {

	if (pinning) {
	  var LeafIcon = L.Icon.extend({
	        options: {
	            iconSize:     [35, 35],
	            iconAnchor:   [22, 40],
	            popupAnchor:  [-3, -76]
	        }
	    });
	    
	    var greenIcon = new LeafIcon({iconUrl: '../images/icon.png'});
	    
	    map.on('click', function(e) {
			
	    	if (marker != undefined) {
				map.removeLayer(marker);
			}
	        marker = L.marker([e.latlng.lat, e.latlng.lng], {icon: greenIcon}).addTo(map);
	        
	        Android.pinLocation(e.latlng.lat, e.latlng.lng);
      
	    });
	} else {
		map.off('click');
	}
}