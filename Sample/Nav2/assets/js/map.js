var EXPORTED_SYMBOLS = ["Put all functions for export here"];
var map;

function initializeMap(lat, long, zoomarea){

	// API key which need payment
	// var cloudMap = 'cloudmade.com/7d3eadeb7fea46859d571d1c38e00456/997/256';
	
	// add layers of map servers
	var ggl = new L.Google();
	var yndx = new L.Yandex();
	var ytraffic = new L.Yandex("null", {traffic:false, opacity:0.8, overlay:true});
	var osm = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
		attribution: ''
	});

    map = L.map('map', {
	    center: new L.LatLng(lat, long),
	    zoom: zoomarea,
	    layers: [ggl, osm, ytraffic]
	});
    
    baseMaps = {
    	'Google':ggl,
    	'Yandex':yndx, 
    	'OSM':osm
    };
    
    mapLayers = {

    };
    
    map.addControl(new L.Control.Layers(baseMaps, mapLayers));
    
    var searchEngine = setSearchFunction("OSM",map,null);
    
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
        showMarker:true
    });
	
	searchEngine.addTo(map);
	
	return searchEngine;
}

function activatePinning(pinning) {
	
	if (pinning) {
	  var LeafIcon = L.Icon.extend({
	        options: {
	            shadowUrl: 'images/leaf-shadow.png',
	            iconSize:     [38, 95],
	            shadowSize:   [50, 64],
	            iconAnchor:   [22, 94],
	            shadowAnchor: [4, 62],
	            popupAnchor:  [-3, -76]
	        }
	    });
	    
	    var greenIcon = new LeafIcon({iconUrl: 'images/leaf-orange.png'});
	    
	    map.on('click', function(e) {
	        var marker = L.marker([e.latlng.lat, e.latlng.lng], {icon: greenIcon}).addTo(map)
	    	.bindPopup("Coordinate " + e.latlng.lat + ", " + e.latlng.lng + " has been pinned").openPopup();
	        
	        marker.dragging.enable();
	        
	        marker.on('dragend', function (e) {
	        	var coords = e.target.getLatLng();
	        	var lat = coords.lat;
	        	var lng = coords.lng;
	        	this.bindPopup("Coordinate " + lat + ", " + lng + " has been pinned").openPopup();
	        });
	    });
	} else {
		map.off('click');
	}
}