<%--
  Copyright 2011-2012 The Kuali Foundation Licensed under the Educational Community
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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="weather.title" var="title"/>

<kme:page title="${title}" id="weather" backButton="true" homeButton="true" cssFilename="weather">
	<kme:content>
		<kme:listView id="currentConditionsList" dataTheme="c" dataDividerTheme="b" filter="false">
			<kme:listItem dataTheme="b" dataRole="list-divider"><spring:message code="weather.forecastPage.label.currentConditions" /></kme:listItem>
			<kme:listItem>
				<h3 class="wrap">${forecast.currentCondition}</h3>
			</kme:listItem>
			<kme:listItem>
				<fmt:formatNumber var="celcius" value="${((forecast.temperature - 32) * 5 / 9)}" maxFractionDigits="0" />
				<h3 class="wrap"><spring:message code="weather.forecastPage.label.currentTemperature" />: ${forecast.temperature}&deg;F / ${celcius}&deg;C</h3>
			</kme:listItem>
			<kme:listItem>
				<h3 class="wrap"><spring:message code="weather.forecastPage.label.humidity" />: ${forecast.humidity}%</h3>
			</kme:listItem>
			<kme:listItem>
				<h3 class="wrap"><spring:message code="weather.forecastPage.label.wind" />: ${forecast.wind} </h3>
			</kme:listItem>
			<kme:listItem>
				<h3 class="wrap"><spring:message code="weather.forecastPage.label.pressure" />: ${forecast.pressure} in. Hg</h3>
			</kme:listItem>
		
			<kme:listItem dataTheme="b" dataRole="list-divider"><spring:message code="weather.forecastPage.label.forecast" /></kme:listItem>
			<c:forEach items="${forecast.forecasts}" var="item" varStatus="status">
				<kme:listItem>
					<div class="forecastItem" style="background-image:url('${item.iconLink}')">
						<h3>${item.name}</h3>
						<p class="wrap">${item.text}</p>
					</div>
				</kme:listItem>
			</c:forEach>

			<kme:listItem dataTheme="b" dataRole="list-divider"><spring:message code="weather.forecastPage.label.alerts" /></kme:listItem>
            <kme:listItem hideDataIcon="true">
            	<a href="${alertsTool.url}" style="background-image: url('${alertsTool.iconUrl}');">
		      		<h3>${alertsTool.title}</h3>
		      		<p class="wrap">${alertsTool.description}</p>
		      		<c:if test="${not empty alertsTool.badgeCount}"> 
		      			<span class="countBadge ui-btn-up-c ui-btn-corner-all">${alertsTool.badgeCount}</span>
		      		</c:if>
		      	</a>
            </kme:listItem>
		</kme:listView>
	</kme:content>
</kme:page>
