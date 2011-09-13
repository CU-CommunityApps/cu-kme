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

<kme:page title="polling" id="polling-webapp" backButton="true" homeButton="true" cssFilename="polling" jsFilename="polling">
	<kme:content>
		<p>${stats.poll.question}</p>
		<p>Total votes: ${stats.totalVotes}</p>
			<table>
				<c:forEach items="${stats.answerStats}" var="answerStat" varStatus="status">
					<tr>
						<td>${answerStat.key}</td>
						<td>
							${answerStat.value}
						</td>
					</tr>
				</c:forEach>
			</table>
	</kme:content>
</kme:page>
