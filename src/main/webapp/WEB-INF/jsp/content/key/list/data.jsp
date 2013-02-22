<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${count!=0}">
    <table class="list" width=100%>
        <colgroup span="4">
            <col span="1" width="26">
            <col span="1">
            <col span="3" width="100">
        </colgroup>
        <thead>
        <tr class="first">
            <td></td>
            <td>Категория</td>
            <td>Ранг</td>
            <td>Вес</td>
            <td>Частота</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${keyWords}" var="ks">
            <tr>
                <td><input type="checkbox" name="id" value="<c:out value="${ks.id}" />"/></td>
                <td><c:out value="${ks.name}"/></td>
                <td><c:out value="${ks.rank}"/></td>
                <td><c:out value="${ks.mean}"/> %</td>
                <td><c:out value="${ks.frequency}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${count==0}">
    <div class="remark">Слов не найдено.</div>
</c:if>