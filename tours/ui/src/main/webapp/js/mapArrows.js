//http://www.wolfpil.de/v3/arrow-heads.html

function ArrowHandler() {
	this.setMap(map);
	// MapCanvasProjection is only available after draw has been called.
	// this.draw = function() {};
	// Markers with 'head' arrows must be stored
	this.arrowheads = [];
}

ArrowHandler.prototype = new google.maps.OverlayView();

// Draw is inter alia called on zoom change events.
// So we can use the draw method as zoom change listener
ArrowHandler.prototype.draw = function() {
	if (this.arrowheads.length > 0) {
		for (var i = 0, m; m = this.arrowheads[i]; i++) {
			var gMap = map;
			if (this.getSegmentLengthInPixels(m.p1, m.p2) < 30) gMap = null;
			m.setOptions({ map: gMap });
		}
	}
};

//Computes the length of a line segment in pixels
ArrowHandler.prototype.getSegmentLengthInPixels = function(p1, p2) {
	var proj = this.getProjection();
	var g = google.maps;
	var pix1 = proj.fromLatLngToContainerPixel(p1);
	var pix2 = proj.fromLatLngToContainerPixel(p2);
	var vector = new g.Point(pix2.x - pix1.x, pix2.y - pix1.y);
	return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
};

// Returns the triangle icon object
ArrowHandler.prototype.addIcon = function(file) {
	var g = google.maps;
	var icon = new g.MarkerImage("http://www.google.com/mapfiles/" + file,
	new g.Size(24, 24), null, new g.Point(12, 12));
	return icon;
};

// Creates markers with corresponding triangle icons
ArrowHandler.prototype.create = function(p1, p2) {
	var markerpos;
	var g = google.maps;
	markerpos = g.geometry.spherical.interpolate(p1, p2, .5);
	// Compute the bearing of the line in degrees
	var dir = g.geometry.spherical.computeHeading(p1, p2).toFixed(1);
	// round it to a multiple of 3 and correct unusable numbers
	dir = Math.round(dir/3) * 3;
	if (dir < 0) dir += 240;
	if (dir > 117) dir -= 120;
	// use the corresponding icon
	var icon = this.addIcon("dir_" +dir+ ".png");
	var gMap = map;
	if (this.getSegmentLengthInPixels(p1, p2) < 30) gMap = null;
	var marker = new g.Marker({position: markerpos,
		map: gMap, icon: icon, clickable: false
	});
	// Store markers to adjust their offset position on zoom change
	marker.p1 = p1;
	marker.p2 = p2;
	this.arrowheads.push(marker);
};

ArrowHandler.prototype.load = function (points) {
	if (this.arrowheads.length > 0){
		for (var i = 0; i < this.arrowheads.length-1; i++) {
			this.arrowheads[i].setMap(null);
			this.arrowheads[i] = null;
		}
	}
	this.arrowheads = [];
	for (var i = 0; i < points.length-1; i++) {
		var p1 = points[i],
		p2 = points[i + 1];
		this.create(p1, p2);
	}
};

ArrowHandler.prototype.hideArrows = function() {
	if (this.arrowheads.length > 0){
		for (var i = 0; i < this.arrowheads.length; i++) {
			this.arrowheads[i].setMap(null);
		}
	}
};