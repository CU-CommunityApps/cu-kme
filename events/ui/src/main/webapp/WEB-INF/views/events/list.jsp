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

<kme:page title="Events By Day" id="events" appcacheFilename="kme.appcache" backButton="true" homeButton="true" cssFilename="events" backButtonURL="${pageContext.request.contextPath}/home">
	<kme:content>
		<kme:listView id="eventslist" dataTheme="c" dataDividerTheme="b" filter="false">
<%-- 			<kme:listItem>
				<c:url var="calendarUrl" value="/calendar">
				</c:url>
				<a href="${calendarUrl}">
					<h3>
						My Calendar
					</h3> </a>
			</kme:listItem>
 --%>
 
 <div id="dateNavigation">
     <a href="index.html" id="leftArrow">Previous Day</a>
     <a href="index.html" id="rightArrow">Next Day</a>
     <h3>${todayDate}</h3>
<!--     <div>
         <input type="date" name="date" />
     </div>-->
 </div>

<!--                        <c:forEach items="${eventListController}" var="elc" varStatus="status">  
                            <div id="categoryHeading">
                                <h3><c:out value="${elc.type}" /></h3> 
                            </div>
                            <div id="eventName">
                                <h3><c:out value="${elc.title}" /></h3>
                            </div>
                            <div id ="eventSchedule">
                                <h3><c:out value="${elc.displayStartTime}" /> - <c:out value="${elc.displayEndTime}" /> | <c:out value="${elc.location}" /> </h3>
                            </div>
                                                              
                        </c:forEach>-->

                        <c:forEach items="${groupByCat}" var ="gbc" varStatus="status">
<!--                             Category Header                               -->
                              <kme:listItem dataRole="list-divider">
                                    <c:out value="${gbc.key.title}" />
                              </kme:listItem>                         
                            
                              <c:forEach items="${gbc.value}" var ="gbcV" varStatus="status">
<!--                                     Event Information                              -->
                                    <kme:listItem>
                                        <c:url var="url" value="/events/viewEvent">
						<c:param name="categoryId" value="${gbc.key.categoryId}"></c:param>
						<c:param name="campus" value="${campus}"></c:param>
                                                <c:param name="eventId" value="${gbcV.eventId}"></c:param>
					</c:url>
                                        <a href="${url}">
                                            <h3><c:out value="${gbcV.title}" /></h3>
                                            <p><c:out value="${gbcV.displayStartTime}" /> - <c:out value="${gbcV.displayEndTime}" /> | <c:out value="${gbcV.location}" /></p>   
                                        </a>                                        
                                    </kme:listItem>
                                    
                               </c:forEach>
                            
                        </c:forEach>
                        
                            
<!--			<c:forEach items="${categories}" var="category" varStatus="status">
				<kme:listItem>
					<c:url var="url" value="/events/viewEvents">
						<c:param name="categoryId" value="${category.categoryId}"></c:param>
						<c:param name="campus" value="${campus}"></c:param>
                                                <c:param name="dateSpecific" value="${todayDate}"></c:param>
					</c:url>
					<a href="${url}">
						<h3>
							<c:out value="${category.title}" />
						</h3> </a>
				</kme:listItem>
			</c:forEach>-->
		</kme:listView>
	</kme:content>
</kme:page>
