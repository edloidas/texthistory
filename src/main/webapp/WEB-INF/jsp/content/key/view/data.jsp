<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2><c:out value="${word}"/></h2>

<div id="mean-view-id" class="invisible"><c:out value="${id}"/></div>
<div class="sep"></div>

<c:if test="${count!=0}">
    <table class="list" width=100%>
        <colgroup span="4">
            <col span="1" width="26">
            <col span="1">
            <col span="1" width="140">
            <col span="1">
        </colgroup>
        <thead>
        <tr class="first">
            <td></td>
            <td class="taright">Левая часть</td>
            <td class="tacenter">Слово</td>
            <td class="taleft">Правая часть</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cons}" var="con">
            <tr>
                <td><!--<input type="checkbox" name="id" value="" />--></td>
                <td class="taright"><c:out value="${con.before}"/></td>
                <td class="tacenter"><c:out value="${con.word}"/></td>
                <td class="taleft"><c:out value="${con.after}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${count==0}">
    <div class="remark">Вхождений не найдено.</div>
</c:if>