<%@ page import="sk.dudas.appengine.viacnezsperk.domain.User" %>
<%@ page import="sk.dudas.appengine.viacnezsperk.util.MainUtil" %>
<%@ include file="includes.jsp" %>

<%
    User loggedUser = MainUtil.getLoggedUser();
%>

<div class="contentWrapper">

    <h1 class="title">
        <%=loggedUser.getFullname()%>
    </h1>

    <div>
        <p>
            <font size="3">
                <br/>
                <img src="<%=loggedUser.getMainURL()%>" alt="..." class="mainImg"/>
                <%=loggedUser.getContent()%>
            </font>

        </p>
    </div>

</div>

