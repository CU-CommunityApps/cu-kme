<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="dining.title" var="title"/>

<kme:page title="${place}" id="dining" backButton="true" homeButton="true" cssFilename="dining" jsFilename="dining">
	<kme:content>
		<ul data-role="listview" id="dropdownHeader" data-theme="c" data-inset="false" data-filter="false" data-dividertheme="b">
            <li data-role="list-divider" data-theme="b" data-icon="arrow-d" class="dropdownHeader"><a id="toggleDropdown" href="#">Select a Menu</a></li>
        </ul>
		<kme:listView  id="dropdownMenu" dataTheme="c" dataInset="false" dataDividerTheme="b" filter="false">
			<script type="text/javascript">
				$('[data-role=page][id=dining]').live('pagebeforeshow', function(event, ui) {
					$('#dropdownMenuTemplate').template('dropdownMenuTemplate');
					refreshTemplate('${name}?location=${location}', '#dropdownMenu', 'dropdownMenuTemplate', '<li>No Menus to select</li>', 
							function() {$('#dropdownMenu').listview('refresh'); 
								});  
					
					$('#menuListTemplate').template('menuListTemplate');
					refreshTemplate('${name}?location=${location}', '#results', 'menuListTemplate', '<li>No Menus</li>', 
							function() {$('#results').listview('refresh'); 
								}); 
					});
			</script>
			
			<script id="dropdownMenuTemplate" type="text/x-jquery-tmpl">
				{{each(i,m) meal}}
					<li data-theme="c" class="dropdownItem" value="\${i + 1}">\${name}</li>
				{{/each}}
			</script>
		</kme:listView>
			
		<kme:listView id="results" filter="false" dataTheme="c" dataInset="false" dataDividerTheme="b">
		
			<script id="menuListTemplate" type="text/x-jquery-tmpl">
				{{each(i,m) meal}}
					{{if categories}}
					{{each categories}}
						<li class="contentItem category-\${i + 1}" data-role="list-divider" data-theme="b">\${title}
							{{if quantities}}
								{{each quantities}}
									<em> -\${$value}</em>
								{{/each}}
							{{/if}}
						</li>
						{{each items}}
							<li class="contentItem category-\${i + 1}" data-theme="c">
								
								<h3 class="wrap">\${this.title}</h3>
								{{if prices}}
									<p class="wrap">
									{{each prices}}
										<em>\${$value} </em>
									{{/each}}
									</p>
								{{/if}}
								{{if attributes}}
									<div class="nutritionIcons">
										{{each attributes}}
											<span class="nutrition nutrition-\${$value}">\${$value}</span> 
										{{/each}}
									</div>
								{{/if}}
							</li>
						{{/each}}
					{{/each}}
					{{else}}
						<li class="contentItem category-\${i + 1}" data-role="list-divider" data-theme="b">Not served</li>
					{{/if}}
		
				{{/each}}	
			</script>
					
		</kme:listView>
		
	</kme:content>
	
	<div id="legend">
    	<span class="closeLegend">Close</span>
    	<ul id="legendItems">
			<li><span class="nutrition-featured">100% trans fat free</span></li>
			<li><span class="nutrition-featured">Lower in fat</span></li>
			<li><span class="nutrition-featured">Lower in sugar and salt</span></li>
			<li><span class="nutrition-featured">Unprocessed ingredients</span></li>
		</ul>
	</div>
	
</kme:page>	