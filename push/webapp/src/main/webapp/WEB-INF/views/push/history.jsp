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

<kme:page title="Push History" id="push-webapp" backButton="true" homeButton="true" cssFilename="push" jsFilename="push">
	<kme:content>
		<kme:listView id="pushForm" filter="false" dataTheme="c" dataInset="false">
			<kme:listItem hideDataIcon="false">               
				A total of ${pushCount} push notifications.
        	</kme:listItem>


			<c:if test="${not empty pastPushes}">	
				<li data-role="list-divider">Past Pushes</li>
		        <c:forEach items="${pastPushes}" var="pastPush" varStatus="status">	            
		            <kme:listItem hideDataIcon="false">
						<c:if test="${not empty pastPush.url}">
							<a href="${pastPush.url}">
						</c:if>
				      			<h3>${pastPush.title}</h3>
				      			<p class="wrap"><strong>Message: ${pastPush.message}</strong></p>
				      			<c:if test="${not empty pastPush.url}">
				      				<p class="wrap"><strong>URL: ${pastPush.url}</strong></p>
				      			</c:if>
				      			<p>Number of Recipients: ${pastPush.recipients}</p>
				      			<p>Sender: ${pastPush.sender}</p>
				      			<p class="wrap">Time Posted: ${pastPush.postedTimestamp}</p>
						<c:if test="${not empty pastPush.url}">
							</a>
						</c:if>
		            </kme:listItem>
				</c:forEach>              
			</c:if>
			
		</kme:listView>
	</kme:content>
</kme:page>