<%--@elvariable id="user" type="sk.dudas.appengine.viacnezsperk.domain.User"--%>
<%@ include file="includes.jsp" %>

<div class="contentWrapper">

    <h1 class="title">
        ${user.firstname}
    </h1>

    <div>
        <p>
            <font size="3">
                <br/>
                <c:if test="${user.validMainURL}">
                    <img src="${user.mainURL}" alt="..." class="mainImg"/>
                </c:if>
                <c:if test="${user.validBornYear && user.validResidence && user.validSocialInfo}">
                    <div>
                        <table class="detailTable">
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
                            <c:if test="${user.validSocialInfo}">
                                <tr>
                                    <td class="label"><fmt:message key="socialInfo"/>:</td>
                                    <td class="value">${user.socialInfo}</td>
                                </tr>
                            </c:if>
                        </table>
                    </div>
                    <hr class="detailSeparator"/>
                </c:if>
                ${user.content.value}
            </font>
        </p>
    </div>

</div>

