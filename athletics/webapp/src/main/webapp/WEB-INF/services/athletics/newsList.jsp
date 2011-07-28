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

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility"%>

<kme:page title="News" id="athletics-news" backButton="true" homeButton="true" cssFilename="athletics" backButtonURL="${pageContext.request.contextPath}/athletics">
	<kme:content>

		<div class="subnav">
			<div class="subnav-container">
				<a href="${pageContext.request.contextPath}/athletics/viewSport?sportId=${sport.sportId}" class="button-subnav left"><span>news</span> </a>
				<c:if test="${sport.seasonId > 0}">
					<a href="${pageContext.request.contextPath}/athletics/viewRoster?sportId=${sport.sportId}&seasonId=${sport.seasonId}" class="button-subnav left"><span>roster</span> </a>
					<a href="${pageContext.request.contextPath}/athletics/viewSchedule?sportId=${sport.sportId}&seasonId=${sport.seasonId}" class="button-subnav left"><span>schedule</span> </a>
				</c:if>
			</div>
		</div>

		<div class="nonfocal">
			<c:out value="${sport.name}" escapeXml="true"></c:out>
		</div>

		<%--
  <ul class="nav-news" id="newsindex">
   <c:forEach items="${page.model.rss.rssItems}" var="item" varStatus="status">
	<li><c:choose>
    	<c:when test="${not empty item.enclosureUrl}"><img src="<c:out value="${item.enclosureUrl}" escapeXml="true"  />" class="rowicon-news" /></c:when>
    	<c:otherwise><img src="ip/images/default-blockiu.png" class="rowicon-news" /></c:otherwise>
    	</c:choose>
    	<a href="Base.do?s=AthleticsService&p=story&sportId=${page.model.team.id}&link=${item.link}">
    	
    	<strong><c:out value="${item.title}" escapeXml="true" /></strong>
    
    				 <div class="teaserline">
			<c:out value="${item.description}" escapeXml="true"/>
			</div>
			<div class="timestamp"><fmt:formatDate value="${item.publishDate}" pattern="EEE, dd MMM yyyy"/></div>
			</a><div style="clear:both"></div>
      	
	</li>
   </c:forEach>
</ul>
 --%>
	</kme:content>
</kme:page>