<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Дискурс анализ</h2>

<div class="sep"></div>

<c:if test="${count!=0}">
    <table class="list" width=100%>
        <colgroup span="4">
            <col span="1" width="26">
            <col span="1" width="200">
            <col span="1">
            <col span="1">
        </colgroup>
        <thead>
        <tr class="first">
            <td></td>
            <td>Категория</td>
            <td>Описание</td>
            <td>Применение</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${discGroups}" var="gs">
            <tr>
                <td><input type="checkbox" name="id" value="<c:out value="${gs.id}" />"/></td>
                <td>
                    <div class="nowrap desc"><c:out value="${gs.name}"/></div>
                </td>
                <td>
                    <div class="nowrap desc"><c:out value="${gs.effects}"/></div>
                </td>
                <td>
                    <div class="nowrap desc"><c:out value="${gs.example}"/></div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${count==0}">
    <div class="remark">Слов не найдено.</div>
</c:if>