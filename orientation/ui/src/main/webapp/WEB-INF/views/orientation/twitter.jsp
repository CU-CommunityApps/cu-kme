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

<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<kme:page title="#cornell2016 #cuorientation" id="orientation" backButton="true" homeButton="true" preferencesButton="false" jsFilename="orientation" cssFilename="orientation">
	<kme:content>
		<!-- <div id="localTweets"><p>Internet connection error - displaying previously loaded tweets.</p></div> -->
		<ul data-role="listview" data-inset="false" data-filter="false" class="ui-listview twitStream 100" id="tweets" title="cornell2016%20OR%20%23cuorientation">
		</ul>
		<div id="loadTweets"><p></p></div>
	</kme:content>
	<script type="text/javascript">
		//code used to not cache in FF so this page will run its javascript on each visit.
		window.addEventListener('unload', UnloadHandler, false);
		function UnloadHandler() {
			//do nothing
		}

		$(window).ready(function(){
			initialize();
		});
		$(window).scroll(function () {
			checkPosition();
		});
	</script>
</kme:page>
