//  Copyright 2011 The Kuali Foundation Licensed under the Educational Community
//  License, Version 2.0 (the "License"); you may not use this file except in
//  compliance with the License. You may obtain a copy of the License at
//  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
//  agreed to in writing, software distributed under the License is distributed
//  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
//  express or implied. See the License for the specific language governing
//  permissions and limitations under the License.

trackMap = jQuery.parseJSON('{"Favorites":"Favorites","All":"All","AAP":"AAP","ARTS":"ARTS","CALS":"CALS","ENG":"ENG","HOTEL":"HOTEL","HUMEC":"HUMEC","ILR":"ILR","UNIV":"UNIV","Welcome Weekend":"WW"}');
filterMap = jQuery.parseJSON('{"Favorites":"My Personal Schedule","All":"All Sessions","AAP":"Architecture Art and Planning","ARTS":"Arts and Sciences","CALS":"Agriculture and Life Sciences","HOTEL":"Hotel Administration","HUMEC":"Human Ecology","ILR":"Industrial and Labor Relations","ENG":"Engineering","Welcome Weekend":"Welcome Weekend"}');
trackGroupMap = jQuery.parseJSON('{"All":"dayGroup1","AAP":"dayGroup1","ARTS":"dayGroup1","CALS":"dayGroup1","ENG":"dayGroup1","HOTEL":"dayGroup1","HUMEC":"dayGroup1","ILR":"dayGroup1","UNIV":"dayGroup1","WW":"dayGroup2"}');
eventDates = ['20120817', '20120818', '20120819', '20120820', '20120821', '20120822', '20120823', '20120824', '20120825', '20120826', '20120827', '20120828'];
trackLastSelected = "All";

/* run after page load */
$(window).load(function () {
	// old conference.js
	var currentTime = new Date();
	var month = currentTime.getMonth() + 1;
	var day = currentTime.getDate();
	var year = currentTime.getFullYear();
	var hours = currentTime.getHours();
	var nextHours = currentTime.getHours() + 1;
	var minutes = currentTime.getMinutes();
	var todayDate = year+""+month+""+day;

	todayDate = parseInt(todayDate);
	
	var selectedDate = "";
	var startDate = eventDates[0];
	var endDate = eventDates[eventDates.length - 1];
	if (todayDate < 20120817 || todayDate > 20120828) {
		selectedDate = 'allDays';
	} else {
		selectedDate = ('date-' + todayDate);
	}


// CU disabling, redundant with clickedDay (localstorage) functionality
//	$('div#daySelectTabs a').removeClass('selected');
//	$('div#daySelectTabs a#'+selectedDate).addClass('selected');
//	$('li.tDate').addClass('invisible');
//	// hide events on all dates by adding invisibleDate class
//	for (i = 0; i < eventDates.length; i++) {
//		$('li.date-' + eventDates[i]).addClass('invisibleDate');
//	}
//
//	// reshow events on selected date
//	$('li.' + selectedDate).removeClass('invisibleDate');
//	if (selectedDate == 'allDays') { 
//		$('li.sessionItem').removeClass('invisibleDate');
//		if ($('div#trackSelectTabs a#allTracks').hasClass('selected')) {
//			$('li.tDate').removeClass('invisible');
//		}
//	}
	
	// testing timeslots
	// var thisHour = 2011111609;
	// var nextHour = 2011111610;

	// actual time on device. Need to adjust time zone?
//	var thisHour = year+""+month+""+day+""+hours;
//	var nextHour = year+""+month+""+day+""+nextHours;
	
//	$('li.dateTime-' + thisHour + ' h3').prepend('<span class="sessionInProgress">IN PROGRESS:</span> ');
//	$('li.dateTime-' + nextHour + ' h3').prepend('<span class="sessionNextSession">NEXT SESSION:</span> ');
	
	// Session favorite star
//	$('li.sessionItem div a div.favorite').each(function(index) {
//		// limit this to 1000 sessions, just in case
//		if (index < 1000) {
//			var favoriteSessionTitle = $(this).html();
//			// Display star on/off based on presence of key
//			if (localStorage.getItem(favoriteSessionTitle) == "favorite") {
//				$(this).addClass('starOn');
//			}
//		}
//	});
	
	
	// Set selected track to localStorage saved track
	clickedTrack = null;
	if (localStorage)
		clickedTrack = localStorage.getItem('selectedTrack');
	if (clickedTrack == null) { 
		clickedTrack = "All";
		try {
			localStorage.setItem("selectedTrack", "All");
		} catch (e) {
//			if (e == QUOTA_EXCEEDED_ERR) {
//				//alert('HTML5 Local Storage quota exceeded. No items will be added.'); //quota exceeded, error
//			}
		}
	}

	$('div#trackSelectTabs a').removeClass('selected');
	$('div#trackSelectTabs a').each(function(index) {
		if ($(this).html() == clickedTrack) { $(this).addClass('selected'); }
	});

	// hide specific track buttons
	$('#allTracks').hide();
	
	var mappedTrack = trackMap[clickedTrack];
	var filteredTrack = filterMap[clickedTrack];
	var filteredGroup = trackGroupMap[mappedTrack];
	
	// hide the dayGroup that does not apply to this track
	$(".dayGroup").hide();
	$("#" + filteredGroup).show();
		
	$('li.selectedSessionsLabel').text(filteredTrack);
	$('li.sessionItem').addClass('invisible');
	$('li.tDate').addClass('invisible');
//	if (mappedTrack != 'Favorites') { $('div#trackSelectTabs a#favoritesSelector').removeClass('starOn'); }
	$('li.track-' + mappedTrack).removeClass('invisible');

	// always show UNIV events unless WW
	if (mappedTrack != "WW") {
		$('li.track-UNIV').removeClass('invisible');
	}
	
	// if track is all, remove WW events
	if (mappedTrack == 'All') { 
		$('li.sessionItem').removeClass('invisible');
		$('li.tDate').removeClass('invisible');
		$('li.track-WW').addClass('invisible');
		
		// hide session title divider if we're on all
		$('li.selectedSessionsLabel').hide();
	}

	// store chosen track, so we can determine changes for daygroups later on
	trackLastSelected = clickedTrack;

	// select favorites
//	if (mappedTrack == 'Favorites') { 
//		$('li.sessionItem div a div.favorite').each(function(index) {
//			$('div#trackSelectTabs a#favoritesSelector').addClass('starOn');
//			// limit this to 1000 sessions, just in case
//			if (index < 1000) {
//				var favoriteSessionTitle = $(this).html();
//				// Display star on/off based on presence of key
//				if (localStorage.getItem(favoriteSessionTitle) == "favorite") {
//					$(this).parents('li').removeClass('invisible');
//				}
//			}
//		});
//	}
	
	// Set selected day to localStorage saved day
	clickedDay = null;
	if (localStorage)
		clickedDay = localStorage.getItem('selectedDay');
	if (clickedDay == null) { 
		clickedDay = selectedDate;
		try {
			localStorage.setItem("selectedDay", clickedDay);
		} catch (e) {
//			if (e == QUOTA_EXCEEDED_ERR) {
//				//alert('HTML5 Local Storage quota exceeded. No items will be added.'); //quota exceeded, error
//			}
		}
	}
	
	$('div#daySelectTabs a').removeClass('selected');
	$('div#daySelectTabs a#' + clickedDay).addClass('selected');
	$('li.tDate').addClass('invisible');
	// hide events on these dates
	for (i = 0; i < eventDates.length; i++) {
		$('li.date-' + eventDates[i]).addClass('invisibleDate');
	}

	$('li.' + clickedDay).removeClass('invisibleDate');
	if (clickedDay == 'allDays') { 
		$('li.sessionItem').removeClass('invisibleDate');
		if ($('div#trackSelectTabs a#allTracks').hasClass('selected')) {
			$('li.tDate').removeClass('invisible');
		}
	}
	
	// display time sections if "all" selected
//	if (clickedDay == 'allDays' && mappedTrack == 'All') {
//	//if (clickedDay == 'allDays') {
//		$('li.tDate').removeClass('invisible');
//		$('li.tDate').removeClass('invisibleDate');
//	}

	confSessionsCheckFilterCount();
	
});

$(function() {

	$('div#trackSelectTabs a').click(
		function () {
			
			clickedTrack = $(this).html();

			$('div#trackSelectTabs a').removeClass('selected');
			$(this).addClass('selected');
 			var mappedTrack = trackMap[clickedTrack];
 			var filteredTrack = filterMap[clickedTrack];

 			$('li.selectedSessionsLabel').text(filteredTrack);
 			$('li.selectedSessionsLabel').show();
 			//$('li.sessionItem').fadeOut();
 			$('li.sessionItem').addClass('invisible');
 			$('li.tDate').addClass('invisible');
// 			if (mappedTrack != 'Favorites') { $('div#trackSelectTabs a#favoritesSelector').removeClass('starOn'); }
			$('li.track-' + mappedTrack).removeClass('invisible');

			// specifically show all UNIV classes
			if (mappedTrack != "WW") {
				$('li.track-UNIV').removeClass('invisible');
			}
			
			// hide the dayGroup that does not apply to this track
			var filteredGroup = trackGroupMap[mappedTrack];
			$(".dayGroup").hide();
			$("#" + filteredGroup).show();
			
			// if change track group, change day back to All
			var changeTrackDayGroup = trackGroupMap[trackLastSelected] != filteredGroup;
			trackLastSelected = clickedTrack;
			if (changeTrackDayGroup) {
				$("#allDays").click();
			}

			if (mappedTrack == 'All') { 
				$('li.sessionItem').removeClass('invisible');
				//$('li.tDate').removeClass('invisible');
			}
			//$('li.sessionItem').fadeIn();
			
			
			// remember which track was just selected
			try {
				localStorage.removeItem("selectedTrack");
			} catch (e) {
				//alert('Error:' + e);
			}
		
			try {
				localStorage.setItem("selectedTrack", clickedTrack);
			} catch (e) {
//				if (e == QUOTA_EXCEEDED_ERR) {
//					//alert('HTML5 Local Storage quota exceeded. No items will be added.'); //quota exceeded, error
//				}
			}
			
//			// display time sections if "all days" and "all tracks" selected
//			clickedTrack = localStorage.getItem('selectedTrack');
//			clickedDay = localStorage.getItem('selectedDay');
//			if (clickedDay == 'allDays' && clickedTrack == 'All') {
//				$('li.tDate').removeClass('invisible');
//				$('li.tDate').removeClass('invisibleDate');
//			}

			confSessionsCheckFilterCount();

		}
	);

	$('div#daySelectTabs a').click(
		function () {
			$('div#daySelectTabs a').removeClass('selected');
			$(this).addClass('selected');
			var todayDate = parseInt($('div#selectedDate').text());
			$('li.tDate').addClass('invisible');

			for (i = 0; i < eventDates.length; i++) {
				$('li.date-' + eventDates[i]).addClass('invisibleDate');
			}

			$('li.' + $(this).attr('id')).removeClass('invisibleDate');
			if ($(this).attr('id') == 'allDays') { 
				$('li.sessionItem').removeClass('invisibleDate');
				if ($('div#trackSelectTabs a#allTracks').hasClass('selected')) {
					$('li.tDate').removeClass('invisible');
				}
			}
			
			// remember which track was just selected
			try {
				localStorage.removeItem("selectedDay");
			} catch (e) {
				//alert('Error:' + e);
			}
		
			try {
				localStorage.setItem("selectedDay", $(this).attr('id'));
			} catch (e) {
//				if (e == QUOTA_EXCEEDED_ERR) {
//					//alert('HTML5 Local Storage quota exceeded. No items will be added.'); //quota exceeded, error
//				}
			}
			
			// display time sections if "all days" and "all tracks" selected
//			clickedTrack = localStorage.getItem('selectedTrack');
//			clickedDay = localStorage.getItem('selectedDay');
//			if (clickedDay == 'allDays' && clickedTrack == 'All') {
//				$('li.tDate').removeClass('invisible');
//				$('li.tDate').removeClass('invisibleDate');
//			}
			
			confSessionsCheckFilterCount();

		}
	);

	// show all favorite sessions, hide others
//	$('div#trackSelectTabs a#favoritesSelector').click(
//		function () {
//			$('li.sessionItem div a div.favorite').each(function(index) {
//				$('div#trackSelectTabs a#favoritesSelector').addClass('starOn');
//				// limit this to 1000 sessions, just in case
//				if (index < 1000) {
//					var favoriteSessionTitle = $(this).html();
//					// Display star on/off based on presence of key
//					if (localStorage.getItem(favoriteSessionTitle) == "favorite") {
//						$(this).parents('li').removeClass('invisible');
//					}
//				}
//			});
//			return false;
//		}
//	);
//
//	// toggle on and off session favoriting
//	$('div.favorite').click(
//		function () {
//			if (typeof (localStorage) == 'undefined') {
//				//alert('Your browser does not support HTML5 Local Storage.');
//			} else {
//				var favoriteSessionTitle = $(this).html();
//				// if this is already a favorite
//				if (localStorage.getItem(favoriteSessionTitle) == "favorite") {
//					try {
//						// un-favorite it!
//						localStorage.removeItem(favoriteSessionTitle);
//						$(this).removeClass('starOn');
//						//alert('Session removed from personal schedule.');
//					} catch (e) {
//						alert('Error:' + e);
//					}
//				} else {
//					// else, if it's not already a favorite
//					try {
//						// favorite it!
//						var favoriteSessionTitle = $(this).html();
//						localStorage.setItem(favoriteSessionTitle, "favorite"); //saves to local storage, "key", "value"
//						$(this).addClass('starOn');
//						//alert('Session added to personal schedule.')
//					} catch (e) {
//						if (e == QUOTA_EXCEEDED_ERR) {
//							alert('HTML5 Local Storage quota exceeded. No items will be added.'); //quota exceeded, error
//						}
//					}
//				}
//			}
//			return false;
//		}
//	);

});

function confSessionsCheckFilterCount() {
	
	// get the count of currently visible sessionItem
	var visibleSessionCount = $('li.sessionItem').filter(':visible').length;
	if (!visibleSessionCount) {
		$('li.sessionItemNone').show();
	} else {
		$('li.sessionItemNone').hide();
	}
}


/* Twitter */
String.prototype.linkify=function(){
	return this.replace(/[A-Za-z]+:\/\/[A-Za-z0-9-_]+\.[A-Za-z0-9-_:%&;\?\/.=]+/g,function(m){
		return m.link(m);
	});
};
String.prototype.linkuser=function(){
	return this.replace(/[@]+[A-Za-z0-9-_]+/g,function(u){
		return u.link("http://twitter.com/"+u.replace("@",""));
	});
};
String.prototype.linktag=function(){
	return this.replace(/[]+[A-Za-z0-9-_]+/,function(t){
		return t;
	});
};
//tweet global variables
var showTweetLinks='none';
var element='none';
var NUMBER_PER_SET = 20;
var tweetDataStr = "tweetData";
var tweetData = null;
// fetch tweets from the url, if it fails to it goes straight to getting any from local storage
function fetch_tweets(elem){
	//localStorage.removeItem(tweetDataStr); //flush out localStorage
	loadStoredTweets();
	element=$(elem);
	keyword=escape(element.attr('title'));
	num=element.attr('class').split(' ').slice(-1);
	var url="http://search.twitter.com/search.json?q="+keyword+"&rpp="+num+"&callback=?";
	//$.getJSON(url, function(data) {buildNewTweets(data); buildTweetData();});
	//buildTweetData();
	$.ajax({
		url: url,
		dataType: 'json',
		cache: false,
		timeout: 5000,
		success: buildNewTweets,
		error: oldTweets
	});
	//return false;
};

function oldTweets() {
	$('#localTweets').slideDown("fast").delay(5000).slideUp("slow");
	buildTweetData();
	
}

//loads local storage tweets into tweetdata and sets some global variables if ones were found
function loadStoredTweets(){
	if (typeof(localStorage) != 'undefined'){
		try {
			if (localStorage.getItem(tweetDataStr)){
				tweetData = JSON.parse(localStorage.getItem(tweetDataStr));
			}
		} catch (e) {
			alert(e);
		}
	}
};


//takes a tweet and returns a string of how old it is
function getPostTime(tweet) {
	var tTime = new Date(Date.parse(tweet.tTime));
	var cTime=new Date();
	var sinceMin=Math.round((cTime-tTime)/60000);
	if(sinceMin==0){
		var sinceSec=Math.round((cTime-tTime)/1000);
		if(sinceSec<10)
			var since='less than 10 seconds ago';
		else if(sinceSec<20)
			var since='less than 20 seconds ago';
		else
			var since='half a minute ago';
	}
	else if(sinceMin==1){
		var sinceSec=Math.round((cTime-tTime)/1000);
		if(sinceSec==30)
			var since='half a minute ago';
		else if(sinceSec<60)
			var since='less than a minute ago';
		else
			var since='1 minute ago';
	}
	else if(sinceMin<45)
		var since=sinceMin+' minutes ago';
	else if(sinceMin>44&&sinceMin<60)
		var since='about 1 hour ago';
	else if(sinceMin<1440){
		var sinceHr=Math.round(sinceMin/60);
		if(sinceHr==1)
			var since='about 1 hour ago';
		else
			var since='about '+sinceHr+' hours ago';
	}
	else if(sinceMin>1439&&sinceMin<2880)
		var since='1 day ago';
	else{
		var sinceDay=Math.round(sinceMin/1440);
		var since=sinceDay+' days ago';
	}
	return since;
};

//converts the tweetdata into the completed html format
function buildTweetData(){
	var deleteNum = 0;
	$.each(tweetData, function(key, tweet) {
		if (deleteNum < NUMBER_PER_SET){
			var since = getPostTime(tweet);
			var tweetBy='<strong>@'+tweet.user+'</strong> <span class="tweet-time">'+since+'</span>';
			var completeTweet = tweet.tweetInfo + tweetBy + '</p></li>';
			element.append(completeTweet);
			deleteNum += 1;
		} else {
			return false;
		}
	});
	tweetData.splice(0, deleteNum);
	if (tweetData == null || tweetData.length == 0){
		$('#loadTweets p').text(''); // end of tweets
	} else {
		$('#loadTweets p').text('Loading...');
	}
};

//checks tweetData for if this tweet was already recorded
function notOldTweet(newId){
	var bool = true;
	if (tweetData != null){
		$(tweetData).each(function(){
			if (this.id == newId) {
				bool = false;
				return false;
			}
		});
	}
	return bool;
};

//builds the first part of the tweet into html and then stores it into tweetdata for more
//data conversion
function buildNewTweets(data){
	var newTweets = [];
	$(data.results).each(function(){
		var id = this.id_str;
		if (notOldTweet(id)) {
			var tTime=this.created_at;
			var tweet='<li class="tweetRow" style="background-image: url('+this.profile_image_url+')"><p class="text">'+this.text.linkify().linkuser().linktag().replace(/<a/g,'<strong').replace(/<\/a/g,'</strong')+'<br />';

			var tweetPair = {
					id: id,
					tTime: tTime,
					user: this.from_user,
					tweetInfo: tweet
			};
			newTweets.push(tweetPair);
		} else {
			return false;
		}
	});
	if (tweetData != null){
		tweetData = newTweets.concat(tweetData);
	} else {
		tweetData = newTweets;
	}
	if (typeof(localStorage) != 'undefined'){
		try {
			localStorage.setItem(tweetDataStr, JSON.stringify(tweetData));
		} catch (e) {
		}
	}
	buildTweetData();
};

//checks for if you have scrolled to the bottom and if so loads in more tweets from tweetData
function checkPosition(){
	var docViewTop = $(window).scrollTop();
	var totalHeight = window.innerHeight ? window.innerHeight : $(window).height();
    var docViewBottom = docViewTop + totalHeight;
    
    var elemTop = $('#loadTweets').offset().top;
    var elemBottom = elemTop + $('#loadTweets').height();
    if (docViewTop > 0 && tweetData != null && tweetData.length > 0 && (elemBottom >= docViewTop) && (elemTop <= docViewBottom)
      && (elemBottom <= docViewBottom) && (elemTop >= docViewTop)){
    	buildTweetData();
    }
};

//starts all tweet functions
function initialize(){
	showTweetLinks=showTweetLinks.toLowerCase();
	if(showTweetLinks.indexOf('all')!=-1)
		showTweetLinks='reply,view,rt';
	$('.twitStream').each(function(){
		fetch_tweets(this);
	});
	notFirstLoad = true;
};


//testing
function blah(){
	var imageUrl = this.profile_image_url;
	var ctx = canvas.getContext("2d");
    ctx.drawImage(imageUrl, 0, 0);
	alert(ctx.width);
};