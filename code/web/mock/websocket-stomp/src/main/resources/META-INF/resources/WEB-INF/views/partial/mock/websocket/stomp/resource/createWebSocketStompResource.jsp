<%@ include file="../../../../../includes.jspf"%>
<%--
  ~ Copyright 2016 Karl Dahlgren
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~   http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software
  ~ distributed under the License is distributed on an "AS IS" BASIS,
  ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~ See the License for the specific language governing permissions and
  ~ limitations under the License.
  --%>

<c:url var="create_websocketstomp_resource_url"  value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplicationId}/create/resource" />
<div class="content-top">
    <h1><spring:message code="websocket.stomp.createresource.header.resource"/></h1>
</div>
<form:form action="${create_websocketstomp_resource_url}" method="POST" commandName="webSocketStompResource">
    <table class="formTable">
        <tr>
            <td class="column1"><label><spring:message code="websocket.stomp.createresource.label.name"/></label></td>
            <td class="column2"><form:input id="websocketstompResourceNameInput" path="name" /></td>
        </tr>
        <tr>
            <td class="column1"><label><spring:message code="websocket.stomp.createresource.label.uri"/></label></td>
            <td class="column2"><form:input id="websocketstompResourceUriInput" path="uri" /></td>
            <td class="information" title="<spring:message code="websocket.stomp.createresource.tooltip.uri"/>"><i class="fa fa-question-circle fa-1x"></i></td>
        </tr>
    </table>
 
    <button class="button-success pure-button" type="submit" name="submit"><i class="fa fa-plus"></i> <span><spring:message code="websocket.stomp.createresource.button.createresource"/></span></button>
    <a href="<c:url value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplicationId}"/>" class="button-error pure-button"><i class="fa fa-times"></i> <span><spring:message code="websocket.stomp.createresource.button.cancel"/></span></a>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form:form>
<script>
    $("#websocketstompResourceNameInput").attr('required', '');
    $("#websocketstompResourceUriInput").attr('required', '');
</script>
