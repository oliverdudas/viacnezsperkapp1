<%@ page import="sk.dudas.appengine.viacnezsperk.controller.UserController" %>
<%@ include file="../includes.jsp" %>
<%@ taglib prefix="tg" tagdir="/WEB-INF/tags" %>

<c:url value="/admin/childForm" var="childFormUrl"/>

<c:url value="<%=UserController.ADMIN_CHILDREN_VIEW%>" var="pagedURL">
    <c:param name="p" value="~"/>
</c:url>
<c:set var="pageView" value="<%=UserController.ADMIN_CHILDREN_VIEW%>"/>
<c:url value="<%=UserController.ADMIN_CHILD_FORM_VIEW%>" var="formURL">
</c:url>

<script type="text/javascript">
    $(document).ready(function () {
        $('.tableWrapper table tbody tr').click(function (e) {
            var id = $(this).attr('id');
            window.location = '${childFormUrl}?id=' + id;
        });

        $('.insightClass').click(function (e) {
//            e.preventDefault();
            e.stopPropagation();
        });
    });
</script>

<div class="contentWrapper">

    <h1 class="contentTitle"><fmt:message key="children.list"/></h1>

    <h2 class="contentMenuItem">
        <a href="${formURL}"><fmt:message key="add.child"/></a>
    </h2>

    <div>

        <%--@elvariable id="holder" type="org.springframework.beans.support.PagedListHolder>"--%>
        <div class="tableWrapper">

            <%-- // load our paging tag, pass pagedListHolder and the link --%>
            <tg:paging pagedListHolder="${holder}" pagedLink="${pagedURL}"/>

            <div class="tableContent">
                <table class="childrentable" cellpadding="0" cellspacing="0">
                    <thead>
                    <tr>
                        <th>
                            <a href="<c:url value="${pageView}">
                                        <c:param name="sort.property" value="username"/>
                                     </c:url>">
                                <fmt:message key="login.name"/>
                            </a>
                        </th>
                        <th>
                            <a href="<c:url value="${pageView}">
                                        <c:param name="sort.property" value="firstname"/>
                                     </c:url>">
                                <fmt:message key="name"/>
                            </a>
                        </th>
                        <th>
                            <a href="<c:url value="${pageView}">
                                        <c:param name="sort.property" value="lastname"/>
                                     </c:url>">
                                <fmt:message key="lastname"/>
                            </a>
                        </th>
                        <th>
                            <a href="<c:url value="${pageView}">
                                        <c:param name="sort.property" value="<%=UserController.MODIFIED%>"/>
                                     </c:url>">
                                <fmt:message key="modified"/>
                            </a>
                        </th>
                        <th>
                            <a href="<c:url value="${pageView}">
                                        <c:param name="sort.property" value="created"/>
                                     </c:url>">
                                <fmt:message key="created"/>
                            </a>
                        </th>
                        <th>
                            <span><fmt:message key="insight"/></span>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <%--@elvariable id="child" type="sk.dudas.appengine.viacnezsperk.domain.User"--%>
                    <c:forEach items="${holder.pageList}" var="child">
                        <c:if test="${!child.admin}">
                            <tr id="${child.key.id}">
                                <td class="firsttd">
                                    <div>${child.username}</div>
                                </td>
                                <td class="td">
                                    <div>${child.firstname}</div>
                                </td>
                                <td class="td">
                                    <div>${child.lastname}</div>
                                </td>
                                <td class="td">
                                    <div><fmt:formatDate value="${child.modified}" pattern="dd.MM.yyyy HH:mm"/></div>
                                </td>
                                <td class="td">
                                    <div><fmt:formatDate value="${child.created}" pattern="dd.MM.yyyy HH:mm"/></div>
                                </td>
                                <td class="lasttd">
                                    <div><a target="_blank" class="insightClass"
                                            href="<c:url value="/home?id=${child.key.id}"/>"><fmt:message
                                            key="insight"/></a></div>
                                </td>
                            </tr>
                        </c:if>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <%-- // load our paging tag, pass pagedListHolder and the link --%>
            <tg:paging pagedListHolder="${holder}" pagedLink="${pagedURL}"/>
        </div>

    </div>

</div>