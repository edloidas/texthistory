<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2>Психоанализ</h2>

<div class="sep"></div>

<c:if test="${count!=0}">
    <table class="list" width=100%>
        <colgroup span="3">
            <col span="1" width="26">
            <col span="1" width="200">
            <col span="1">
        </colgroup>
        <thead>
        <tr class="first">
            <td></td>
            <td>Категория</td>
            <td>Описание</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${psychoGroups}" var="ps">
            <tr>
                <td><input type="checkbox" name="id" value="<c:out value="${ps.id}" />"/></td>
                <td>
                    <div class="nowrap desc"><c:out value="${ps.name}"/></div>
                </td>
                <td>
                    <div class="nowrap desc"><c:out value="${ps.description}"/></div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${count==0}">
    <div class="remark">Слов не найдено.</div>
</c:if>