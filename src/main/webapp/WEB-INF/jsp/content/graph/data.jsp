<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${count!=0}">
    <div id="holder" style="height:<c:out value="${count * 42}"/>px;"></div>

    <script>
        var r = Raphael("holder"),
                xs = [<c:forEach items="${meanWords}" var="ws">0, 1, 2, 3, 4, </c:forEach>],
                ys = [
                    <c:forEach items="${meanWords}" var="ws" varStatus="i">
                    <c:out value="${i.index}" />, <c:out value="${i.index}" />, <c:out value="${i.index}" />, <c:out value="${i.index}" />, <c:out value="${i.index}" />,
                    </c:forEach>
                ],
                data = [
                    <c:forEach items="${meanWords}" var="ws" varStatus="index">
                    <c:out value="${ws.interval02}" />, <c:out value="${ws.interval24}" />, <c:out value="${ws.interval46}" />, <c:out value="${ws.interval68}" />, <c:out value="${ws.interval810}" />,
                    </c:forEach>
                ],
                axisy = [<c:forEach items="${meanWords}" var="ws">"<c:out value="${ws.name}" />", </c:forEach>],
                axisx = ["0% - 20%", "20% - 40%", "40% - 60%", "60% - 80%", "80% - 100%"];

        r.dotchart(0, -60, 600, ${count*40}, xs, ys, data, {symbol: "o", max: 10, heat: true, axis: "1 0 1 1", axisxstep: 4, axisystep: ${count-1}, axisxlabels: axisx, axisxtype: " ", axisytype: " ", axisylabels: axisy}).hover(function () {
            this.marker = this.marker || r.tag(this.x, this.y, this.value, 0, this.r + 2).insertBefore(this);
            this.marker.show();
        }, function () {
            this.marker && this.marker.hide();
        });
    </script>
</c:if>

<c:if test="${count==0}">
    <div class="remark">Слов не найдено.</div>
</c:if>