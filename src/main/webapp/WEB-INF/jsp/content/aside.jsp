<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul>
    <li>
        <div class="menuField nav-cont" id="nav-cont-base">
            <div class="caret none"></div>
            <a>Общее</a>
        </div>
    </li>
    <li class="aside-sub-show">
        <div class="menuField">
            <div class="caret"></div>
            <a>Значимые слова</a>
        </div>
        <ul>
            <li><a id="nav-cont-mean-list">Список слов</a></li>
            <li><a id="nav-cont-mean-graph">График распределенности</a></li>
        </ul>
    </li>
    <li>
        <div class="menuField nav-cont-key-list" id="nav-cont-key">
            <div class="caret none"></div>
            <a>Ключевые слова (категории)</a>
        </div>
    </li>
</ul>