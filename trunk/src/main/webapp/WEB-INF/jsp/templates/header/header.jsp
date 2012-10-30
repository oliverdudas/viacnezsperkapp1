<%@ include file="../../includes.jsp" %>

<div class="container_2">

    <div class="grid_2">

        <div class="logo">
            <a class="logo-link" href="http://www.viacnezsperk.sk/">
                <img title="" alt="Viac než šperk"
                     src="http://www.viacnezsperk.sk/wp-content/themes/theme1205/images/temp/logo.png">
            </a>
            <span class="description"></span>
        </div>

        <nav>
            <ul class="nav">
                <security:authorize ifAnyGranted="ROLE_USER">
                    <li>
                        <a href="<c:url value="/logout"/>"><fmt:message key="logout"/></a>
                    </li>
                </security:authorize>
            </ul>
        </nav>

    </div>

</div>