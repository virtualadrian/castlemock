<%@ include file="../../../../includes.jspf"%>

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

<c:url var="webSocket_method_update_url"  value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopicId}/resource/${webSocketResource.id}" />
<div class="content-top">
    <div class="title">
        <h1><spring:message code="websocket.websocketresource.header.resource" arguments="${webSocketResource.name}"/></h1>
    </div>
    <div class="menu" align="right">
        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
            <a class="button-success pure-button" href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopicId}/resource/${webSocketResource.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="websocket.websocketresource.button.updateresource"/></span></a>
            <a class="button-secondary pure-button" href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopicId}/resource/${webSocketResource.id}/create/method"/>"><i class="fa fa-plus"></i> <span><spring:message code="websocket.websocketresource.button.createmessage"/></span></a>
            <a class="button-error pure-button" href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopicId}/resource/${webSocketResource.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="websocket.websocketresource.button.delete"/></span></a>
        </sec:authorize>
    </div>
</div>
<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="websocket.websocketresource.label.name"/></label></td>
            <td class="column2"><label path="name">${webSocketResource.name}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="websocket.websocketresource.label.uri"/></label></td>
            <td class="column2"><label path="name">${webSocketResource.uri}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="name"><spring:message code="websocket.websocketresource.label.address"/></label></td>
            <td class="column2"><label path="name">${webSocketResource.invokeAddress}</label></td>
        </tr>
    </table>
</div>