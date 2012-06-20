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

<kme:page title="Push Devices" id="push-webapp" backButton="true" homeButton="true" cssFilename="push" jsFilename="push">
	<kme:content>
	
	<%-- 
			<kme:listView id="pushForm" filter="false" dataTheme="c" dataInset="false">
            	<kme:listItem>                
					A total of ${deviceCount} devices.
               	</kme:listItem>
			</kme:listView>
	--%>
			<kme:listView id="deviceslist" filter="false" dataTheme="c" dataInset="false">
            	<kme:listItem>                
					A total of ${deviceCount} devices.
               	</kme:listItem>

			<li data-role="list-divider">iOS Devices</li>
	        <c:forEach items="${iosdevices}" var="iosdevice" varStatus="status">	            
	            <kme:listItem hideDataIcon="false">
	            		<a href="${pageContext.request.contextPath}/device/detail/${iosdevice.deviceId}">
			      			<h3>${iosdevice.deviceName}</h3>
			      			<p class="wrap"><strong>Username:${iosdevice.username}</strong></p>
			      			<p class="wrap"><strong>Dev ID:${iosdevice.deviceId}</strong></p>
			      			<p class="wrap">Reg ID: ${iosdevice.regId}</p>
			      		</a>
	            </kme:listItem>
			</c:forEach>              

			<li data-role="list-divider">Android Devices</li>
	        <c:forEach items="${androiddevices}" var="androiddevice" varStatus="status">	            
	            <kme:listItem hideDataIcon="false">
	            		<a href="${pageContext.request.contextPath}/device/detail/${androiddevice.deviceId}">
			      			<h3>${androiddevice.deviceName}</h3>
			      			<p class="wrap"><strong>Dev ID:${androiddevice.deviceId}</strong></p>
			      			<p class="wrap">Reg ID: ${androiddevice.regId}</p>
						</a>
	            </kme:listItem>
			</c:forEach>              
			</kme:listView>




	</kme:content>
</kme:page>
