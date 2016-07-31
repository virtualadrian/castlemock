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

<c:url var="websocket_application_update_url"  value="/web/wss/project/${webSocketProject.id}" />
<div class="content-top">
    <div class="title">
        <h1><spring:message code="websocket.websocketproject.header.project" arguments="${webSocketProject.name}"/></h1>
    </div>
    <div class="menu" align="right">
        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
        <a class="button-success pure-button" href="<c:url value="/web/wss/project/${webSocketProject.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="websocket.websocketproject.button.updateproject"/></span></a>
        <a class="button-secondary pure-button" href="<c:url value="/web/wss/project/${webSocketProject.id}/create/application"/>"><i class="fa fa-plus"></i> <span><spring:message code="websocket.websocketproject.button.createapplication"/></span></a>
        <a class="button-secondary pure-button" href="<c:url value="/web/wss/project/${webSocketProject.id}/export"/>"><i class="fa fa-cloud-download"></i> <span><spring:message code="websocket.websocketproject.button.export"/></span></a>
        <a class="button-error pure-button" href="<c:url value="/web/wss/project/${webSocketProject.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="websocket.websocketproject.button.delete"/></span></a>
        </sec:authorize>
    </div>
</div>
<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="websocket.websocketproject.label.name"/></label></td>
            <td class="column2"><label path="name">${webSocketProject.name}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="description"><spring:message code="websocket.websocketproject.label.description"/></label></td>
            <td class="column2"><label path="description">${webSocketProject.description}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="projectType"><spring:message code="websocket.websocketproject.label.type"/></label></td>
            <td class="column2"><label path="projectType">WebSocket</label></td>
        </tr>
    </table>
</div>
<h2 class="decorated"><span><spring:message code="websocket.websocketproject.header.applications"/></span></h2>
<c:choose>
    <c:when test="${webSocketProject.applications.size() > 0}">
        <form:form action="${websocket_application_update_url}/" method="POST"  commandName="webSocketApplicationModifierCommand">
            <div class="table-frame">
                <table class="entityTable">
                    <tr>
                        <th><spring:message code="websocket.websocketproject.column.selected"/></th>
                        <th><spring:message code="websocket.websocketproject.column.application"/></th>
                        <c:forEach items="${webSocketResourceStatuses}" var="webSocketResourceStatus">
                            <th><spring:message code="websocket.type.websocketresourcestatus.${webSocketResourceStatus}"/></th>
                        </c:forEach>
                    </tr>
                    <c:forEach items="${webSocketProject.applications}" var="webSocketApplication" varStatus="loopStatus">
                        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><form:checkbox path="webSocketApplicationIds" name="${webSocketApplication.id}" value="${webSocketApplication.id}"/></td>
                            <td><a href="<c:url value="/web/wss/project/${webSocketProject.id}/application/${webSocketApplication.id}"/>">${webSocketApplication.name}</a></td>
                            <c:forEach items="${webSocketResourceStatuses}" var="webSocketResourceStatus">
                                <td>${webSocketApplication.statusCount[webSocketResourceStatus]}</td>
                            </c:forEach>
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
                <button class="button-success pure-button" type="submit" name="action" value="update"><i class="fa fa-check-circle"></i> <span><spring:message code="websocket.websocketproject.button.update"/></span></button>
                <button class="button-secondary pure-button" type="submit" name="action" value="update-endpoint"><i class="fa fa-code-fork"></i> <span><spring:message code="websocket.websocketproject.button.updateendpoint"/></span></button>
                <button class="button-error pure-button" type="submit" name="action" value="delete"><i class="fa fa-trash"></i> <span><spring:message code="websocket.websocketproject.button.deleteapplication"/></span></button>
            </sec:authorize>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>

    </c:when>
    <c:otherwise>
        <spring:message code="websocket.websocketproject.label.noapplications" arguments="wsdl"/>
    </c:otherwise>
</c:choose>
