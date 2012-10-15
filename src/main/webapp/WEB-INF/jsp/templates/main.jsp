<%--<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">--%>
<!DOCTYPE html>
<%@ include file="../includes.jsp" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
        <meta name="language" content="SK"/>
        <meta http-equiv="Content-Language" content="sk"/>
        <meta name="description" content="Robecca's Fashion"/>
        <meta name="keywords" content="viacnezsperk,deti,sperk"/>
        <title><tiles:insertAttribute name="title"/></title>

        <tiles:insertAttribute name="styles"/>
        <tiles:insertAttribute name="javascripts"/>
        <%--<tiles:insertAttribute name="ga"/>--%>

    </head>

    <body>

        <div id="main">

            <header>
                <tiles:insertAttribute name="header"/>
            </header>

            <section>
                <tiles:insertAttribute name="section"/>
            </section>

            <div class="container">
                <tiles:insertAttribute name="content"/>
            </div>

            <footer>
                <tiles:insertAttribute name="footer"/>
            </footer>
        </div>

    </body>

</html>