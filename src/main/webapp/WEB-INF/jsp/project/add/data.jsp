<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<table class="manager" width=100%>
    <colgroup>
        <col span="1" width="100">
    </colgroup>
    <tbody>
    <tr>
        <td>Название:</td>
        <td><input type="textfield" id="project-new-name" class="field tf"></td>
    </tr>
    <tr>
        <td>Описание:</td>
        <td><textarea rows=4 id="project-new-desc" class="field tf"></textarea></td>
    </tr>
    </tbody>
</table>