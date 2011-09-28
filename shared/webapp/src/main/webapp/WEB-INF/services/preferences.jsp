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

<kme:page title="Preferences" id="preferences" backButton="true" homeButton="true">
	<kme:content>
		<kme:listView id="campuslist" dataTheme="c" dataDividerTheme="b">
			<kme:listItem dataRole="list-divider" dataTheme="b">
				Select Your Campus
			</kme:listItem>
			<c:forEach items="${campuses}" var="campus" varStatus="status">
				<kme:listItem>
					<c:url var="campusUrl" value="/campus/select">
						<c:param name="toolName" value="${toolName}" />
						<c:param name="campus" value="${campus.code}" />
					</c:url>
					<a href="${campusUrl}"> <c:out value="${campus.name}" />
					</a>
				</kme:listItem>
			</c:forEach>
			<kme:listItem dataRole="list-divider" dataTheme="b">
				Demo Tools
			</kme:listItem>
			<kme:listItem >
				<a href="${pageContext.request.contextPath}/stylize">
					Personalize KME Styles
				</a>
			</kme:listItem>
		</kme:listView>
	</kme:content>
</kme:page>


