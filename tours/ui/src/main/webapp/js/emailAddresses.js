jQuery(window).load(function() {
	$('#addEmailButton').click(function() {
		var address = $.trim($('#newEmailAddress').val());
		var name = $.trim($('#emailName').val());
		if (!address){
			alert('An email address is required.');
		} else {
			addEmailAddress(name, address);
		}
	});
});

function addEmailAddress(name, address) {
	var li = $('<li data-value="' + address +'"data-name="' + name + '"></li>');
	var x = $('<button class="removeEmailLink"><a href="#">x</a></button>');
	x.click(removeEmailAddress);
	li.append(x);
	var spanText;
	if (name) {
		spanText = name + ' ' + address;
	} else {
		spanText = address;
	}
	li.append('<span>' + spanText + '</span>');
	
	$('ul#emailAddresses').append(li);
	$('#newEmailAddress').val('');
	$('#emailName').val('');
}

function removeEmailAddress(event) {
	var li = $(this).parent().remove(); //remove the <li>
}

function addEmailAddressesToPoi(place) {
	place.emailAddresses = new Array();
	$('ul#emailAddresses li').each(function (index, item) {
		var address = $(item).attr('data-value');
		var name = $(item).attr('data-name');
		var email = new Object();
		email.address = address;
		email.name = name;
		place.emailAddresses.push(email);
	});
}

function populateEmailAddresses(addresses) {
	if (addresses) {
		for (var i=0; i< addresses.length; i++) {
			var email = addresses[i];
			if (email.address) {
				addEmailAddress(email.name, email.address);
			}
		}
	}
}

function clearEmailAddresses() {
	$('#newEmailAddress').val('');
	$('#emailName').val('');
	$('ul#emailAddresses').empty();
}
