<!DOCTYPE html>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page session="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>

<head>

    <style type="text/css">
        body {
            background: url("/images/main-bg.jpg") repeat-x fixed center top transparent;
            color: #474747;
            font: 100%/1.125em Georgia, "Times New Roman", Times, serif;
            position: relative;
            margin: 0;
        }

        #main {
            font-size: 0.813em;
            min-width: 960px;
        }

        header {
            background: url("/images/tail-top.png") repeat-x scroll center top transparent;
            height: 150px;
        }

        header .container_2 {
            height: 196px;
            position: relative;
            z-index: 2;
        }

        .container_2 {
            margin-left: auto;
            margin-right: auto;
            width: 960px;
        }

        h2 {
            margin-top: 100px;
            position: absolute;
        }
    </style>

</head>
<body>

<div id="main">
    <header>
        <div class="container_2">
            <h2>Stránka nenájdená --- <a href="/">Späť na úvodnú stránku</a></h2>
        </div>
    </header>
</div>

</body>

</html>