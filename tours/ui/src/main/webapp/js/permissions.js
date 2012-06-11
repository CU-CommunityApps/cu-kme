jQuery(window).load(function() {
	$('#addViewPermissionButton').click(function() {
		var groupName = $('#newViewPermission').val();
		checkPermissionGroup(groupName, false);
	});
	$('#addEditPermissionButton').click(function() {
		var groupName = $('#newEditPermission').val();
		checkPermissionGroup(groupName, true);
	});
});

function checkPermissionGroup(groupName, edit) {
	if (groupName) {
		groupName = groupName.toUpperCase();
		if (!findExistingPermission(groupName, edit)) {
			validateGroup(groupName, edit);
		} else {
			alert('That group has already been added.');
		}	
	} else {
		alert('Please enter an ADS group.');
	}
}

function findExistingPermission(groupName, edit) {
	var result = false;
	if (!edit) {
		$('#viewPermissionsList li').each(function(index, element) {
			var group = $('span.group', element).text();
			if (group == groupName) result = true;
		});
	} else {
		$('#editPermissionsList li').each(function(index, element) {
			var group = $('span.group', element).text();
			if (group == groupName) result = true;
		});
	}
	return result;
}

function validateGroup(groupName, edit) {
	var url = contextPath + '/tours/verify?group=' + groupName;
	var request = $.ajax({
	  url: url,
	  dataType: 'json'
	});
	
	request.fail(function() {
		alert('There was a problem verifying the ADS group name.');
	});
	request.done(function(data) {
		if (data.valid){
			addPermissionGroup(groupName, edit)
		} else {
			alert(groupName + ' is not a valid ADS group.');
		}
	});
}

function addPermissionGroup(groupName, edit) {
	var li = $('<li></li>');
	var x = $('<button class="removePermissionLink"><a href="#">x</a></button>');
	x.click(removePermission);
	li.append(x);
	li.append('<span class="group">' + groupName + '</span>');
	
	if (!edit) {
		$('#publicTour').hide();
		$('#viewPermissionsList').append(li);
		$('#newViewPermission').val('');
	} else {
		$('#editPermissionsList').append(li);
		$('#newEditPermission').val('');
	}
}

function removePermission(event) {
	var li = $(this).parent().remove(); //remove the <li>
	
	//check if we removed the last view group
	if (!$('#viewPermissionsList li').length) {
		$('#publicTour').show();
	}
}