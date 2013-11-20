<%@ page import="com.google.appengine.api.utils.SystemProperty" %>
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

        <!-- Pulled from http://code.google.com/p/html5shiv/ -->
        <!--[if lt IE 9]>
        <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

        <%
            if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
        %>
            <%--Google analytics only on production environment--%>
            <script>
              (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
              (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
              m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
              })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

              ga('create', 'UA-42457530-1', 'viacnezsperk.sk');
              ga('send', 'pageview');

            </script>
        <%
            }
        %>

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

            <footer id="footer">
                <tiles:insertAttribute name="footer"/>
            </footer>
        </div>

    </body>

</html>