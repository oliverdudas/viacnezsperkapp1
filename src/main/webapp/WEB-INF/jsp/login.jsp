<%@ include file="includes.jsp" %>

<div class="contentWrapper">

    <h2 style="font-family: Times New Roman,Times,serif;text-align: center;"><fmt:message key="your.child" /></h2>

    <form id="loginForm" action="j_spring_security_check" method="post">
        <table class="loginTable">
            <tr>
                <td>
                    <fmt:message key="login.name"/>:
                </td>
                <td>
                    <input id="j_username"
                           name="j_username"
                           value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>'
                           size="20"
                           maxlength="50"
                           type="text"/>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="login.password"/>:
                </td>
                <td>
                    <input id="j_password" name="j_password" size="20" maxlength="50" type="password"/>
                </td>
            </tr>
            <c:if test="${not empty param.login_error}">
                <tr>
                    <td colspan="2" id="loginErrorMessage" class="form-error">
                        <%--<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>--%>
                        <fmt:message key="login.error"/>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td colspan="2" style="text-align: center">
                    <input type="submit" value="<fmt:message key="login.button"/>"/>
                </td>
            </tr>
        </table>
    </form>

</div>