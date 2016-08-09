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

<c:url var="delete_broadcasters_url"  value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopicId}/broadcaster/delete/confirm" />
<div class="content-top">
    <h1><spring:message code="websocket.deletebroadcasters.header.deletebroadcasters"/></h1>
</div>
<c:choose>
    <c:when test="${webSocketBroadcasters.size() > 0}">
        <p><spring:message code="websocket.deletebroadcasters.label.confirmation"/></p>
        <form:form action="${delete_broadcasters_url}" method="POST" commandName="deleteWebSocketBroadcastersCommand">
            <ul>
                <c:forEach items="${webSocketBroadcasters}" var="webSocketBroadcaster" varStatus="loopStatus">
                    <li>${webSocketBroadcaster.name}</li>
                    <form:hidden path="webSocketBroadcasters[${loopStatus.index}].id" value="${webSocketBroadcaster.id}"/>
                </c:forEach>
            </ul>

            <button class="button-error pure-button" type="submit"><i class="fa fa-trash"></i> <span><spring:message code="websocket.deletebroadcasters.button.deletebroadcasters"/></span></button>
            <a href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopicId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="websocket.deletebroadcasters.button.cancel"/></a>
        </form:form>
    </c:when>
    <c:otherwise>
        <spring:message code="websocket.deletebroadcasters.label.nobroadcasters"/>
        <p>
        <a href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopicId}"/>" class="button-secondary pure-button"><i class="fa fa-times"></i> <spring:message code="websocket.deletebroadcasters.button.cancel"/></a>
        </p>
    </c:otherwise>
</c:choose>