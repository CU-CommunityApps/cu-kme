<%--
  Copyright 2011 The Kuali Foundation Licensed under the Educational Community
  License, Version 2.0 (the "License"); you may not use this file except in
  compliance with the License. You may obtain a copy of the License at
  http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
  agreed to in writing, software distributed under the License is distributed
  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
  express or implied. See the License for the specific language governing
  permissions and limitations under the License.
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>

<kme:page title="Computer Labs" id="computerlabs" homeButton="true" backButton="true" cssFilename="computerlabs">
    <kme:content>
        <kme:listView id="computerlablist" dataTheme="c" dataDividerTheme="b" filter="false">
        
        <script type="text/javascript">
			$('[data-role=page][id=computerlabs]').live('pagebeforeshow', function(event, ui) {
				$('#clListTemplate').template('clListTemplate');
				refreshTemplate('computerlabs?campus=${campus}', '#computerlablist', 'clListTemplate', '<li>No labs were found</li>', function() {$('#computerlablist').listview('refresh');});
			});
		</script>
		<script id="clListTemplate" type="text/x-jquery-tmpl">
			<li data-role="list-divider">\${name}</li>
			{{each(i,lab) computerLabs}}
      			<li data-id="\${buildingCode}" detailId="\${labCode}">
					{{if buildingCode}}
						<a href="/mdot/maps?id=\${buildingCode}">
        					<h3>\${labCode}</h3>
        					<p>\${availability} seats available</p>
						</a>
					{{else}}
						<h3>\${labCode}</h3><p>\${availability} seats available</p>
					{{/if}}
      			</li>
			{{/each}}
		</script>
        <%--
            <c:forEach items="${lablocations}" var="location" varStatus="status">
                <kme:listItem dataTheme="b" dataRole="list-divider">${location.name}</kme:listItem>
	            <c:forEach items="${location.computerLabs}" var="computerlab" varStatus="status">
	                <kme:listItem cssClass="link-gps">
				       <c:choose>
			                <c:when test="${!empty computerlab.buildingCode}">
                                <a href="${pageContext.request.contextPath}/maps/location?id=${computerlab.buildingCode}"><h3>${computerlab.labCode}</h3><p>${computerlab.availability} seats available</p></a>
			                </c:when>
			                <c:otherwise>
                                <h3>${computerlab.labCode}</h3><p>${computerlab.availability} seats available</p>
			                </c:otherwise>
			            </c:choose>
	                </kme:listItem>
	            </c:forEach>
            </c:forEach>
        --%>
        </kme:listView>
    </kme:content>
</kme:page>
