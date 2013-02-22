<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul>
    <li><a id="nav-hm" class="white">Главная</a></li>
    <li><a id="nav-pm" class="dir green">Проект</a></li>
    <li><a id="nav-ca" class="dir orange">Контент-анализ</a></li>
    <li><a id="nav-da" class="dir blue">Дискурс-анализ</a></li>
    <li><a id="nav-pa" class="dir red">Психоанализ</a></li>
</ul>
<a id="account">
    <div class="caret"></div>
    <c:out value="${sessionUser}"/></a>
<br/>
<ul class="subdir nav-pm hidden">
    <!-- SUBDIR : PROJECT -->
    <li><a id="nav-pm-n" class="green tip" title="Новый проект">Новый проект</a></li>
    <li><a id="nav-pm-l" class="blue tip" title="Список проектов">Список проектов</a></li>
</ul>
<ul class="subdir nav-ca hidden">
    <!-- SUBDIR : CONTENT -->
    <li><a id="nav-ca-s" class="orange tip" title="Статистика">Статистика</a></li>
    <li><a id="nav-ca-t" class="blue tip" title="Работа со словами и блоками">Инструменты</a></li>
    <li><a id="nav-ca-g" class="red tip" title="Графики распределения">Графики</a></li>
    <li><a id="nav-ca-d" class="green tip" title="Создание словаря">Словарь</a></li>
</ul>
<ul class="subdir nav-da hidden">
    <!-- SUBDIR : DISCOURSE -->
    <li><a id="nav-da-c" class="blue tip" title="Конкорданс">Конкорданс</a></li>
    <li><a id="nav-da-t" class="red tip" title="Прочие инструменты">Инструменты</a></li>
</ul>
<ul class="subdir nav-pa hidden">
    <!-- SUBDIR : PSYCHO -->
    <li><a id="nav-pa-c" class="red tip" title="Конкорданс">Конкорданс</a></li>
    <li><a id="nav-pa-t" class="green tip" title="Прочие инструменты">Инструменты</a></li>
</ul>
<br id="newline"/>