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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<spring:message code="sessiondetails.title" var="title"/>
<spring:message code="sessiondetails.about" var="about"/>
<spring:message code="sessiondetails.rate" var="rate"/>
<spring:message code="label.time" var="time"/>
<spring:message code="label.date" var="date"/>
<spring:message code="label.location" var="location"/>
<spring:message code="label.comments" var="comments"/>
<spring:message code="label.submit" var="submit"/>
<spring:message code="label.fullsiteurl" var="siteurl"/>
<spring:message code="speakers.title" var="speakers"/>

<kme:page title="${title}" id="sessiondetails" homeButton="true" backButton="true" cssFilename="orientation">
	<kme:content>
	    <kme:listView id="sessionList" filter="false" dataTheme="c" dataInset="false" cssClass="sessionDetails">
			<kme:listItem dataRole="list-divider">
	        	${session.title}
	        </kme:listItem>
   		
			<kme:listItem>
				<c:if test="${fn:length(session.flags) != 0}">
					<div class="sessionIcons wrap">
					<p class="wrap sessionIcons">
	 					<c:forEach items="${fn:split(session.flags,'|')}" var="flag">
							<div class="sessionIcon${flag} sessionIcon"></div>
	 					</c:forEach>
					</p>
					</div>
				</c:if>

 				<c:choose>
	  				<c:when test="${not empty session.track && session.track == 'UNIV'}">
		 				<p class="wrap"><em>University-wide</em></p>
		   			</c:when>
  				</c:choose>

    			<p class="wrap"><%--${date}: --%>
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
    			</p>
    			
     			<p class="wrap"><%-- ${time}: --%>
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
    			</p>

       		<p class="wrap">${location}: <strong>${session.location}</strong></p>

        	<c:if test="${not empty session.description}" >
<%--         	<h3>${about}:</h3> --%>
       			<p class="wrap tightPadding">${session.description}</p>
        	</c:if>

       		</kme:listItem>

        	<c:if test="${not empty session.latitude}" >
	        	<kme:listItem dataRole="list-divider">Map</kme:listItem>   
	        	<kme:listItem cssClass="link-gps">
	        	
<%--
	        		<a href="http://maps.google.com/maps?q=${session.latitude},${session.longitude}&z=18">${session.location}</a>
--%>
<%--
	        	<a href="${pageContext.request.contextPath}/maps/location?latitude=${session.latitude}&longitude=${session.longitude}">${session.location}</a>
--%>
	        	<a href="${pageContext.request.contextPath}/maps?lat=${session.latitude}&lng=${session.longitude}">${session.location}</a>

	        	</kme:listItem>
        	</c:if>

<%--
	        
	        <c:if test="${!empty session.speakers}">
		        <kme:listItem dataRole="list-divider">${speakers}</kme:listItem>   
		        <c:forEach items="${session.speakers}" var="speaker" varStatus="status">
			    	<kme:listItem>
		    			<h3 class="wrap">
		    				${speaker.firstName} ${speaker.lastName}
		    			</h3>
		    			<p class="wrap">${speaker.email}</p>
		    			<p class="wrap">${speaker.title}</p>
		    			<p class="wrap">${speaker.institution}</p>
			    	</kme:listItem>            
			    </c:forEach>
		    </c:if>
	        
	        <kme:listItem dataRole="list-divider">${rate}:</kme:listItem>
	        <kme:listItem>

				<form:form action="${pageContext.request.contextPath}/orientation/sessionFeedback" commandName="sessionFeedback" data-ajax="false" method="post">
					<div>
						<div class="ratingStar" id="star1">1</div>
		        		<div class="ratingStar" id="star2">2</div>
		        		<div class="ratingStar" id="star3">3</div>
		        		<div class="ratingStar" id="star4">4</div>
		        		<div class="ratingStar" id="star5">5</div>
		            </div>
		            
		            <input type="hidden" id="rating" name="rating"/>
		            <input type="hidden" id="sessionId" name="sessionId" value="${session.id}"/>
		            <input type="hidden" id="sessionName" name="sessionName" value="${session.title}"/>
		            
		           
		            
		            <div style="clear:left;">
		            	<fieldset>
		                    <label for="comments">${comments}:</label>
		                    <form:textarea path="comments" cols="40" rows="8" />
		            	</fieldset>
		            </div>
		            
		            <div data-inline="true">
		                <div class="ui-grid-a">
		                    <input data-theme="a" class="submit" type="submit" value="${submit}" />
		                </div>
		            </div>
		        </form:form>
	
        	</kme:listItem>
--%>
	        
	        <c:if test="${not empty session.link}" >
	        	<kme:listItem dataRole="list-divider">${siteurl}</kme:listItem>   
	        	<kme:listItem>
	        		<a href="${session.link}"><p>${session.link}</p></a>
	        	</kme:listItem>
	        </c:if>
		</kme:listView>
		
<%--
		<script>
		$(function() {
			$('div.ratingStar').click(
				function () {
					clickedRating = parseInt($(this).html());
					if (1 <= clickedRating) { $("div#star1").addClass("starOn"); } else { $("div#star1").removeClass("starOn"); }
					if (2 <= clickedRating) { $("div#star2").addClass("starOn"); } else { $("div#star2").removeClass("starOn"); }
					if (3 <= clickedRating) { $("div#star3").addClass("starOn"); } else { $("div#star3").removeClass("starOn"); }
					if (4 <= clickedRating) { $("div#star4").addClass("starOn"); } else { $("div#star4").removeClass("starOn"); }
					if (5 <= clickedRating) { $("div#star5").addClass("starOn"); } else { $("div#star5").removeClass("starOn"); }				
					$("input#rating").val($(this).html());
				}
			);
		});
		</script>
--%>
	</kme:content>
</kme:page>