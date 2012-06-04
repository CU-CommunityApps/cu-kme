/*
  Copyright 2011-2012 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
*/

// expanded form
var expanded = 0;

var legendVisible = 0;

var menuHeight = 0;

$(document).ready(function() {
    $('li.contentItem').hide();
    $('ul#dropdownHeader span.ui-icon').hide();
    
    var thisCount = 0;
    $('li.contentItem').each(function(index) {
    	thisCount = 0;
    	$($(this).children('div.nutritionIcons')).each(function(index) {
    		$($(this).children('span.nutrition')).each(function(index) {
    			thisCount = thisCount + 1;
    		});
        });
    	$(this).css('padding-right',(thisCount*40)+"px");
	});
});

if (!$.support.transition)
    $.fn.transition = $.fn.animate;

$(function() {
    $('a#toggleDropdown').click(
        function () {
            if (expanded == 0) {
                $('ul#results').transition({
                    y: $('ul#dropdownMenu').height() + 'px',
                    easing: 'in-out',
                    duration: '300ms'
                });
                $('li.dropdownHeader span.ui-icon').removeClass('ui-icon-arrow-d');
                $('li.dropdownHeader span.ui-icon').addClass('ui-icon-arrow-u');
                expanded = 1;
            } else {
                $('ul#results').transition({
                    y: '0px',
                    easing: 'in-out',
                    duration: '300ms'
                });
                $('li.dropdownHeader span.ui-icon').removeClass('ui-icon-arrow-u');
                $('li.dropdownHeader span.ui-icon').addClass('ui-icon-arrow-d');
                expanded = 0;
            }
            return false;
        }
    );
});

function loadContents(passedItem, itemName){
    $('.dropdownHeader a').text(itemName);
    $('li.contentItem').hide();
    $('li.category-' + passedItem).show();
}

$(function() {
    $('li.dropdownItem').click(
        function () {
        	$('ul#dropdownHeader span.ui-icon').show();
            viewportHeight = $(window).height();
            var str = $(this).text();
            selectedItem = $(this).attr("value");
            $('.dropdownHeader a').text('Loading.....');
            $('ul#results').transition({ y: viewportHeight, easing: 'in-out', duration: '500ms'}, function() { loadContents(selectedItem, str); });
            // flip dropdown menu header arrow
            $('li.dropdownHeader span.ui-icon').removeClass('ui-icon-arrow-u');
            $('li.dropdownHeader span.ui-icon').addClass('ui-icon-arrow-d');
            $('li.dropdownHeader span.ui-icon').prop('data-icon','arrow-d');
            $('ul#results').transition({ y: '0px', easing: 'in-out', duration: '500ms'});
            expanded = 0;
            return false;
        }
    );
});

$(function() {
    $('span.nutrition').click(
        function () {
            viewportHeight = $(window).height();
            selectedItem = $(this).text();
            if (legendVisible == 1) {
        		$('div#legend').transition({ y: '-500px', easing: 'in-out', duration: '500ms'});
        		legendVisible = 0;
        	} else {
	            $('div#legend').transition({ y: Math.floor( $(this).offset().top + 400) + 'px', easing: 'in-out', duration: '500ms'}, function() {
	            		legendVisible = 1;
	        		}
	            );
        	}
            return false;
        }
    );
});

$(function() {
    $('span.closeLegend').click(
        function () {
        	if (legendVisible == 1) {
        		$('div#legend').transition({ y: '-500px', easing: 'in-out', duration: '500ms'});
        		legendVisible = 0;
        		return false;
        	}
        }
    );
});


$(document).bind("scrollstart", function() {
	if (legendVisible == 1) {
		$('div#legend').transition({ y: '-500px', easing: 'in-out', duration: '500ms'});
		legendVisible = 0;
	}
});
