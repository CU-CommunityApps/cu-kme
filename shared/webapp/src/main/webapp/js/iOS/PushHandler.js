/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
*	handleIncomingPush() gets called whenever a push notification comes in while the app is running
*	or in the background. If app is not running it will not fire, other signs of incoming push will
*	show, ie, Badge, Banner, Notifcation Center etc. 
*
*/

// Set a deviceready listener, this will execute onIncomingPush once the native app and the webview 
// is completely loaded. 

$(function(){	
	document.addEventListener("deviceready",onIncomingPush,false);	
	console.log("onIncomingPush Listener enabled");
});


// Deal with an inbound push when app is not running. Basically we have to use the native portion
// of the app to inject the JSON into the webview, then use this function to use it. 
function onIncomingPush(){
	
	// Get the hasPush variable from the native app. Which is set on start up if there is a push. 
	window.plugins.applicationPreferences.get('hasPush', function(result) {

			// If yes, grap the JSON formatted pushMessage, and get the pertenant parts out of it. 
			if(result == "YES"){
				

				
				var pushObj = jQuery.parseJSON(pushMessageJSON);
				var msg		= pushObj.aps.alert;
				var badge	= pushObj.aps.badge;
				var msgID	= pushObj.i;
				var emer	= pushObj.e;

				// Deal with it like any other incoming push notification. 
				handleIncomingPush(msg, msgID, emer, badge);

				// Reset the hasPush variable to NO, indicating that it has been dealt with. 
				window.plugins.applicationPreferences.set('hasPush','NO', function() {
						console.log("It is saved");
					}, function(error) {
						console.log("Failed to retrieve a setting: " + error);
					}
				);
			}
		}, function(error) {
			console.log("Failed to retrieve a setting: " + error);
		}
	);
}

function test(){
	alert("suck");
}

function handleIncomingPush(msg, mid, emergency, badge){
	// Get Detail for push message. 
	$.getJSON('http://mtwagner.dyndns.org:9999/mdot/push/get/' + mid ,function(json){
		
		// If the push is an Emergency, make a noise and vibrate.  
		if(emergency == "YES"){
			navigator.notification.beep(3);
			navigator.notification.vibrate(3000); 
		}
		
		// If there is no URL associated with the Push, just display message. 
		if(json.url == ""){
			navigator.notification.alert(
				json.message, 
				function(){
				
				}, 
				json.title, 
				'OK');
		}else{	// If there is a URL included in the Push, give an option to follow it. 
			navigator.notification.confirm(
				json.message, 
				function(button){
					if(button == 1){
						window.location = json.url;
					}
				}, 
				json.title, 
				'Open Link, Close');
		}
	});
}
