<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Ключевые слова</h2>

<div class="sep"></div>

<table class="manager dia" width=100%>
    <colgroup>
        <col span="1" width="150">
    </colgroup>
    <tbody>
    <tr>
        <td>Новая категория:</td>
        <td><input type="textfield" id="key-word-name" class="field tf"></td>
    </tr>
    </tbody>
</table>

<c:if test="${count!=0}">
    <table class="list" width=100%>
        <colgroup span="6">
            <col span="1" width="26">
            <col span="1">
            <col span="4" width="100">
        </colgroup>
        <thead>
        <tr class="first">
            <td></td>
            <td>Категория</td>
            <td>Z-Score</td>
            <td>Объем</td>
            <td>Количество слов</td>
            <td>i использования</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${keyWords}" var="ws">
            <tr>
                <td><input type="checkbox" name="id" value="<c:out value="${ws.id}" />"/></td>
                <td><c:out value="${ws.name}"/></td>
                <td><c:out value="${ws.zScore}"/></td>
                <td><c:out value="${ws.volume}"/> %</td>
                <td><c:out value="${ws.count}"/></td>
                <td><c:out value="${ws.index}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${count==0}">
    <div class="remark">Слов не найдено.</div>
</c:if>