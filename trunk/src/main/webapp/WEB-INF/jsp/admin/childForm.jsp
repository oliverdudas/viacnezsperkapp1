<%@ page import="sk.dudas.appengine.viacnezsperk.controller.UserController" %>
<%@ taglib prefix="fmy" uri="http://java.sun.com/jstl/fmt" %>
<%@ include file="../includes.jsp" %>

<div class="contentWrapper">

    <h2 style="display: inline-block;"><fmt:message key="child"/></h2>

    <div class="formWrapper">

        <form:form action="<%=UserController.ADMIN_CHILD_FORM_VIEW%>" method="POST" commandName="<%=UserController.CHILD_COMMAND%>" name="childForm">

            <table>
                <tr>
                    <td><fmy:message key="login.name"/></td>
                    <td><form:input path="username" /></td>
                </tr>
                <tr>
                    <td><fmy:message key="login.password"/></td>
                    <td><form:password path="password" /></td>
                </tr>
            </table>

            <div>
                <input type="submit" name="childFormSubmit" value="<fmt:message key="save"/>">
                <input type="submit" name="cancel" value="<fmt:message key="cancel"/>">
            </div>

        </form:form>

    </div>

</div>