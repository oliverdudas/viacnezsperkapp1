<%--@elvariable id="user" type="sk.dudas.appengine.viacnezsperk.domain.User"--%>
<%@ include file="includes.jsp" %>

<div class="contentWrapper">

    <div>
        <p>
            <font size="3">
                <c:if test="${user.validMainURL}">
                    <img src="${user.mainURL}" alt="..." class="mainImg"/>
                </c:if>
                <div>
                    <table class="detailTable">
                        <c:if test="${user.validFirstname}">
                            <tr>
                                <td class="label"><fmt:message key="name"/>:</td>
                                <td class="value">${user.firstname}</td>
                            </tr>
                        </c:if>
                        <c:if test="${user.validBornYear}">
                            <tr>
                                <td class="label"><fmt:message key="bornYear"/>:</td>
                                <td class="value">${user.bornYear}</td>
                            </tr>
                        </c:if>
                        <c:if test="${user.validResidence}">
                            <tr>
                                <td class="label"><fmt:message key="residence"/>:</td>
                                <td class="value">${user.residence}</td>
                            </tr>
                        </c:if>
                    </table>
                </div>
                <hr/>
                <h2><fmt:message key="socialInfo"/></h2>
                ${user.content.value}
            </font>
        </p>
    </div>

</div>

