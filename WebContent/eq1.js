/**
 * Google Map to Display the Earthquake Data
 */

var map, latlngbounds;
var currentLat,  currentLong;
var currentLocation;
var mapOptions;
var result;
var searchLocLat;
var searchLocLong;
var url;
var xmlHttpYear;
var eq;
var markers = [];
var animation;
var outputformat;

function getYearEQData() {
	
	var startYear = document.getElementById("startYear").value;
	var startMonth = document.getElementById("startMonth").value;
	var startDate = document.getElementById("startDate").value;
	var endYear = document.getElementById("endYear").value;
	var endMonth = document.getElementById("endMonth").value;
	var endDate = document.getElementById("endDate").value;
//	var latitude = document.getElementById("latitude").value;
//	var longitude = document.getElementById("longitude").value;
	var minMagnitude = document.getElementById("minMagnitude").value;
	var maxMagnitude = document.getElementById("maxMagnitude").value;
	var searchLocation = document.getElementById("searchlocation").value;
	var radius = document.getElementById("radius").value;
	
	if (document.getElementById('table').checked) {
		outputformat = document.getElementById('table').value;
		}
	if (document.getElementById('googlemap').checked) {
		outputformat = document.getElementById('googlemap').value;
		
		}
	if (document.getElementById('animation').checked === true) 
		animation = true;
	else
		animation = false;
	
	// Prepare for HTTP connection
	xmlHttpYear = new XMLHttpRequest();
	
	// get the coordinates of the searchLocation
	//https://maps.googleapis.com/maps/api/geocode/json?address=Taipei&key=YOUR_API_KEY
	if (searchLocation == "" || searchLocation == null) {
		// The user wants to search all and not limited by location
		result = "";
		searchLocLat = "";
		searchLocLong= "";
	} else {
	   url = "https://maps.googleapis.com/maps/api/geocode/json?address=" 
			+searchLocation;
	
		//alert("Req Lat/long: " + url);
		
		xmlHttpYear.open("GET", url, false);
		
		xmlHttpYear.send();

		if (xmlHttpYear.readyState == 4 && xmlHttpYear.status == 200)
		{
			result = xmlHttpYear.responseText;
			var obj = JSON.parse(result);
			searchLocLat = obj.results[0].geometry.location.lat;
			//alert ("get Loc Lat: " + searchLocLat);

			searchLocLong= obj.results[0].geometry.location.lng;
			//alert ("get Loc Long: " + searchLocLong);
			
		}
		
	}
	
		// var url = "http://140.137.218.53:8080/EarthquakeServer/GetFromDB?year=" +
		// year;
		 url = "http://192.168.1.109:8080/EarthquakeServer2/GetFromForm3?";
		
		url += "startYear=" + startYear;
		url += "&startMonth=" + startMonth;
		url += "&startDate=" + startDate;
		url += "&endYear=" + endYear;
		url += "&endMonth=" + endMonth;
		url += "&endDate=" + endDate;
		url += "&minMagnitude=" + minMagnitude;
		url += "&maxMagnitude=" + maxMagnitude;
		url += "&outputformat=" + outputformat;
		url += "&searchLocLat=" + searchLocLat;
		url += "&searchLocLong=" + searchLocLong;
		url += "&radius=" + radius;
		url += "&outputformat=" + outputformat;
		
		//alert("url=" + url);

		xmlHttpYear.open("GET", url, false);

		xmlHttpYear.send();

		if (xmlHttpYear.readyState == 4 && xmlHttpYear.status == 200)

		{
			//alert("Get response from Tomcat server");
			result = xmlHttpYear.responseText;
			//alert(result);
			
			if (outputformat === "Table") {
				//alert(outputformat);
				document.getElementById("mapArea").style.display = 'none';
				document.getElementById("tableArea").style.display = 'block';
			}
			else {
				document.getElementById("tableArea").style.display = 'none';
				document.getElementById("mapArea").style.display = "block";
			}
			
			drawMap(result);
		}
		else

		{

			alert("Could not get the required information");

		}

}
function processHeader (result) {
	
	var header = result.split(",");
	//alert ("header[0]=" + header[0]);
	document.getElementById("searchResult").value = header[0];
	document.getElementById("startYear").value = header[1];
	document.getElementById("startMonth").value = header[2];
	document.getElementById("startDate").value = header[3];
	document.getElementById("endYear").value = header[4];
	document.getElementById("endMonth").value = header[5];
	document.getElementById("endDate").value = header[6];
	document.getElementById("minMagnitude").value = header[7];
	document.getElementById("maxMagnitude").value = header[8];
	// header[9] and header[10] are the place latitude and longitude
	// update the radius only when user has specified the place
	if (header[9] != "")
		document.getElementById("radius").value = header[11];
	else
		document.getElementById("radius").value = "";
}

function clearMarkers() {
	  for (var i = 0; i < markers.length; i++) {
	    markers[i].setMap(null);
	  }
	  markers = [];
}

function drawMap(result) {
	
   // Added to test animation effect
	
	clearMarkers();

	eq = result.split("<br />");
	
	processHeader (eq[0]);
	
	if (outputformat === "Table") {
		GenerateTable(eq);
		return;
	}
	
	// if there is only one row of data, then there is only header
	if (eq[1] == "")
	 {
	
	 //alert("There is no Earthquake data!!!");
	 
	 currentLocation = new google.maps.LatLng(currentLat, currentLong);

		mapOptions = {

			center : currentLocation,

			zoom : 6,

			mapTypeId : google.maps.MapTypeId.ROADMAP

		};

		map = new google.maps.Map(document.getElementById("mapArea"), mapOptions);

	 } else {
	// _id, event_date, event_time, latitude, longitude, depth,
	// mag, magtype, nst, gap, dmin, rms, net, id,
	// updated_date, updated_time, place, type, horizontalerror,
	// deptherror, magerror, magnst, status, locationsource, magsource)

	var center = eq[1].split(",");

	var latitude = center[3];

	var longitude = center[4];

	centerLocation = new google.maps.LatLng(latitude, longitude);

	mapOptions = {

		center : centerLocation,

		zoom : 11,

		mapTypeId : google.maps.MapTypeId.ROADMAP

	};

	map = new google.maps.Map(document.getElementById("mapArea"), mapOptions);

	latlngbounds = new google.maps.LatLngBounds();

	var total = eq.length - 1;
	for (var i = 1; i <= total; i++)

	{
		drawMarker(eq[i], i, total);
	}
	
	map.fitBounds(latlngbounds);
	
	 }
	
}


function drawMarker(eq, index, total)

{

	var individual = eq.split(",");

	// alert("latitude: " + individual[3] + " " + "longitude: " +
	// individual[4]);

	var location = new google.maps.LatLng(individual[3], individual[4]);

	var marker = new google.maps.Marker({

		position : location,
		
		// Changed to test animation.  Comment out the following.
		// map : map

	});
	
	// set marker color
	if (Number(individual[6]) <= 2.0) {
		marker.setIcon('http://maps.google.com/mapfiles/ms/icons/green-dot.png');
	} else 
		if ((Number(individual[6]) > 2.0) && (Number(individual[6]) <= 4.0) ) {
			marker.setIcon('http://maps.google.com/mapfiles/ms/icons/blue-dot.png');
		}
		else 
			if ((Number(individual[6]) > 4.0) && (Number(individual[6]) <= 6.0) ) {
				marker.setIcon('http://maps.google.com/mapfiles/ms/icons/yellow-dot.png');
			}
			else 
				if ((Number(individual[6]) > 6.0) && (Number(individual[6]) <= 8.0) ) {
					marker.setIcon('http://maps.google.com/mapfiles/ms/icons/purple-dot.png');
				} else
					marker.setIcon('http://maps.google.com/mapfiles/ms/icons/red-dot.png');
	
	latlngbounds.extend(marker.position);

	var infoWindow = new google.maps.InfoWindow({

		content : "<b>Date:</b> " + individual[1] + 
		"<br /><b>Time:</b> " + individual[2] +
		"<br /><b>Latitude:</b> " + individual[3] +
		"<br /><b>Longitude:</b> " + individual[4] +
		"<br /><b>Magnitude:</b> " + individual[6] +
		"<br /><b>Depth:</b> " + individual[5] +
		"<br /><b>Place:</b> " + individual[16]

	});

	google.maps.event.addListener(marker, 'click', function() {

		infoWindow.open(map, this)

	});
	
	// Added to test animation effect
	// added the marker to the markers[]. 
	markers.push(marker);
	// set timeout duration.  The larger the total number of events
	// to display, the smaller the duration between timeouts
	var timeout = 0;
	
	if (animation === false)
		timeout = 0; 
	else
	if (total >= 5000) 
		   timeout = 1;
	else if (total < 5000 && total >= 1000)
		 timeout = 5;
	else if (total < 1000 && total >= 500)
		 timeout = 10;
	else
		timeout = 15;
	
	
	window.setTimeout(function() {
		       marker.setMap(map);
		    
		  }, index*timeout);
	   
}

//window.onload = function()
function initMap()
{
	// It seems the following getLocation() will always fail due to security issue
	//getLocation();
	
	// TODO:  for the time being, hard code the Taipei location in initial map
	currentLat = 25.0329694;
	
	currentLong = 121.5654177;
	
	currentLocation = new google.maps.LatLng(currentLat, currentLong);

	mapOptions = {

		center : currentLocation,

		zoom : 6,

		mapTypeId : google.maps.MapTypeId.ROADMAP

	};

	map = new google.maps.Map(document.getElementById("mapArea"), mapOptions);
	
	var imageBounds = {
		    north: 27.883469,
		    south: 25.406578,
		    east: 128.148774,
		    west: 122.638196
		  };
	var overlay = new google.maps.GroundOverlay(
		      'http://192.168.1.109:8080/EarthquakeServer2/present.png',
		      imageBounds);
	overlay.setMap(map);
	
}

function GenerateTable(eqList) {
	
	
	if (eqList[1] == "")
	 {
	 //alert("There is no Earthquake data!!!");
     // TODO:  how to handle the empty list	 
	 } else {
	
	
    //Create a HTML Table element.
    var table = document.createElement("TABLE");
    
    
    table.border = "2";
    
    //Get the count of columns.
    var columnCount = 8
 
    //Add the header row.
    var row = table.insertRow(-1);
    
    var headerCell = document.createElement("TH");
    headerCell.innerHTML = "No";
    row.appendChild(headerCell);
    headerCell = document.createElement("TH");
    headerCell.innerHTML = "Event Date";
    row.appendChild(headerCell);
    headerCell = document.createElement("TH");
    headerCell.innerHTML = "Event Time";
    row.appendChild(headerCell);
    headerCell = document.createElement("TH");
    headerCell.innerHTML = "Latitude";
    row.appendChild(headerCell);
    headerCell = document.createElement("TH");
    headerCell.innerHTML = "Longitude";
    row.appendChild(headerCell);
    headerCell = document.createElement("TH");
    headerCell.innerHTML = "Magnitude";
    row.appendChild(headerCell);
    headerCell = document.createElement("TH");
    headerCell.innerHTML = "Depth";
    row.appendChild(headerCell);
    headerCell = document.createElement("TH");
    headerCell.innerHTML = "place";
    row.appendChild(headerCell);
    
 // _id, event_date, event_time, latitude, longitude, depth,
	// mag, magtype, nst, gap, dmin, rms, net, id,
	// updated_date, updated_time, place, type, horizontalerror,
	// deptherror, magerror, magnst, status, locationsource, magsource)

    //Add the data rows.
    
	for (var i = 1; i <= eqList.length - 1; i++) {
        row = table.insertRow(-1);
        var center = eqList[i].split(",");
//        for (var j = 0; j < columnCount; j++) {
//            var cell = row.insertCell(-1);
//            cell.innerHTML = customers[i][j];
//        }
        var cell = row.insertCell(-1);
        cell.innerHTML = i.toString(); // number
        cell = row.insertCell(-1);
        cell.innerHTML = center [1]; // event_date
        cell = row.insertCell(-1);
        cell.innerHTML = center [2]; // event_time
        cell = row.insertCell(-1);
        cell.innerHTML = center [3]; // latitude
        cell = row.insertCell(-1);
        cell.innerHTML = center [4]; // longititude
        cell = row.insertCell(-1);
        cell.innerHTML = center [6]; // magnititude
        cell = row.insertCell(-1);
        cell.innerHTML = center [5]; // depth
        cell = row.insertCell(-1);
        cell.innerHTML = center [16]; // place
    }
	sorttable.makeSortable(table);
    var tableArea = document.getElementById("tableArea");
    tableArea.innerHTML = "";
    tableArea.appendChild(table);
   }
}



    