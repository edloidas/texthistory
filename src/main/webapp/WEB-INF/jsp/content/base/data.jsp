<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="list" width=100%>
    <colgroup span="2">
        <col span="1" width="200px">
        <col span="1">
    </colgroup>
    <thead>
    <tr class="first">
        <td>Параметр</td>
        <td>Значение</td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>Число слов</td>
        <td><c:out value="${textWordsCount}"/></td>
    </tr>
    <tr>
        <td>Средняя длина слова</td>
        <td><c:out value="${textWordsLength}"/></td>
    </tr>
    <tr>
        <td>Число значимых слов</td>
        <td><c:out value="${textMeanCount}"/></td>
    </tr>
    <tr>
        <td>Вода</td>
        <td><c:out value="${textWater}"/>%</td>
    </tr>
    </tbody>
</table>

<h2>Источник</h2>

<div class="sep"></div>
<pre>
    <c:out value="${text}"/>
</pre>


