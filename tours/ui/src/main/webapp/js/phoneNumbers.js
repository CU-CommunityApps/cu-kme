jQuery(window).load(function() {
	$('#addPhoneNumberButton').click(function() {
		var part1 = $.trim($('#newPhoneNumber1').val());
		var part2 = $.trim($('#newPhoneNumber2').val());
		var part3 = $.trim($('#newPhoneNumber3').val());
		var name = $.trim($('#phoneNumberLabel').val());
		checkPhoneNumber(name, part1, part2, part3);
	});
});

function checkPhoneNumber(name, part1, part2, part3) {
	if (!part1 || !part2 || !part3){
		alert('All three parts of the phone number are required.');
	} else {
		if (part1.length != 3 || part2.length != 3 || part3.length != 4 || !isNumber(part1) || !isNumber(part2) || !isNumber(part3)) {
			alert('(' + part1 + ') ' + part2 + '-' + part3 + ' is not a valid phone number.');
		} else {
			addPhoneNumber(name, part1, part2, part3);
		}
	}
}


function addPhoneNumber(name, part1, part2, part3) {
	var li = $('<li data-value="' + part1+part2+part3 +'"data-name="' + name + '"></li>');
	var x = $('<button class="removePhoneNumberLink"><a href="#">x</a></button>');
	x.click(removePhoneNumber);
	li.append(x);
	var spanText;
	if (name) {
		spanText = name + ' (' + part1 + ') ' + part2 + '-' + part3;
	} else {
		spanText = '(' + part1 + ') ' + part2 + '-' + part3;
	}
	li.append('<span>' + spanText + '</span>');
	
	$('ul#phoneNumbers').append(li);
	$('#newPhoneNumber1').val('');
	$('#newPhoneNumber2').val('');
	$('#newPhoneNumber3').val('');
	$('#phoneNumberLabel').val('');
}

function removePhoneNumber(event) {
	var li = $(this).parent().remove(); //remove the <li>
}

function addPhoneNumbersToPoi(place) {
	place.phoneNumbers = new Array();
	$('ul#phoneNumbers li').each(function (index, item) {
		var number = $(item).attr('data-value');
		var name = $(item).attr('data-name');
		var phoneNumber = new Object();
		phoneNumber.value = number;
		phoneNumber.name = name;
		place.phoneNumbers.push(phoneNumber);
	});
}

function populatePhoneNumbers(numbers) {
	if (numbers) {
		for (var i=0; i< numbers.length; i++) {
			var number = numbers[i];
			if (number.value.length == 10) {
				var part1 = number.value.substring(0, 3);
				var part2 = number.value.substring(3, 6);
				var part3 = number.value.substring(6);
				
				addPhoneNumber(number.name, part1, part2, part3);
			}
		}
	}
}

function clearPhoneNumbers() {
	$('#newPhoneNumber1').val('');
	$('#newPhoneNumber2').val('');
	$('#newPhoneNumber3').val('');
	$('#phoneNumberLabel').val('');
	$('ul#phoneNumbers').empty();
}

function isNumber(n) {
	return !isNaN(parseFloat(n)) && isFinite(n);
}
