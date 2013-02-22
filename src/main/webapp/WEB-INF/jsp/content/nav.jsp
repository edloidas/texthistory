<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul>
    <li><a id="nav-home" class="tip" title="Начальная страница">Главная</a></li>
    <li><a id="nav-proj" class="tip" title="Управление проектами">Проекты</a></li>
    <li><a id="nav-cont" class="tip active" title="Модуль контент-анализа">Контент-анализ</a></li>
    <li><a id="nav-disc" class="tip" title="Модуль дискурс-анализа">Дискурс-анализ</a></li>
    <li><a id="nav-psycho" class="tip" title="Модуль психоанализа">Психоанализ</a></li>
</ul>
<a id="account">
    <div class="caret"></div>
    <c:out value="${sessionUser}"/></a>