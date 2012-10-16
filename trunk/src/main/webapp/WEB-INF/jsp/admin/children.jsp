<%@ include file="../includes.jsp" %>

<div class="contentWrapper">

    <h2 style="display: inline-block;"><fmt:message key="children.list"/></h2>

    <div>
        <a href="/admin/addChild"><fmt:message key="add.child" /></a>
    </div>

    <div style="text-align: center">

        <%--@elvariable id="all" type="java.util.List<sk.dudas.appengine.viacnezsperk.domain.User>"--%>
        <div class="tableWrapper">
            <table class="childrentable" cellpadding="0" cellspacing="0">
                <thead>
                <tr>
                    <th><fmt:message key="login.name"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${all}" var="child">
                    <tr>
                        <td>
                            <div>${child.username}</div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>

</div>