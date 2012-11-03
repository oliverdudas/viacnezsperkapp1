<%--@elvariable id="user" type="sk.dudas.appengine.viacnezsperk.domain.User"--%>
<%@ include file="includes.jsp" %>

<div class="contentWrapper">

    <h1 class="title">
        ${user.fullname}
    </h1>

    <div>
        <p>
            <font size="3">
                <br/>
                <img src="${user.mainURL}" alt="..." class="mainImg"/>
                ${user.content.value}
            </font>
        </p>
    </div>

</div>

