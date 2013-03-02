<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- CATEGORY -->
    <!-- Key word AND Concordance -->
    <!-- Key word AND Concordance -->
    <!-- Key word AND Concordance -->
    <!-- Key word AND Concordance -->

<h2><c:out value="${category.name}"/></h2>
<div class="invisible" id="id"><c:out value="${category.id}"/></div>
<div class="sep"></div>
<c:choose>
    <c:when test="${category.size != 0}">
        <c:forEach items="${keys}" var="key">
            <h3>${key.name}</h3>
            <table class="list">
                <colgroup span="3">
                    <col span="1">
                    <col span="1" width="100">
                    <col span="1">
                </colgroup>
                <thead><tr><td colspan="3">Конкорданс</td></tr></thead>
                <tbody>
                    <c:forEach items="${key.concordances}" var="con">
                        <tr>
                            <td class="aright"><c:out value="${con.before}"/></td>
                            <td class="acenter"><c:out value="${con.word}"/></td>
                            <td><c:out value="${cat.after}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <p></p>
            <table class="list">
                <colgroup span="3">
                    <col span="1" width="200">
                    <col span="2" width="100">
                </colgroup>
                <thead>
                    <tr><td colspan="3">z-Оценки</td></tr>
                    <tr>
                        <td>Слово</td>
                        <td>Вхождений</td>
                        <td>z-Оценка</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${key.scores}" var="score">
                    <tr>
                        <td><c:out value="${score.word}"/></td>
                        <td><c:out value="${score.count}"/></td>
                        <td><c:out value="${score.score}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </c:forEach>
    </c:when>
    <c:otherwise>
        <div class="remark">Слов не найдено.</div>
    </c:otherwise>
</c:choose>