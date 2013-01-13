<%--@elvariable id="user" type="sk.dudas.appengine.viacnezsperk.domain.User"--%>
<%@ include file="includes.jsp" %>

<link rel="stylesheet" href="<c:url value="/js/prettyphoto/css/prettyPhoto.css"/>" type="text/css" media="screen"
      charset="utf-8"/>
<script src="<c:url value="/js/prettyphoto/js/jquery.prettyPhoto.js"/>" type="text/javascript" charset="utf-8"></script>

<style type="text/css">
    #homeGallery {
        /*display: inline-block;*/
    }

    #homeGallery .homeGalleryItemWrapper {
        display: inline-block;
        padding: 3px 3px 0 0;
    }
</style>

<script type="text/javascript" charset="utf-8">
    $(document).ready(function () {
        $("a[rel^='prettyPhoto']").prettyPhoto({
            social_tools:'',
            show_title:false,
            default_width:600,
            default_height:800
        });
    });
</script>

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
                <c:if test="${user.validGalleryItems}">
                    <div id="homeGallery">
                        <hr/>
                        <h2><fmt:message key="gallery"/></h2>
                        <c:forEach items="${user.galleryItems}" var="item">
                            <span class="homeGalleryItemWrapper">
                                <a href="${item.imageUrl}" rel="prettyPhoto [gallery]">
                                    <img src="${item.thumbUrl}" alt="...">
                                </a>
                            </span>
                        </c:forEach>
                    </div>
                </c:if>
                <hr/>
                <h2><fmt:message key="socialInfo"/></h2>
                ${user.content.value}
            </font>
        </p>
    </div>

</div>

