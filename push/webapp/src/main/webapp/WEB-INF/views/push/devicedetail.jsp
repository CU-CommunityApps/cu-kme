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

<kme:page title="Device Detail" id="push-webapp" backButton="true" homeButton="true" cssFilename="push" jsFilename="push">
	<kme:content>
	
			<kme:listView id="deviceslist" filter="false" dataTheme="c" dataInset="false">
	            <li data-role="list-divider">
	            	${device.deviceName}
	            </li>
	            <kme:listItem hideDataIcon="false">	            	            	
	            	<H3>Device Type:</H3>
	            	<p>${device.type}</p>	
	            </kme:listItem>
	            <kme:listItem hideDataIcon="false">
	            	<H3>Registration Id:</H3>
	            	<p>${device.regId}</p>
	            </kme:listItem>
	            <kme:listItem hideDataIcon="false">
					<H3>Username:</H3>
	            	<p>${device.username}</p>	
	          	</kme:listItem>
	            <kme:listItem hideDataIcon="false">            	            	 
					<H3>Device Id:</H3>
	            	<p>${device.deviceId}</p>	
	          	</kme:listItem>
	            <kme:listItem hideDataIcon="false">
					<H3>Registration Date:</H3>
	            	<p>${device.postedTimestamp}</p>
	            </kme:listItem>
	            
               	<kme:listItem>
				<fieldset class="ui-grid-a">
						<div class="ui-block-a"><button data-theme="c" id="remove" onClick="showConfirm('${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/device/remove/${device.deviceId}');return false;">Remove</button></div>
						<div class="ui-block-b"><a data-role="button" data-theme="a" id="send" href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/push/send?id=${device.id}">Send Push</a></div>

			    </fieldset>
               	</kme:listItem>	            
	            
			</kme:listView>
	</kme:content>
</kme:page>
