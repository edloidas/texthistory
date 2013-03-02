<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${count!=0}">
        <div id="holder"></div>
        <script>
            var data = [
                        <c:forEach items="${categories}" var="cat" varStatus="counter"><c:if test="${counter.index > 0}">,</c:if>[[<c:forEach items="${cat.intervals}" var="val" varStatus="iter"><c:if test="${iter.index > 0}">,</c:if>${val}</c:forEach>]]</c:forEach>
                    ];

            var name = new Array(<c:forEach items="${categories}" var="cat" varStatus="status"><c:if test="${status.index > 0}">,</c:if>"${cat.name}"</c:forEach>);
            var r = Raphael("holder", 600, (data.length*40));
            var txtattr = { font: "12px sans-serif", anchor: "left" };
            typeattr = {type: "sharp"};
            for (var i = 0; i < data.length; i++) {
                r.text(100, (15 + i*40), name.split(",")[i]).attr(txtattr);
                r.barchart(200, (-10 + i*40), 400, 50, data[i], 0).attr(typeattr);
            }
        </script>

    </c:when>
    <c:otherwise>
        <div class="remark">Слов не найдено.</div>
    </c:otherwise>
</c:choose>