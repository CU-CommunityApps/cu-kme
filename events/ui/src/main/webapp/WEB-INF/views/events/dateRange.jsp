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

<kme:page title="Events By Range" id="events" appcacheFilename="kme.appcache" backButton="true" homeButton="true" cssFilename="events" backButtonURL="${pageContext.request.contextPath}/home">
    <head>
        <meta http-equiv="Content-type" name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no, width=device-width"> 
        
        <link type="text/css" href="http://dev.jtsage.com/cdn/datebox/latest/jqm-datebox.min.css" rel="stylesheet" /> 
        <script type="text/javascript" src="http://dev.jtsage.com/cdn/datebox/latest/jqm-datebox.core.min.js"></script>
        <script type="text/javascript" src="http://dev.jtsage.com/cdn/datebox/latest/jqm-datebox.mode.calbox.min.js"></script>
        <script type="text/javascript" src="http://dev.jtsage.com/gpretty/prettify.js"></script>
        
    </head>

    <kme:content>
        <kme:listView id="eventslist" dataTheme="c" dataDividerTheme="b" filter="false">
        <kme:listItem dataRole="list-divider">
                    <c:out value="Select the Date Range" />
        </kme:listItem>
        <div id="dateHeading">
                    <h3>
                     <!--                             From Date                               -->    
                        From: 
                    </h3>
                    <input name="todate" id="todate" var="todate" value="" type="text" data-role="datebox" data-options='{"mode":"calbox", "useNewStyle":true}' />    
                    <h3>
                     <!--                             From Date                               -->    
                        To:    
                    </h3> 
                    <input name="dynstart" id="dynstart" type="date" data-role="datebox" id="dynstart" data-options='{"mode":"calbox", "useNewStyle":true}' onclick="pageinit();" />		
                        <script type="text/javascript">
                                $('#dynstart').bind('click', function(event) {
                                        var defaultPickerValue = startDate().split("-", 10);
                                        var defaultPickerValue = [defaultPickerValue[0], defaultPickerValue[1]-1, defaultPickerValue[2]];                        
                                        var presetDate = new Date(defaultPickerValue[0], defaultPickerValue[1], defaultPickerValue[2], 0, 0, 0, 0);
                                        var todaysDate = new Date();
                                        var lengthOfDay = 24 * 60 * 60 * 1000;
                                        var diff = parseInt((((presetDate.getTime() - todaysDate.getTime()) / lengthOfDay))*-1,10);
                                        $('#dynstart').data('datebox').options.defaultValue = defaultPickerValue;
                                        $('#dynstart').data('datebox').options.minDays = diff;
                                })
                                function startDate()
                                {
                                    return $('input#todate').val();
                                };
                                function url()
                                {
                                    document.location.href = '${pageContext.request.contextPath}/events/viewEventsByDateFromTo?dateFrom='+$('input#todate').val()+'&dateTo='+$('input#dynstart').val(); 
                                }
                        </script>
        </div>
        <c:url var="uffrl" value="/events/viewEventsByDateFromTo">
            <c:param name="dateFrom" value="${todate.value}"></c:param>
            <c:param name="dateTo" value="${dynstart.value}"></c:param>
            
        </c:url>    
            <a onclick="url()" data-role="button">Submit</a>                                     
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
