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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:if test="${fn:contains(header['User-Agent'],'iPhone') || fn:contains(header['User-Agent'],'iPad') || fn:contains(header['User-Agent'],'iPod') || fn:contains(header['User-Agent'],'Macintosh')}">
	<c:set var="platform" value="iOS"/>
</c:if>
<c:if test="${fn:contains(header['User-Agent'],'Android')}">
	<c:set var="platform" value="Android"/>
</c:if>

<c:set var="header" value="${param['header']}"/>
<c:set var="phonegap" value="${cookie['phonegap'].value}"/>

<kme:page title="${title}" id="home" cssFilename="home" backButton="false" homeButton="false" preferencesButton="false" preferencesButtonURL="preferences" platform="${platform}" phonegap="${phonegap}" onBodyLoad="" jqmHeader="${param['header']}" institutionLogo="true" >

<c:forEach items="${tools}" var="homeTool" varStatus="status">
	<div class="iconbox">
  	<a class="iconimage" href="${homeTool.tool.url}" style="background-image: url('${homeTool.tool.iconUrl}');"></a>
  	<div class="icontitle">${homeTool.tool.title}</div>
</div>
</c:forEach>

	<script type="text/javascript">
		function otherDeviceReadyFunctions(){
			window.plugins.badge.set(${bCount});	
		}
	</script>
	
 </kme:page>


