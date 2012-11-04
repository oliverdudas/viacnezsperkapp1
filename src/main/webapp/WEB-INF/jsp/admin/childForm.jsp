<%@ page import="sk.dudas.appengine.viacnezsperk.controller.UserController" %>
<%@ include file="../includes.jsp" %>

<script type="text/javascript" src="<c:url value="/js/loader/loader.js"/>"></script>

<script type="text/javascript" src="http://bp.yahooapis.com/2.4.21/browserplus-min.js"></script>

<script type="text/javascript" src="<c:url value="/js/plupload/plupload.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/plupload/plupload.gears.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/plupload/plupload.silverlight.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/plupload/plupload.flash.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/plupload/plupload.browserplus.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/plupload/plupload.html4.js"/>"></script>
<script type="text/javascript" src="<c:url value="/js/plupload/plupload.html5.js"/>"></script>

<style type="text/css">
    .systemdata td {
        text-align: right;
    }

    input[type="file"] {
        display: none;
    }

    .blue-loader .ajax_loader {
        background: url('<c:url value="/js/loader/ajax-loader_blue.gif"/>') no-repeat scroll center center transparent;
    }

    .ajax_loader {
        height: 100%;
        width: 100%;
    }

    textarea {
        width: 760px;
        height: 200px;
    }

</style>

<div class="contentWrapper">

    <h2 style="display: inline-block;"><fmt:message key="child"/></h2>

    <div class="formWrapper">

        <form:form action="<%=UserController.ADMIN_CHILD_FORM_VIEW%>"
                   commandName="<%=UserController.CHILD_COMMAND%>" name="childForm" id="childFormId"
                   enctype="multipart/form-data">

            <table class="formTable">
                <tr>
                    <td class="label" title="<fmt:message key="required"/>"><fmt:message key="login.name"/>*:</td>
                    <td class="value"><form:input id="usernameId" path="username"/><form:errors cssClass="form-error"
                                                                                path="username"/></td>
                </tr>
                <tr>
                    <td class="label" title="<fmt:message key="required"/>"><fmt:message key="login.password"/>:</td>
                    <td class="value">
                        <form:input path="password" id="passwordId"/>
                        <input type="button" id="generateId" value="<fmt:message key="generate"/>"/>
                        <form:errors cssClass="form-error" path="password"/></td>
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
                    <td class="value"><form:input path="age"/><form:errors cssClass="form-error" path="age"/></td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="main.url"/>:</td>
                    <td class="value">
                        <form:input id="mainUrlId" path="mainURL"/>
                        <span id="container">
                            <a id="pickfiles" href="javascript:;"><fmt:message key="select.file"/></a>
                            <span id="filelist"></span>
                            <a id="uploadfiles" style="display: none;" href="javascript:;"><fmt:message
                                    key="upload.to.picasa"/></a>
                        </span>
                    </td>
                </tr>
                <tr>
                    <td class="label" style="vertical-align: top;"><fmt:message key="main.content"/>:</td>
                    <td class="value"><form:textarea id="contentId" path="content"/></td>
                </tr>
            </table>

            <table class="formTable systemdata">
                <tr>
                    <td class="label"><fmt:message key="created"/>:</td>
                    <td class="value"><fmt:formatDate value="${child.created}" pattern="dd.MM.yyyy HH:mm"/></td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="createdBy"/>:</td>
                    <td class="value">${child.createdBy}</td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="modified"/>:</td>
                    <td class="value"><fmt:formatDate value="${child.modified}" pattern="dd.MM.yyyy HH:mm"/></td>
                </tr>
                <tr>
                    <td class="label"><fmt:message key="modifiedBy"/>:</td>
                    <td class="value">${child.modifiedBy}</td>
                </tr>
            </table>

            <div class="submitArea">
                <input type="submit" name="childFormSubmit" value="<fmt:message key="save"/>">
                <input type="submit" name="cancel" value="<fmt:message key="cancel"/>">
                <c:if test="${child.key != null}">
                    <input type="submit" name="delete" id="deleteId" value="<fmt:message key="delete"/>"
                           style="margin-left: 30px;">
                </c:if>
            </div>

        </form:form>

    </div>

</div>

<script type="text/javascript">

    $(document).ready(function () {

        <c:if test="${child.key != null}">
            $('#contentId').get(0).focus();
        </c:if>
        <c:if test="${child.key == null}">
            $('#usernameId').get(0).focus();
        </c:if>

        // Custom example logic
        function getElemById(id) {
            return document.getElementById(id);
        }

        var loader = null;

        var uploader = new plupload.Uploader({
            runtimes:'gears,html5,flash,silverlight,browserplus',
            browse_button:'pickfiles',
            container:'container',
            multipart:true,
            max_file_size:'10mb',
            multi_selection:false,
            url:'<c:url value="/admin/uploadTest"/>',
            resize:{width:800, height:600, quality:90},
            flash_swf_url:'<c:url value="/js/plupload/plupload.flash.swf"/>',
            silverlight_xap_url:'<c:url value="/js/plupload/plupload.silverlight.xap"/>',
            filters:[
                {title:"Image files", extensions:"jpg,gif,png"}
            ]
        });

        function removeFile(uploader, up) {
            var fileToRemove = uploader.files[0];
            var idToRemove = fileToRemove.id;
            uploader.removeFile(fileToRemove);
            $('#' + idToRemove).remove();
            up.refresh(); // Reposition Flash/Silverlight
        }

        uploader.bind('FilesAdded', function (up, files) {

            if (uploader.files.length == 1) {
                removeFile(uploader, up);
            }

            for (var i in files) {
                getElemById('filelist').innerHTML += '<span id="' + files[i].id + '">' + files[i].name + ' (' + plupload.formatSize(files[i].size) + ') <b></b></span>';
            }

            $('#uploadfiles').show();
        });

        uploader.bind('FileUploaded', function (up, file, info) {
            $('#mainUrlId').val(info.response);
            removeFile(uploader, up);
            $('#uploadfiles').hide();
            loader.remove();
        });


        uploader.bind('UploadFile', function (up, file) {
            loader = new ajaxLoader($('body'), {classOveride:'blue-loader', bgColor:'#000', opacity:'0.3'});
        });

        uploader.bind('UploadProgress', function (up, file) {
            var elemById = getElemById(file.id);
            if (elemById) {
                elemById.getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
            }
        });

        getElemById('uploadfiles').onclick = function () {
            uploader.start();
            return false;
        };

        uploader.init();

    });
</script>
<script type="text/javascript">

    $(document).ready(function () {

        $('#deleteId').click(function (e) {
            return confirm('<fmt:message key="delete.question"/>');
        });

        function getRandomInt(lower, upper) {
            //to create an even sample distribution
            return Math.floor(lower + (Math.random() * (upper - lower + 1)));

            //to produce an uneven sample distribution
            //return Math.round(lower + (Math.random() * (upper - lower)));

            //to exclude the max value from the possible values
            //return Math.floor(lower + (Math.random() * (upper - lower)));
        }

        $('#generateId').click(function(e) {
            $('#passwordId').val(getRandomInt(1000, 9999));
        });

    });

</script>