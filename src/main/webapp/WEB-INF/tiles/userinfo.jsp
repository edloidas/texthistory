<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table>
    <tbody>
    <tr>
        <td class="info-title">Проект:</td>
    </tr>
    <tr>
        <td class="info-data" id="cur-project"><c:out value="${sessionProject}"/></td>
    </tr>
    </tbody>
</table>
<div class="sep"></div>
<table>
    <thead>
    <tr>
        <td class="info-title">Авторы</td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>Толкачев</td>
        <td class="info-name">email:</td>
        <td class="info-data"><a href="mailto:edloidas@gmail.com">edloidas@gmail.com</a></td>
    </tr>
    <tr>
        <td class="info-title"></td>
        <td class="info-name">twitter:</td>
        <td class="info-data"><a href="https://twitter.com/edloidas">edloidas</a></td>
    </tr>
    <tr>
        <td>Приборович</td>
        <td class="info-name">email:</td>
        <td class="info-data"><a href="mailto:priborovich@gmail.com">priborovich@gmail.com</a></td>
    </tr>
    </tbody>
</table>
<div class="sep"></div>
<a id="logout">Выйти</a> || <a id="nav-settings">Настройки</a>











