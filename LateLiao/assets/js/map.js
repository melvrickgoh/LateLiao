var EXPORTED_SYMBOLS = ["Put all functions for export here"];
var map;
var marker; 
var loc_marker;

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
    
   	setMarker(lat, long);
    Android.pinLocation(lat, long);
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
	    
	    map.on('click', function(e) {
			
	    	if (marker != undefined) {
				map.removeLayer(marker);
			}
			
	        setMarker(e.latlng.lat, e.latlng.lng);
	        Android.pinLocation(e.latlng.lat, e.latlng.lng);
      
	    });
	} else {
		map.off('click');
	}
}

function setMarker(lat, long) {
	 var LeafIcon = L.Icon.extend({
        options: {
            iconSize:     [35, 35],
            iconAnchor:   [22, 40],
            popupAnchor:  [-3, -76]
        }
    });
    
    var greenIcon = new LeafIcon({iconUrl: '../images/icon.png'});    
    marker = L.marker([lat, long], {icon: greenIcon}).addTo(map);
}

function setCurrentLocation(current_Lat, current_long) {
	 var currentLocIcon = L.Icon.extend({
        options: {
            iconSize:     [35, 35],
            iconAnchor:   [22, 40],
            popupAnchor:  [-3, -76]
        }
    });
    
    var loc_Icon = new currentLocIcon({iconUrl: '../images/loc_icon.png'});    
    loc_marker = L.marker([current_Lat, current_long], {icon: loc_Icon}).addTo(map);
}