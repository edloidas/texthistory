<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${count!=0}">
    <table class="list" width=100%>
        <colgroup span="5">
            <col span="1">
            <col span="1">
            <col span="1">
            <col span="1">
            <col span="1">
        </colgroup>
        <thead>
        <tr class="first">
            <td></td>
            <td>Название</td>
            <td>Описание</td>
            <td>Создан</td>
            <td>Изменен</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${projects}" var="prj">
            <tr>
                <td><input type="checkbox" name="id" value="<c:out value="${prj.id}" />"/></td>
                <td>
                    <div class="nowrap name"><c:out value="${prj.name}"/></div>
                </td>
                <td>
                    <div class="nowrap desc"><c:out value="${prj.description}"/></div>
                </td>
                <td><c:out value="${prj.created}"/></td>
                <td><c:out value="${prj.updated}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${count==0}">
    <div class="remark">Нет рабочих проектов.</div>
</c:if>