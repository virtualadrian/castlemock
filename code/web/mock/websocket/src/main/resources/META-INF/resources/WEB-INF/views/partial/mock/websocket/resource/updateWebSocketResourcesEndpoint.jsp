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

<%@ include file="../../../../includes.jspf"%>
<c:url var="update_resources_endpoint_url"  value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopicId}/resource/update/confirm" />
<div class="content-top">
    <h1><spring:message code="websocket.updatewebSocketresourcesendpoint.header.updateresource"/></h1>
</div>
<c:choose>
    <c:when test="${webSocketResources.size() > 0}">
        <p><spring:message code="websocket.updatewebSocketresourcesendpoint.label.confirmation"/></p>
        <form:form action="${update_resources_endpoint_url}" method="POST" commandName="updateWebSocketResourcesEndpointCommand">
            <ul>
                <c:forEach items="${webSocketResources}" var="webSocketResource" varStatus="loopStatus">
                    <li>${webSocketResource.name}</li>
                    <form:hidden path="webSocketResources[${loopStatus.index}].id" value="${webSocketResource.id}"/>
                </c:forEach>
            </ul>
            <table class="formTable">
                <tr>
                    <td class="column1"><label path="forwardedEndpoint"><spring:message code="websocket.updatewebSocketresourcesendpoint.label.forwardedendpoint"/></label></td>
                    <td class="column2"><form:input path="forwardedEndpoint" value="${updateWebSocketResourcesEndpointCommand.forwardedEndpoint}"/></td>
                </tr>
            </table>
            <button class="button-success pure-button"><i class="fa fa-check-circle"></i> <span><spring:message code="websocket.updatewebSocketresourcesendpoint.button.updateresources"/></span></button>
            <a href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopicId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="websocket.updatewebSocketresourcesendpoint.button.cancel"/></a>
        </form:form>
    </c:when>
    <c:otherwise>
        <p><spring:message code="websocket.updatewebSocketresourcesendpoint.label.noresources"/></p>
        <a href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopicId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="websocket.updatewebSocketresourcesendpoint.button.cancel"/></a>
    </c:otherwise>
</c:choose>