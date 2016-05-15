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

<c:url var="websocketstomp_method_update_url"  value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplicationId}/resource/${webSocketStompResource.id}" />
<div class="content-top">
    <div class="title">
        <h1><spring:message code="websocket.stomp.websocketstompresource.header.resource" arguments="${webSocketStompResource.name}"/></h1>
    </div>
    <div class="menu" align="right">
        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
            <a class="button-success pure-button" href="<c:url value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplicationId}/resource/${webSocketStompResource.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="websocket.stomp.websocketstompresource.button.updateresource"/></span></a>
            <a class="button-secondary pure-button" href="<c:url value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplicationId}/resource/${webSocketStompResource.id}/create/method"/>"><i class="fa fa-plus"></i> <span><spring:message code="websocket.stomp.websocketstompresource.button.createmethod"/></span></a>
            <a class="button-error pure-button" href="<c:url value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplicationId}/resource/${webSocketStompResource.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="websocket.stomp.websocketstompresource.button.delete"/></span></a>
        </sec:authorize>
    </div>
</div>
<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="websocket.stomp.websocketstompresource.label.name"/></label></td>
            <td class="column2"><label path="name">${webSocketStompResource.name}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="websocket.stomp.websocketstompresource.label.uri"/></label></td>
            <td class="column2"><label path="name">${webSocketStompResource.uri}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="websocket.stomp.websocketstompresource.label.address"/></label></td>
            <td class="column2"><label path="name">${webSocketStompResource.invokeAddress}</label></td>
        </tr>
    </table>
</div>