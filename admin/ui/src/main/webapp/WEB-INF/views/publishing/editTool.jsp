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
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<spring:message code="edittool.title" var="title"/>
<kme:page title="${title}" id="edittool" backButton="true" homeButton="true" cssFilename="publishing">
	<kme:content>
		<script type="text/javascript">
			$(function(){
				$('a.deleteButton').click(function() {
					if(!confirm('Are you sure you want to delete this tool?')) {
						return false;
					}
				});
				
			});
		</script>
		<form:form action="${pageContext.request.contextPath}/publishing/tool/edit" commandName="tool" data-ajax="false" method="post">
			<form:hidden path="toolId"/>
	    	<form:hidden path="versionNumber"/>
			<fieldset>
				<kme:listView id="layoutlist" dataTheme="c" dataDividerTheme="b" filter="false">
					<kme:listItem dataRole="list-divider">
						<spring:message code="edittool.presentation.title"/>
					</kme:listItem>
					<kme:listItem>
						<label for="title"><spring:message code="edittool.tool.title"/>:</label>
						<form:input path="title" size="80" />
						<form:errors path="title" />
						<label for="subtitle"><spring:message code="edittool.tool.subtitle"/>:</label>
						<form:input path="subtitle" size="80" />
						<form:errors path="subtitle" />
						<label for="iconUrl"><spring:message code="edittool.tool.iconurl"/>:</label>
				   		<form:input path="iconUrl"  size="80" />
						<form:errors path="iconUrl" />
					</kme:listItem>
					<kme:listItem dataRole="list-divider">
						<spring:message code="edittool.link.title"/>
					</kme:listItem>
					<kme:listItem>
						<label for="url"><spring:message code="edittool.tool.url"/>:</label>
						<form:input path="url" size="80" />
						<form:errors path="url" />
					</kme:listItem>
					<kme:listItem dataRole="list-divider">
						<spring:message code="edittool.metadata.title"/>
					</kme:listItem>
					<kme:listItem>
						<label for="description"><spring:message code="edittool.tool.description"/>:</label>
						<form:textarea path="description" rows="4" cols="80" />
						<form:errors path="description" />
						<label for="contacts"><spring:message code="edittool.tool.contacts"/>:</label>
				   		<form:input path="contacts"  size="80" />
						<form:errors path="contacts" />
						<label for="keywords"><spring:message code="edittool.tool.keywords"/>:</label>
				   		<form:input path="keywords"  size="80" />
						<form:errors path="keywords" />
						<label for="alias"><spring:message code="edittool.tool.alias"/>:</label>
						<form:input path="alias" size="80" />
						<form:errors path="alias" />
					</kme:listItem>
					<kme:listItem>					
						<div data-inline="true">
		                	<div class="ui-grid-a">
		                    	<div class="ui-block-a">
		                    		<input data-theme="a" class="submit" type="submit" id="saveButton" value="<spring:message code="common.save"/>" />
		                    	</div>
		                    	<div class="ui-block-b">
		                    		<a data-theme="c"  href="${pageContext.request.contextPath}/publishing/tool" data-role="button"><spring:message code="common.cancel"/></a>                    		
		                    	</div>
		                	</div>
		                	<a class="deleteButton" href="${pageContext.request.contextPath}/publishing/tool/delete/${tool.toolId}" data-role="button"><spring:message code="tools.delete.title"/></a>
		            	</div>						
					</kme:listItem>
				</kme:listView>
			</fieldset>
		</form:form>
		
		
	</kme:content>
</kme:page>


