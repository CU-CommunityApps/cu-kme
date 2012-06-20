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

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<kme:page title="Push Notifications" id="push-webapp" backButton="true" homeButton="true" cssFilename="push" jsFilename="push">
	<style>

	</style>

	<kme:content>
        <form:form action="${pageContext.request.contextPath}/push" commandName="push" data-ajax="false" method="post"> 
			<input type="hidden" name="id" value="${device.id}"/>
			<kme:listView id="pushForm" filter="false" dataTheme="c" dataInset="false">

			<c:if test="${not empty device}">
            	<kme:listItem>                
		      		<h3>Recipient:</h3> 
		      		<p><strong>${device.deviceName}</strong></p>
		      		<p class="wrap">${device.deviceId}</p>
               	</kme:listItem>               
			</c:if>
			
            	<kme:listItem>                
                    <label for="title">Title:</label>
                    <form:textarea path="title" cols="40" rows="8" class="required" />
                    <form:errors path="title"/>
		        </kme:listItem>                

               	<kme:listItem>
                    <label for="message">Message:</label>
                    <form:textarea path="message" cols="40" rows="8" class="required" />
                    <form:errors path="message"/>
		        </kme:listItem>

               	<kme:listItem>
                    <label for="url">URL:</label>
                    <form:input path="url" cols="40" rows="8" placeholder="Optional (include 'http://')" class="required" />
                    <form:errors path="url"/>
		        </kme:listItem>

               	<kme:listItem>				
					<form:checkbox path="emergency" value="Y" label="Emergency"/>
		        </kme:listItem>

               	<kme:listItem>
                    <label>Push Messages:</label>
                    <select data-native-menu="false" id="pushselect" onchange="selectDidChange($('#pushselect option:selected').text(), $('#pushselect').val())">
                    	<c:forEach items="${stockPushMessages}" var="msg">
                    		<option value="${msg.value}">${msg.key}</option>
                    	</c:forEach>
                    </select>
		        </kme:listItem>

               	<kme:listItem>
				<fieldset class="ui-grid-a">
						<div class="ui-block-a"><a href="${pageContext.request.contextPath}/push" data-role="button" data-theme="c" id="clear">Clear</a></div>
						<div class="ui-block-b"><input data-theme="a" class="submit" type="submit" value="Push"/></div>
			    </fieldset>
               	</kme:listItem>
			</kme:listView>			
        </form:form>

<br/>

			<kme:listView id="pushForm" filter="false" dataTheme="c" dataInset="true">
            	<kme:listItem>                
					<a href=${pageContext.request.contextPath}/device/list>
			      		<h3>Registered Devices</h3>
			      		<p class="wrap">View a list of devices registered for Push Notifications</p>
			      	</a>			
               	</kme:listItem>
			</kme:listView>
			
			<kme:listView id="pushForm" filter="false" dataTheme="c" dataInset="true">
            	<kme:listItem>                
					<a href=${pageContext.request.contextPath}/push/history>
			      		<h3>Push History</h3>
			      		<p class="wrap">View a list past Push Notifications</p>
			      	</a>			
               	</kme:listItem>
			</kme:listView>

	</kme:content>
</kme:page>
