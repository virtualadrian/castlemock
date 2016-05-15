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

<c:url var="websocketstomp_resource_update_url"  value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplication.id}" />
<div class="content-top">
    <div class="title">
        <h1><spring:message code="websocket.stomp.websocketstompapplication.header.application" arguments="${webSocketStompApplication.name}"/></h1>
    </div>
    <div class="menu" align="right">
        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
            <a class="button-success pure-button" href="<c:url value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplication.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="websocket.stomp.websocketstompapplication.button.updateapplication"/></span></a>
            <a class="button-secondary pure-button" href="<c:url value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplication.id}/create/resource"/>"><i class="fa fa-plus"></i> <span><spring:message code="websocket.stomp.websocketstompapplication.button.createresource"/></span></a>
          <a class="button-error pure-button" href="<c:url value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplication.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="websocket.stomp.websocketstompapplication.button.delete"/></span></a>
        </sec:authorize>
    </div>
</div>
<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="websocket.stomp.websocketstompapplication.label.name"/></label></td>
            <td class="column2"><label path="name">${webSocketStompApplication.name}</label></td>
        </tr>
    </table>
</div>

<h2 class="decorated"><span><spring:message code="websocket.stomp.websocketstompapplication.header.resources"/></span></h2>
<c:choose>
    <c:when test="${webSocketStompApplication.resources.size() > 0}">
        <form:form action="${websocketstomp_resource_update_url}/" method="POST"  commandName="webSocketStompResourceModifierCommand">
            <div class="table-frame">
                <table class="entityTable">
                    <col width="10%">
                    <col width="20%">
                    <col width="30%">
                    <tr>
                        <th><spring:message code="websocket.stomp.websocketstompapplication.column.selected"/></th>
                        <th><spring:message code="websocket.stomp.websocketstompapplication.column.resource"/></th>
                        <th><spring:message code="websocket.stomp.websocketstompapplication.column.uri"/></th>
                        <th><spring:message code="websocket.stomp.websocketstompapplication.column.resourcestatus"/></th>
                    </tr>
                    <c:forEach items="${webSocketStompApplication.resources}" var="webSocketStompResource" varStatus="loopStatus">
                        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><form:checkbox path="webSocketStompResourceIds" name="${webSocketStompResource.id}" value="${webSocketStompResource.id}"/></td>
                            <td><a href="<c:url value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplication.id}/resource/${webSocketStompResource.id}"/>">${webSocketStompResource.name}</a></td>
                            <td><a href="<c:url value="/web/wss/project/${webSocketStompProjectId}/application/${webSocketStompApplication.id}/resource/${webSocketStompResource.id}"/>">${webSocketStompResource.uri}</a></td>
                            <td><spring:message code="websocket.stomp.type.websocketstompresourcestatus.${webSocketStompResource.status}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                <form:select path="webSocketStompResourceStatus">
                    <c:forEach items="${webSocketStompResourceStatuses}" var="webSocketStompResourceStatus">
                        <form:option value="${webSocketStompResourceStatus}"><spring:message code="websocket.stomp.type.websocketstompresourcestatus.${webSocketStompResourceStatus}"/></form:option>
                    </c:forEach>
                </form:select>
                <button class="button-success pure-button" type="submit" name="action" value="update"><i class="fa fa-check-circle"></i> <span><spring:message code="websocket.stomp.websocketstompapplication.button.update"/></span></button>
                <button class="button-secondary pure-button" type="submit" name="action" value="update-endpoint"><i class="fa fa-code-fork"></i> <span><spring:message code="websocket.stomp.websocketstompapplication.button.updateendpoint"/></span></button>
                <button class="button-error pure-button" type="submit" name="action" value="delete"><i class="fa fa-trash"></i> <span><spring:message code="websocket.stomp.websocketstompapplication.button.deleteresources"/></span></button>
            </sec:authorize>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>

    </c:when>
    <c:otherwise>
        <spring:message code="websocket.stomp.websocketstompapplication.label.noresources"/>
    </c:otherwise>
</c:choose>
