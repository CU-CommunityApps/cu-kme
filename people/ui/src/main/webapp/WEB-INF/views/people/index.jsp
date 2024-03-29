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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="kme" uri="http://kuali.org/mobility" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<kme:page title="People" id="directory" backButton="true" homeButton="true" cssFilename="directory">
  <kme:content>
    <form:form action="${pageContext.request.contextPath}/people" method="post" commandName="search" data-ajax="false">
      <fieldset>
        <label for="searchText" style="position:absolute; left:-9999px;">Search Usage: Keyword | Last, First | First Last</label>
        <form:input path="searchText" cssClass="text ui-widget-content ui-corner-all" placeholder="${watermark}" />
        <form:errors path="searchText" />
      </fieldset>
    </form:form>
    <div id="searchresults">
      <kme:listView id="peopleList" filter="false" dataTheme="c" dataInset="false">
        <%-- JQM Stuff --%> 
       <script type="text/javascript">
         $('[data-role=page][id=directory]').live('pagebeforeshow', 
           function(event, ui) 
             {
			 $('#peopleTemplate').template('peopleTemplate');
               refreshTemplate('people', '#peopleList', 'peopleTemplate', '', 
                 function() 
                   {
                   $('#peopleList').listview('refresh');
                   }
                );
             }
           );
       </script>
       <script id="peopleTemplate" type="text/x-jquery-tmpl">
         {{if heading}}
           <li data-role="list-divider" data-theme="b" data-icon="listview" >\${heading}</li>
         {{/if}}
         {{if error}}
           <li>\${error}</li>
         {{/if}}
         {{if directoryEntries}}
           {{each directoryEntries}}
             <li>
               <a href="${pageContext.request.contextPath}/people/\${hashedUserName}">
                 {{if lastName}}
                   <h3>\${firstName} \${middleName} \${lastName}</h3>
                 {{else}}
                   <h3>\${displayName}</h3>
                 {{/if}}
                 {{if nickName}}
                   <p><strong>Nickname: \${nickName}</strong>
                 {{/if}}
                 {{if locations && 0 < locations.length}}
                   <p><strong>Location:</strong>
                   {{each(i,location) locations}}
                      \${location}{{if i+1 < locations.length}}, {{/if}}
                   {{/each}}
                   </p>
                 {{/if}}
                 <p><strong>Affiliation:</strong>
                   {{each(i,affiliation) affiliations}}
                     \${affiliation}
                     {{if i+1 < affiliations.length}}, {{/if}}
                   {{/each}}
                 </p>				    		  	
               </a>
             </li>
           {{/each}}
         {{/if}}
         {{if directoryEntriesgroup}}
           {{each directoryEntriesgroup}}
             <li>
               <a href="${pageContext.request.contextPath}/people/group/\${hashedDN}">
                 {{if displayName}}
                   <h3>\${displayName}</h3>
                 {{/if}}
                 {{if descriptions}}
                    <p><strong>Description:</strong>
                    {{each(i,description) descriptions}}
                      \${description}{{if i+1 < descriptions.length}}, {{/if}}
                    {{/each}}
                    </p>	
                 {{/if}}
               </a>	
             </li>
           {{/each}}
           {{/if}}
         </script>					
       </kme:listView>
     </div>
  </kme:content>
</kme:page>
