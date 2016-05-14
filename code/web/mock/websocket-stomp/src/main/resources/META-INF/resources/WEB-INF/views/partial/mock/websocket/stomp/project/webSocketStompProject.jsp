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

<c:url var="websocket_stomp_resource_update_url"  value="/web/wss/project/${webSocketStompProject.id}" />
<div class="content-top">
    <div class="title">
        <h1><spring:message code="websocket.stomp.websocketstompproject.header.project" arguments="${webSocketStompProject.name}"/></h1>
    </div>
    <div class="menu" align="right">
        <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
        <a class="button-success pure-button" href="<c:url value="/web/wss/project/${webSocketStompProject.id}/update"/>"><i class="fa fa-file"></i> <span><spring:message code="websocket.stomp.websocketstompproject.button.updateproject"/></span></a>
        <a class="button-secondary pure-button" href="<c:url value="/web/wss/project/${webSocketStompProject.id}/export"/>"><i class="fa fa-cloud-download"></i> <span><spring:message code="websocket.stomp.websocketstompproject.button.export"/></span></a>
        <a class="button-error pure-button" href="<c:url value="/web/wss/project/${webSocketStompProject.id}/delete"/>"><i class="fa fa-trash"></i> <span><spring:message code="websocket.stomp.websocketstompproject.button.delete"/></span></a>
        </sec:authorize>
    </div>
</div>
<div class="content-summary">
    <table class="formTable">
        <tr>
            <td class="column1"><label path="name"><spring:message code="websocket.stomp.websocketstompproject.label.name"/></label></td>
            <td class="column2"><label path="name">${webSocketStompProject.name}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="description"><spring:message code="websocket.stomp.websocketstompproject.label.description"/></label></td>
            <td class="column2"><label path="description">${webSocketStompProject.description}</label></td>
        </tr>
        <tr>
            <td class="column1"><label path="projectType"><spring:message code="websocket.stomp.websocketstompproject.label.type"/></label></td>
            <td class="column2"><label path="projectType">WebSocket Stomp</label></td>
        </tr>
    </table>
</div>
<h2 class="decorated"><span><spring:message code="websocket.stomp.websocketstompproject.header.applications"/></span></h2>
<c:choose>
    <c:when test="${webSocketStompProject.applications.size() > 0}">
        <form:form action="${websocket_stomp_resource_update_url}/" method="POST"  commandName="webSocketStompApplicationsModifierCommand">
            <div class="table-frame">
                <table class="entityTable">
                    <tr>
                        <th><spring:message code="websocket.stomp.websocketstompproject.column.selected"/></th>
                        <th><spring:message code="websocket.stomp.websocketstompproject.column.application"/></th>
                        <c:forEach items="${webSocketStompResourceStatus}" var="webSocketStompResourceStatus">
                            <th><spring:message code="websocket.stomp.type.websocketstompresourcestatus.${websocketStompResourceStatus}"/></th>
                        </c:forEach>
                    </tr>
                    <c:forEach items="${webSocketStompProject.applications}" var="webSocketStompApplication" varStatus="loopStatus">
                        <tr class="${loopStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <td><form:checkbox path="websocketStompApplicationIds" name="${webSocketStompApplication.id}" value="${webSocketStompApplication.id}"/></td>
                            <td><a href="<c:url value="/web/wss/project/${webSocketStompProject.id}/application/${webSocketStompApplication.id}"/>">${webSocketStompApplication.name}</a></td>
                            <c:forEach items="${webSocketStompResourceStatuses}" var="webSocketStompResourceStatus">
                                <td>${webSocketStompApplication.statusCount[webSocketStompResourceStatus]}</td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <sec:authorize access="hasAuthority('ADMIN') or hasAuthority('MODIFIER')">
                <form:select path="webSocketStompResourceStatus">
                    <c:forEach items="${webSocketStompResourceStatuses}" var="webSocketStompResourceStatus">
                        <form:option value="${webSocketStompResourceStatus}"><spring:message code="websocket.stomp.type.webSocketStompResourceStatus.${webSocketStompResourceStatus}"/></form:option>
                    </c:forEach>
                </form:select>
                <button class="button-success pure-button" type="submit" name="action" value="update"><i class="fa fa-check-circle"></i> <span><spring:message code="websocket.stomp.websocketstompproject.button.update"/></span></button>
                <button class="button-secondary pure-button" type="submit" name="action" value="update-endpoint"><i class="fa fa-code-fork"></i> <span><spring:message code="websocket.stomp.websocketstompproject.button.updateendpoint"/></span></button>
                <button class="button-error pure-button" type="submit" name="action" value="delete"><i class="fa fa-trash"></i> <span><spring:message code="websocket.stomp.websocketstompproject.button.deleteapplication"/></span></button>
            </sec:authorize>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form:form>

    </c:when>
    <c:otherwise>
        <spring:message code="websocket.stomp.websocketstompproject.label.noapplications" arguments="wsdl"/>
    </c:otherwise>
</c:choose>
