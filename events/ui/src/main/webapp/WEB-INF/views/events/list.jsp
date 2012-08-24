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
    <head>
        <meta http-equiv="Content-type" name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no, width=device-width"> 
        
        <link type="text/css" href="http://dev.jtsage.com/cdn/datebox/latest/jqm-datebox.min.css" rel="stylesheet" /> 
        <script type="text/javascript" src="http://dev.jtsage.com/cdn/datebox/latest/jqm-datebox.core.min.js"></script>
        <script type="text/javascript" src="http://dev.jtsage.com/cdn/datebox/latest/jqm-datebox.mode.calbox.min.js"></script>
        <script type="text/javascript" src="http://dev.jtsage.com/gpretty/prettify.js"></script>

        <script type="text/javascript">
            function updateList()
            {                               
                document.location.href = '${pageContext.request.contextPath}/events/viewEventsDateSpecific?dateSpecific='+$('input#mode1').val();               
            }
            
            function previousDate()
            {
                document.location.href = '${pageContext.request.contextPath}/events/previousDate?currentDate='+$('input#mode1').val(); 
            }
            function nextDate()
            {
                document.location.href = '${pageContext.request.contextPath}/events/nextDate?currentDate='+$('input#mode1').val(); 
            }
       </script>
    </head>

    <kme:content>
        <kme:listView id="eventslist" dataTheme="c" dataDividerTheme="b" filter="false">

            <div id="dateNavigation">
                <table width="100%">
                    <tr>
                        <td>
                            <a href="" onclick="previousDate()" id="leftArrow">
                                Previous Day
                            </a>                            
                        </td>
                        <td>                            
                            <h3><input name="mode1" id="mode1"  value="${todayDate}" type="text" data-role="datebox" data-options='{"mode":"calbox", "useNewStyle":true}' onchange="updateList()" /></h3>
                        </td>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
                        <td>
                            <a href="" onclick="nextDate()" id="rightArrow">
                                Next Day
                            </a>
                        </td>

                    </tr>
                </table>  
            </div>
                       
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
                            <h3 class="wrap"><c:out value="${gbcV.title}" /></h3>
                            <p style="white-space:pre-wrap"><c:out value="${gbcV.displayStartTime}" /> - <c:out value="${gbcV.displayEndTime}" /> | <c:out value="${gbcV.location}" /></p>   
                        </a>                                        
                    </kme:listItem>
                </c:forEach>
            </c:forEach>
                        
        </kme:listView>
                        
        <div data-role="navbar" data-position="fixed">
            <ul>
                     <li><a href="${pageContext.request.contextPath}/events/byCategory">By Category</a></li>
                     <li><a href="${pageContext.request.contextPath}/events">By Date</a></li>
                    <li><a href="${pageContext.request.contextPath}/events/byDateRange">By Range</a></li>
            </ul>
       </div><!-- /navbar -->
    </kme:content>    
</kme:page>

                        

<!--Below is the code for viewing the events by category -->
<!--<kme:page title="Events By Day" id="events" appcacheFilename="kme.appcache" backButton="true" homeButton="true" cssFilename="events" backButtonURL="${pageContext.request.contextPath}/home">
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
     <div>
         <input type="date" name="date" />
     </div>
 </div>
			<c:forEach items="${categories}" var="category" varStatus="status">
				<kme:listItem>
					<c:url var="url" value="/events/viewEventsDateSpecific">
						<c:param name="categoryId" value="${category.categoryId}"></c:param>
						<c:param name="campus" value="${campus}"></c:param>
                                                <c:param name="dateSpecific" value="${todayDate}"></c:param>
					</c:url>
					<a href="${url}">
						<h3>
							<c:out value="${category.title}" />
						</h3> </a>
				</kme:listItem>
			</c:forEach>
		</kme:listView>
	</kme:content>
</kme:page>-->
