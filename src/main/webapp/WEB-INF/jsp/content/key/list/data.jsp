<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${count!=0}">
        <table class="list" width=100%>
            <colgroup span="8">
                <col span="1" width="26">
                <col span="1" width="40">
                <col span="1">
                <col span="1" width="100">
                <col span="1" width="60">
                <col span="1" width="100">
                <col span="2" width="60">
            </colgroup>
            <thead>
            <tr class="first">
                <td></td>
                <td>#</td>
                <td>Категория</td>
                <td>Тип</td>
                <td>Ранг</td>
                <td>Вхождений</td>
                <td>Вес</td>
                <td>Частота</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${categories}" var="cat">
                <tr>
                    <td><input type="checkbox" name="id" value="<c:out value="${cat.id}" />"/></td>
                    <td><c:out value="${cat.id}"/></td>
                    <td><c:out value="${cat.name}"/></td>
                    <td><c:choose><c:when test="${cat.size == 1}">слово</c:when><c:otherwise>категория</c:otherwise></c:choose></td>
                    <td><c:out value="${cat.rank}"/></td>
                    <td><c:out value="${cat.count}"/></td>
                    <td><c:out value="${cat.average}"/></td>
                    <td><c:out value="${cat.frequency}"/>%</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <div class="remark">Слов не найдено.</div>
    </c:otherwise>
</c:choose>