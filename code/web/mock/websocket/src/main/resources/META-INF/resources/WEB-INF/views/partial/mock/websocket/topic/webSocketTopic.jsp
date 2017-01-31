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

<c:url var="webSocket_resource_update_url"  value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopic.id}" />
<div class="content-top">
    <div class="title">
        <h1><spring:message code="websocket.websockettopic.header.topic" arguments="${webSocketTopic.name}"/></h1>
    </div>
    <div class="menu" align="right">
        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
            <a class="button-success pure-button" href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopic.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="websocket.websockettopic.button.updatetopic"/></span></a>
            <a class="button-secondary pure-button" href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopic.id}/create/broadcaster"/>"><i class="fa fa-plus"></i> <span><spring:message code="websocket.websockettopic.button.createbroadcaster"/></span></a>
            <a class="button-secondary pure-button" href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopic.id}/create/resource"/>"><i class="fa fa-plus"></i> <span><spring:message code="websocket.websockettopic.button.createresource"/></span></a>
          <a class="button-error pure-button" href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopic.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="websocket.websockettopic.button.delete"/></span></a>
        </sec:authorize>
    </div>
</div>
<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="websocket.websockettopic.label.name"/></label></td>
            <td class="column2"><label path="name">${webSocketTopic.name}</label></td>
        </tr>
    </table>
</div>

<h2 class="decorated"><span><spring:message code="websocket.websockettopic.header.broadcasters"/></span></h2>
<c:choose>
    <c:when test="${webSocketTopic.broadcasters.size() > 0}">
        <form:form action="${webSocket_resource_update_url}/" method="POST"  commandName="webSocketBroadcasterModifierCommand">
            <div class="table-frame">
                <table class="entityTable sortable">
                    <col width="10%">
                    <col width="90%">
                    <tr>
                        <th><spring:message code="websocket.websockettopic.column.selected"/></th>
                        <th><spring:message code="websocket.websockettopic.column.broadcaster"/></th>
                    </tr>
                    <c:forEach items="${webSocketTopic.broadcasters}" var="broadcaster" varStatus="loopStatus">
                        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><form:checkbox path="webSocketBroadcasterIds" name="${broadcaster.id}" value="${broadcaster.id}"/></td>
                            <td><a href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopic.id}/broadcaster/${broadcaster.id}"/>">${broadcaster.name}</a></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                <button class="button-error pure-button" type="submit" name="action" value="delete-broadcasters"><i class="fa fa-trash"></i> <span><spring:message code="websocket.websockettopic.button.deletebroadcasters"/></span></button>
            </sec:authorize>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>

    </c:when>
    <c:otherwise>
        <spring:message code="websocket.websockettopic.label.nobroadcasters"/>
    </c:otherwise>
</c:choose>


<h2 class="decorated"><span><spring:message code="websocket.websockettopic.header.resources"/></span></h2>
<c:choose>
    <c:when test="${webSocketTopic.resources.size() > 0}">
        <form:form action="${webSocket_resource_update_url}/" method="POST"  commandName="webSocketResourceModifierCommand">
            <div class="table-frame">
                <table class="entityTable sortable">
                    <col width="10%">
                    <col width="20%">
                    <col width="30%">
                    <tr>
                        <th><spring:message code="websocket.websockettopic.column.selected"/></th>
                        <th><spring:message code="websocket.websockettopic.column.resource"/></th>
                        <th><spring:message code="websocket.websockettopic.column.uri"/></th>
                        <th><spring:message code="websocket.websockettopic.column.resourcestatus"/></th>
                    </tr>
                    <c:forEach items="${webSocketTopic.resources}" var="webSocketResource" varStatus="loopStatus">
                        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><form:checkbox path="webSocketResourceIds" name="${webSocketResource.id}" value="${webSocketResource.id}"/></td>
                            <td><a href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopic.id}/resource/${webSocketResource.id}"/>">${webSocketResource.name}</a></td>
                            <td><a href="<c:url value="/web/wss/project/${webSocketProjectId}/topic/${webSocketTopic.id}/resource/${webSocketResource.id}"/>">${webSocketResource.uri}</a></td>
                            <td><spring:message code="websocket.type.websocketresourcestatus.${webSocketResource.status}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                <form:select path="webSocketResourceStatus">
                    <c:forEach items="${webSocketResourceStatuses}" var="webSocketResourceStatus">
                        <form:option value="${webSocketResourceStatus}"><spring:message code="websocket.type.websocketresourcestatus.${webSocketResourceStatus}"/></form:option>
                    </c:forEach>
                </form:select>
                <button class="button-success pure-button" type="submit" name="action" value="update"><i class="fa fa-check-circle"></i> <span><spring:message code="websocket.websockettopic.button.update"/></span></button>
                <button class="button-secondary pure-button" type="submit" name="action" value="update-endpoint"><i class="fa fa-code-fork"></i> <span><spring:message code="websocket.websockettopic.button.updateendpoint"/></span></button>
                <button class="button-error pure-button" type="submit" name="action" value="delete-resources"><i class="fa fa-trash"></i> <span><spring:message code="websocket.websockettopic.button.deleteresources"/></span></button>
            </sec:authorize>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>

    </c:when>
    <c:otherwise>
        <spring:message code="websocket.websockettopic.label.noresources"/>
    </c:otherwise>
</c:choose>
