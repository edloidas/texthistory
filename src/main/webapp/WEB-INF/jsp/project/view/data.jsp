<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="manager" width=100%>
    <colgroup>
        <col span="1" width="100">
    </colgroup>
    <tbody>
    <tr class="invisible">
        <td>id:</td>
        <td id="project-view-id"><c:out value="${project.id}"/></td>
    </tr>
    <tr>
        <td>Название:</td>
        <td>
            <div class="ta-div">
                <input type="textfield" id="project-view-name" class="field ta"
                       value="<c:out value="${project.name}" />">
            </div>
        </td>
    </tr>
    <tr>
        <td>Описание:</td>
        <td>
            <div class="ta-div">
                <textarea rows="4" id="project-view-desc" class="field ta"><c:out
                        value="${project.description}"/></textarea>
            </div>
        </td>
    </tr>
    <tr>
        <td>Создан:</td>
        <td><c:out value="${project.created}"/></td>
    </tr>
    </tbody>
</table>

<div class="sep"></div>
<p class="title raw bld">Источники</p>
<span class="remark">Максимальный размер загружаемого файла ограничен до 10Мб.</span>

<form id="source-local-form" enctype="multipart/form-data">
    <input name="file" type="file" id="source-local" class="file ta">
    <input name="id" type="text" id="project-id" class="field ta invisible">
</form>
<progress id="source-local-progress"></progress>

<div class="sep"></div>

<c:if test="${count!=0}">
    <table class="list" width=100%>
        <colgroup span="4">
            <col span="1">
            <col span="1">
            <col span="1">
            <col span="1">
        </colgroup>
        <thead>
        <tr class="first">
            <td></td>
            <td>Название</td>
            <td>Статус</td>
            <td>MD5 хеш</td>
            <td>Загружен</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sources}" var="src">
            <tr>
                <td><input type="checkbox" name="id" value="<c:out value="${src.id}" />"></td>
                <td>
                    <div class="nowrap name"><c:out value="${src.name}"/></div>
                </td>
                <td>
                    <c:if test="${src.status==true}">
                        подключен
                    </c:if>
                    <c:if test="${src.status==false}">
                        отключен
                    </c:if>
                </td>
                <td>
                    <div class="nowrap desc"><c:out value="${src.md5}"/></div>
                </td>
                <td><c:out value="${src.uploaded}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${count==0}">
    <div class="remark">Нет подключенных источников.</div>
</c:if>