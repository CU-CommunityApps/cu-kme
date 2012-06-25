<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:url var="back" value="/events/viewEvents">
	<c:param name="categoryId" value="${categoryId}" />
	<c:param name="campus" value="${campus}" />
</c:url>

<kme:page title="Event Detail" id="eventdetail" backButton="true" homeButton="true" backButtonURL="${back}">
	<kme:content>
	<kme:listView id="detail" dataTheme="c" dataDividerTheme="b" filter="false">
		<script type="text/javascript">
			$('[data-role=page][id=eventdetail]').live('pagebeforeshow', function(event, ui) {
			
				$('#detailTemplate').template('detailTemplate');
				refreshTemplate('viewEvent?eventId=${event}', '#detail', 'detailTemplate', '<li>No detail available</li>', 
					function() {$('#detail').listview('refresh'); 
						}); 
			});
		</script>
		<script id="detailTemplate" type="text/x-jquery-tmpl">
			<li><h3 class="wrap">\${title}</h3>
				<p class="wrap">\${displayStartDate}<br />\${displayStartTime} - \${displayEndTime}</p></li>
			<li><h3 class="wrap">Location</h3>
				<p class="wrap">\${location}</p></li>
			<li><h3 class="wrap">Description</h3>
				{{each description}}
					<p class="wrap">\${$value}</p>
				{{/each}}</li>
			<li><h3 class="wrap">Website</h3>
				{{if link}}
					<p class="wrap"><a href="\${link}">\${link}</a></p>
				{{else}}
					<p class="wrap">N/A</p>
				{{/if}}</li>
			<li><h3 class="wrap">Sponsor</h3>
				{{each contact}}
					<p class="wrap">\${$value.name}</p>
				{{/each}}</li>
		</script>
	</kme:listView>

	</kme:content>
</kme:page>
