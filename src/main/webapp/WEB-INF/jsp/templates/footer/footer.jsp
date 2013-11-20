<%@ include file="../../includes.jsp" %>
<security:authorize ifAnyGranted="ROLE_USER">
    <div id="footerContent">
        <p>
            <span class="fright"></span>
            <a class="alt" title="" href="http://www.viacnezsperk.sk/"><fmt:message key="viacnezsperk"/></a>
        </p>
    </div>
</security:authorize>
