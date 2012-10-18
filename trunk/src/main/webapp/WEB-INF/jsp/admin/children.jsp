<%@ page import="sk.dudas.appengine.viacnezsperk.controller.UserController" %>
<%@ include file="../includes.jsp" %>

<c:url value="/admin/childForm" var="childFormUrl"/>

<script type="text/javascript">
    $(document).ready(function() {
        $('.tableWrapper table tbody tr').click(function(e) {
            var id = $(this).attr('id');
            window.location = '${childFormUrl}?id=' + id;
        });
    });
</script>

<div class="contentWrapper">

    <h2 style="display: inline-block;"><fmt:message key="children.list"/></h2>

    <div>
        <a href="<%=UserController.ADMIN_CHILD_FORM_VIEW%>"><fmt:message key="add.child" /></a>
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
                    <tr id="${child.key.id}">
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