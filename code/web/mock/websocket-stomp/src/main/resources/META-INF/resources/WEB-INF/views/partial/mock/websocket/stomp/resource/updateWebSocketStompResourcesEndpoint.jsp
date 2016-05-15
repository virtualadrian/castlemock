<%--
 Copyright 2016 Karl Dahlgren

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>

<%@ include file="../../../../../includes.jspf"%>
<c:url var="update_resources_endpoint_url"  value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplicationId}/resource/update/confirm" />
<div class="content-top">
    <h1><spring:message code="websocket.stomp.updatewebsocketstompresourcesendpoint.header.updateresource"/></h1>
</div>
<c:choose>
    <c:when test="${webSocketStompResources.size() > 0}">
        <p><spring:message code="websocket.stomp.updatewebsocketstompresourcesendpoint.label.confirmation"/></p>
        <form:form action="${update_resources_endpoint_url}" method="POST" commandName="updateWebSocketStompResourcesEndpointCommand">
            <ul>
                <c:forEach items="${webSocketStompResources}" var="webSocketStompResource" varStatus="loopStatus">
                    <li>${webSocketStompResource.name}</li>
                    <form:hidden path="webSocketStompResources[${loopStatus.index}].id" value="${webSocketStompResource.id}"/>
                </c:forEach>
            </ul>
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="forwardedEndpoint"><spring:message code="websocket.stomp.updatewebsocketstompresourcesendpoint.label.forwardedendpoint"/></label></td>
                    <td class="column2"><form:input path="forwardedEndpoint" value="${updateWebSocketStompResourcesEndpointCommand.forwardedEndpoint}"/></td>
                </tr>
            </table>
            <button class="button-success pure-button"><i class="fa fa-check-circle"></i> <span><spring:message code="websocket.stomp.updatewebsocketstompresourcesendpoint.button.updateresources"/></span></button>
            <a href="<c:url value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplicationId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="websocket.stomp.updatewebsocketstompresourcesendpoint.button.cancel"/></a>
        </form:form>
    </c:when>
    <c:otherwise>
        <p><spring:message code="websocket.stomp.updatewebsocketstompresourcesendpoint.label.noresources"/></p>
        <a href="<c:url value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplicationId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="websocket.stomp.updatewebsocketstompresourcesendpoint.button.cancel"/></a>
    </c:otherwise>
</c:choose>