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

<kme:page title="Bus Schedules" id="bus" backButton="true" homeButton="true" cssFilename="bus">
	<kme:content>
		<kme:listView>
			<kme:listItem>
				<a class="icon-doublemap" href="bus/doublemap">
					<h3 class="wrap">DoubleMap Live Bus Tracking</h3>
					<p class="wrap">Find IU Bloomington campus buses on the map in real time.</p>
				</a>
			</kme:listItem>
			<kme:listItem>
				<a class="icon-iubcampusbus" href="bus/iubcampusbus">
					<h3 class="wrap">IUB Campus Bus Schedules</h3>
					<p class="wrap">Stop times and locations for Bloomington campus buses.</p>
				</a>
			</kme:listItem>
			<kme:listItem>
				<a class="icon-bloomingtontransit" href="bus/bloomingtontransit">
					<h3 class="wrap">Bloomington Transit Bus Schedules</h3>
					<p class="wrap">Stop times and locations for Bloomington city buses.</p>
				</a>
			</kme:listItem>
		</kme:listView>
	</kme:content>
</kme:page>