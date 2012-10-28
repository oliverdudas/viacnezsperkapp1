<%@ page import="sk.dudas.appengine.viacnezsperk.controller.UserController" %>
<%@ include file="../includes.jsp" %>

<style type="text/css">
    .systemdata td {
        text-align: right;
    }

    input[type="file"] {
        display: none;
    }
</style>

<script type="text/javascript">

    $(document).ready(function () {

        $('#triggerButton').click(function () {
            $('#fileId').trigger('click');
        });

        $('input[type="text"]').css({
            width:'150px;'
        });

//        var elem = $('#mainUrlId');
//        var urlVal = elem.val();
//        var length = urlVal.length;
//        var len = length*7;
//        elem.css({
//            width: len+'px'
//        });

    });

    function upload() {
        $('#uploadId').trigger('click');
    }

</script>

<div class="contentWrapper">

    <h2 style="display: inline-block;"><fmt:message key="child"/></h2>

    <div class="formWrapper">

        <form:form action="<%=UserController.ADMIN_CHILD_FORM_VIEW%>"
                   commandName="<%=UserController.CHILD_COMMAND%>" name="childForm" enctype="multipart/form-data">

            <table class="formTable">
                <tr>
                    <td class="label"><fmt:message key="login.name"/>:</td>
                    <td class="value"><form:input path="username"/></td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="login.password"/>:</td>
                    <td class="value"><form:password path="password"/></td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="name"/>:</td>
                    <td class="value"><form:input path="firstname"/></td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="lastname"/>:</td>
                    <td class="value"><form:input path="lastname"/></td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="age"/>:</td>
                    <td class="value"><form:input path="age"/></td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="main.url"/>:</td>
                    <td class="value">
                        <form:input id="mainUrlId" path="mainURL"/><input type="button" value="Nahrat foto"
                                                                          id="triggerButton">
                        <input type="submit" value="Upload" id="uploadId" style="display: none" name="uploadImage">
                        <input onchange="upload()" type="file" id="fileId" name="file" style="display: none">
                            <%--<table>--%>
                            <%--<tr>--%>
                            <%--<td><input type="file" id="fileId" name="file"><input type="submit" value="upload" name="uploadImage"></td>--%>
                            <%--</tr>--%>
                            <%--</table>--%>
                    </td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="main.content"/>:</td>
                    <td class="value"><form:textarea path="content"/></td>
                </tr>
            </table>

            <table class="formTable systemdata">
                <tr>
                    <td class="label"><fmt:message key="created"/>:</td>
                    <td class="value"><fmt:formatDate value="${child.created}"/></td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="createdBy"/>:</td>
                    <td class="value">${child.createdBy}</td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="modified"/>:</td>
                    <td class="value"><fmt:formatDate value="${child.modified}"/></td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="modifiedBy"/>:</td>
                    <td class="value">${child.modifiedBy}</td>
                </tr>
            </table>

            <div>
                <input type="submit" name="childFormSubmit" value="<fmt:message key="save"/>">
                <input type="submit" name="cancel" value="<fmt:message key="cancel"/>">
            </div>

        </form:form>

    </div>

</div>