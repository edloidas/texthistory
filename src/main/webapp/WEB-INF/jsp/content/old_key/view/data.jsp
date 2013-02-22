<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h2><c:out value="${word}"/></h2>

<div id="key-view-id" class="invisible"><c:out value="${id}"/></div>
<div class="sep"></div>

<c:if test="${meanCount!=0}">
    <table class="list mean" width=100%>
        <colgroup span="2">
            <col span="1" width="26">
            <col span="1">
        </colgroup>
        <thead>
        <tr class="first">
            <td></td>
            <td>Значимое слово</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${meanWords}" var="ws">
            <tr>
                <td><input type="checkbox" name="id" value="<c:out value="${ws.id}" />" disabled="true"
                        <c:if test="${keyMeanCount!=0}">
                            <c:forEach items="${keyMeanWords}" var="kws">
                                <c:if test="${ws.id == kws.id}">checked="true" </c:if>
                            </c:forEach>
                        </c:if>
                        />
                </td>
                <td><c:out value="${ws.name}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${meanCount==0}">
    <div class="remark">Слов не найдено.</div>
</c:if>

<h3>Параметрические характеристики</h3>

<div class="sep"></div>

<c:if test="${senseCount!=0}">
    <table class="list" width=100%>
        <colgroup span="3">
            <col span="1">
            <col span="2" width="100px">
        </colgroup>
        <thead>
        <tr class="first">
            <td>Категория</td>
            <td>Коэффициент корреляции K</td>
            <td>Коэффициент сходства S</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${senseBlocks}" var="bs">
            <tr>
                <c:if test="${bs.one.id == id}">
                    <td><c:out value="${bs.two.name}"/></td>
                </c:if>
                <c:if test="${bs.two.id == id}">
                    <td><c:out value="${bs.one.name}"/></td>
                </c:if>
                <td><c:out value="${bs.factorK}"/></td>
                <td><c:out value="${bs.factorS}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${senseCount==0}">
    <div class="remark">Недостаточно категорий.</div>
</c:if>
