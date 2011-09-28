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

<kme:page title="Personalize KME Styles" id="stylize" backButton="true" homeButton="true" jsFilename="stylize">
	<kme:content>
		<style type="text/css">
			input.ui-input-text {
			    width: 100%;
			    margin-bottom:15px;
			}
		</style>
		<form action = "${pageContext.request.contextPath}/stylize" method="post">
			<label for="pageBackgroundColor">Page Background Color: </label><br /><input type="text" name="pageBackgroundColor" value="${styles['page.background.color']}" /><br />
			
			<label for="headerLogoUrl">Header Logo URL: </label><br /><input type="text" name="headerLogoUrl" value="${styles['header.logo.url']}" /><br />
			<label for="headerTextColor">Header Text Color: </label><br /><input type="text" name="headerTextColor" value="${styles['header.text.color']}" /><br />
			<label for="headerTextShadow">Header Text Shadow: </label><br /><input type="text" name="headerTextShadow" value="${styles['header.text.shadow']}" /><br />
			<label for="headerGradientStartColor">Header Gradient Start Color: </label><br /><input type="text" name="headerGradientStartColor" value="${styles['header.gradient.start.color']}" /><br />
			<label for="headerGradientStopColor">Header Gradient Stop Color: </label><br /><input type="text" name="headerGradientStopColor" value="${styles['header.gradient.stop.color']}" /><br />
			
			<label for="buttonATextColor">Button A Text Color: </label><br /><input type="text" name="buttonATextColor" value="${styles['buttonA.text.color']}" /><br />
			<label for="buttonAGradientStartColor">Button A Gradient Start Color: </label><br /><input type="text" name="buttonAGradientStartColor" value="${styles['buttonA.gradient.start.color']}" /><br />
			<label for="buttonAGradientStopColor">Button A Gradient Stop Color: </label><br /><input type="text" name="buttonAGradientStopColor" value="${styles['buttonA.gradient.stop.color']}" /><br />
			<label for="buttonAHoverGradientStartColor">Button A Hover Gradient Start Color: </label><br /><input type="text" name="buttonAHoverGradientStartColor" value="${styles['buttonA.hover.gradient.start.color']}" /><br />
			<label for="buttonAHoverGradientStopColor">Button A Hover Gradient Stop Color: </label><br /><input type="text" name="buttonAHoverGradientStopColor" value="${styles['buttonA.hover.gradient.stop.color']}" /><br />
			<label for="buttonADownGradientStartColor">Button A Down Gradient Start Color: </label><br /><input type="text" name="buttonADownGradientStartColor" value="${styles['buttonA.down.gradient.start.color']}" /><br />
			<label for="buttonADownGradientStopColor">Button A Down Gradient Stop Color: </label><br /><input type="text" name="buttonADownGradientStopColor" value="${styles['buttonA.down.gradient.stop.color']}" /><br />
			
			<label for="buttonCTextColor">Button C Text Color: </label><br /><input type="text" name="buttonCTextColor" value="${styles['buttonC.text.color']}" /><br />
			<label for="buttonCGradientStartColor">Button C Gradient Start Color: </label><br /><input type="text" name="buttonCGradientStartColor" value="${styles['buttonC.gradient.start.color']}" /><br />
			<label for="buttonCGradientStopColor">Button C Gradient Stop Color: </label><br /><input type="text" name="buttonCGradientStopColor" value="${styles['buttonC.gradient.stop.color']}" /><br />
			<label for="buttonCHoverGradientStartColor">Button C Hover Gradient Start Color: </label><br /><input type="text" name="buttonCHoverGradientStartColor" value="${styles['buttonC.hover.gradient.start.color']}" /><br />
			<label for="buttonCHoverGradientStopColor">Button C Hover Gradient Stop Color: </label><br /><input type="text" name="buttonCHoverGradientStopColor" value="${styles['buttonC.hover.gradient.stop.color']}" /><br />
			<label for="buttonCDownGradientStartColor">Button C Down Gradient Start Color: </label><br /><input type="text" name="buttonCDownGradientStartColor" value="${styles['buttonC.down.gradient.start.color']}" /><br />
			<label for="buttonCDownGradientStopColor">Button C Down Gradient Stop Color: </label><br /><input type="text" name="buttonCDownGradientStopColor" value="${styles['buttonC.down.gradient.stop.color']}" /><br />
               
			<div data-inline="true">
                <div class="ui-grid-b">
                    <div class="ui-block-a">
                    	<a data-theme="c"  href="${pageContext.request.contextPath}/home" data-role="button">Cancel</a>
                    </div>
                    <div class="ui-block-b">
                    	<a data-theme="c"  href="#" data-role="button" id="clearButton">Clear Changes</a>
                    </div>
                    <div class="ui-block-c">
                    	<input data-theme="a" class="submit" type="submit" id="saveButton" value="Apply" />
                    </div>
                </div>
            </div>
		</form>
	</kme:content>
</kme:page>


