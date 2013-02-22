<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Значимые слова</h2>

<div class="sep"></div>

<c:if test="${count!=0}">
    <table class="list" width=100%>
        <colgroup span="4">
            <col span="1" width="26">
            <col span="1">
            <col span="2" width="100">
        </colgroup>
        <thead>
        <tr class="first">
            <td></td>
            <td>Значимое слово</td>
            <td>Частота</td>
            <td>Вес</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${meanWords}" var="ws">
            <tr>
                <td><input type="checkbox" name="id" value="<c:out value="${ws.id}" />"/></td>
                <td><c:out value="${ws.name}"/></td>
                <td><c:out value="${ws.frequency}"/> ipm</td>
                <td><c:out value="${ws.mean}"/> %</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${count==0}">
    <div class="remark">Слов не найдено.</div>
</c:if>