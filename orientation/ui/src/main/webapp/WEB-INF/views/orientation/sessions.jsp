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

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:message code="sessions.title" var="title"/>
<spring:message code="sessions.noresults" var="noresults"/>
<spring:message code="abbr.wednesday" var="wed"/>
<spring:message code="abbr.thursday" var="thu"/>
<spring:message code="abbr.friday" var="fri"/>
<spring:message code="abbr.saturday" var="sat"/>
<spring:message code="abbr.sunday" var="sun"/>
<spring:message code="abbr.monday" var="mon"/>
<spring:message code="abbr.tuesday" var="tue"/>
<spring:message code="abbr.alldays" var="all"/>
<spring:message code="label.time" var="time"/>
<spring:message code="label.date" var="date"/>
<spring:message code="label.location" var="location"/>
<spring:message code="label.trackdescr" var="trackdescr"/>
<spring:message code="label.description" var="description"/>
<spring:message code="label.ends" var="ends"/>
<spring:message code="time.tbd" var="tbd"/>

<kme:page title="${title}" id="orientation" backButton="true" backButtonURL="${pageContext.request.contextPath}/orientation" homeButton="true" cssFilename="orientation" jsFilename="orientation" appcacheFilename="kme.appcache">
	<kme:content>
		
		<div id="trackSelectTabs">
<%-- 			<a id="favoritesSelector" class="${param.track eq 'Favorites' ? 'selected' : ''}" href="#">Favorites</a> --%>
			<a id="allTracks" class="ALL selected ${param.track eq '' ? 'selected' : ''}" href="#">All</a>
			<a class="${param.track eq 'AAP' ? 'selected' : ''}" href="#">AAP</a>
			<a class="${param.track eq 'ARTS' ? 'selected' : ''}" href="#">ARTS</a>
			<a class="${param.track eq 'CALS' ? 'selected' : ''}" href="#">CALS</a>
			<a class="${param.track eq 'ENG' ? 'selected' : ''}" href="#">ENG</a>
			<a class="${param.track eq 'HOTEL' ? 'selected' : ''}" href="#">HOTEL</a>
			<a class="${param.track eq 'HUMEC' ? 'selected' : ''}" href="#">HUMEC</a>
			<a class="${param.track eq 'ILR' ? 'selected' : ''}" href="#">ILR</a>
			<a class="${param.track eq 'WW' ? 'selected' : ''}" href="#">Welcome Weekend</a>
		</div>
	
		<div id="daySelectTabs">

			<a id="allDays" href="#">All</a>

			<div id="dayGroup1" class="dayGroup" style="display:inline;">
				<a id="date-20120817" href="#">Fri</a>
				<a id="date-20120818" href="#">Sat</a>
				<a id="date-20120819" href="#">Sun</a>
				<a id="date-20120820" href="#">Mon</a>
				<a id="date-20120821" href="#">Tues</a>
			</div>

			<div id="dayGroup2" class="dayGroup" style="display:inline;">
				<a id="date-20120822" href="#">Wed</a>
				<a id="date-20120823" href="#">Thurs</a>
				<a id="date-20120824" href="#">Fri</a>
				<a id="date-20120825" href="#">Sat</a>
				<a id="date-20120826" href="#">Sun</a>
				<a id="date-20120827" href="#">Mon</a>
				<a id="date-20120828" href="#">Tues</a>
			</div>

		</div>
		
		<kme:listView id="sessionsBlock" dataTheme="c" dataDividerTheme="b">
			<kme:listItem dataRole="list-divider" cssClass="selectedSessionsLabel">All Sessions</kme:listItem>

			<c:set var="currentTime" value="" scope="request" />

			<c:forEach items="${sessions}" var="session" varStatus="status">
<%-- 				<c:if test="${currentTime ne session.startTime}"> --%>
<%-- 				<kme:listItem dataRole="list-divider" cssClass="tDate ${session.dateCSSClass}"> --%>
<%-- 					<fmt:parseDate value="${session.startTime}" pattern="yyyy-MM-dd HH:mm:ss" var="tdate"/> --%>
<%-- 					<fmt:formatDate value="${tdate}" pattern="E hh:mm a"/> --%>
<%-- 				</kme:listItem> --%>
<%-- 				</c:if> --%>

		    	<kme:listItem cssClass="sessionItem ${session.trackCSSClass} ${session.dateCSSClass} ${session.dateTimeCSSClass}">
		    		<a href="sessionDetails/${session.id}">
		    			<h3 class="wrap">${session.title}</h3>

	    				<c:if test="${fn:length(session.flags) != 0}">
			    			<div class="sessionIcons wrap">
							<p class="wrap sessionIcons">
		    					<c:forEach items="${fn:split(session.flags,'|')}" var="flag">
										<div class="sessionIcon${flag} sessionIcon"></div>
		    					</c:forEach>
							</p>
			    			</div>
	    				</c:if>

	    				<p class="wrap">
	    				<c:choose>
		    				<c:when test="${not empty session.track && session.track == 'UNIV'}">
	    						<em>University-wide</em><br />
			    			</c:when>
		    			</c:choose>
	
<%-- 		    			<p class="wrap">${date}: --%>
			    			<%-- <c:choose> --%>
			    				<c:if test="${not empty session.startTime && session.startTime != 'null'}">
									<fmt:parseDate value="${session.startTime}" pattern="yyyy-MM-dd HH:mm:ss" var="sdate"/>
									<fmt:formatDate value="${sdate}" pattern="EEEEEE M/dd" />
					    			<%-- <c:if test="${not empty session.endTime && session.endTime != 'null'}">
					    				 - 	<fmt:parseDate value="${session.endTime}" pattern="yyyy-MM-dd HH:mm:ss" var="edate"/>
											<fmt:formatDate value="${edate}" pattern="E MM-dd"/>
					    			</c:if> --%>
			    				</c:if>
			    				<%-- <c:otherwise>
			    					<c:choose>
					    				<c:when test="${not empty session.endTime && session.endTime != 'null'}">
					    					 ${ends}:  <fmt:parseDate value="${session.endTime}" pattern="yyyy-MM-dd HH:mm:ss" var="edate"/>
													<fmt:formatDate value="${edate}" pattern="E MM-dd"/>
					    				</c:when>
					    				<c:otherwise>
					    				
					    				</c:otherwise>
				    				</c:choose>
			    				</c:otherwise> --%>
			    			<%-- </c:choose> --%>
<!-- 		    			</p> --><br />
		    			<%--<p class="wrap"> ${time}: --%>
			    			<c:choose>
			    				<c:when test="${not empty session.startTime && session.startTime != 'null'}">
									<fmt:parseDate value="${session.startTime}" pattern="yyyy-MM-dd HH:mm:ss" var="sdate"/>
									<fmt:formatDate value="${sdate}" pattern="h:mm a"/>
					    			<c:if test="${not empty session.endTime && session.endTime != 'null'}">
					    				 - 	<fmt:parseDate value="${session.endTime}" pattern="yyyy-MM-dd HH:mm:ss" var="edate"/>
											<fmt:formatDate value="${edate}" pattern="h:mm a"/>
					    			</c:if>
			    				</c:when>
			    				<c:otherwise>
			    					<c:choose>
					    				<c:when test="${not empty session.endTime && session.endTime != 'null'}">
					    					 ${ends}:  <fmt:parseDate value="${session.endTime}" pattern="yyyy-MM-dd HH:mm:ss" var="edate"/>
													<fmt:formatDate value="${edate}" pattern="h:mm a"/>
					    				</c:when>
					    				<c:otherwise>
					    				
					    				</c:otherwise>
				    				</c:choose>
			    				</c:otherwise>
			    			</c:choose>
<!-- 		    			</p> --><br />
<%-- 		    			<p class="wrap"> --%> ${location}:
		    				<c:choose>
			    				<c:when test="${not empty session.location && session.location != 'null'}">
			    					${session.location}
			    				</c:when>
			    				<c:otherwise>
			    					${tbd}
			    				</c:otherwise>
			    			</c:choose>
		    			</p>

		    			<%-- 
		    			<p class="wrap">${trackdescr}: 
		    				<c:choose>
			    				<c:when test="${not empty session.track && session.track != 'null'}">
			    					${session.track}
			    				</c:when>
			    				<c:otherwise>
			    					${tbd}
			    				</c:otherwise>
			    			</c:choose>
		    			</p>

		    			<p class="wrap">Level: 
		    				<c:choose>
			    				<c:when test="${not empty session.level && session.level != 'null'}">
			    					${session.level}
			    				</c:when>
			    				<c:otherwise>
			    					${tbd}
			    				</c:otherwise>
			    			</c:choose>
		    			</p>
		    			
		    			<p class="wrap">Type: 
		    				<c:choose>
			    				<c:when test="${not empty session.type && session.type != 'null'}">
			    					${session.type}
			    				</c:when>
			    				<c:otherwise>
			    					${tbd}
			    				</c:otherwise>
			    			</c:choose>
		    			</p> 
		    			--%>	    			
		    		</a>
		    	</kme:listItem> 
		    </c:forEach>
	    	<kme:listItem cssClass="sessionItemNone">
	    		<h3>${noresults}</h3>
	    	</kme:listItem>
		</kme:listView>
	</kme:content>
</kme:page>
