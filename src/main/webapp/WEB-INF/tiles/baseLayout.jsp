<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>
        <tiles:insertAttribute name="title"/>
    </title>
    <tiles:insertAttribute name="head"/>
</head>

<body>
<nav>
    <tiles:insertAttribute name="nav"/>
</nav>
<div id="toolset">
    <tiles:insertAttribute name="toolset"/>
</div>
<aside>
    <tiles:insertAttribute name="aside"/>
</aside>
<div id="content" class="resized">
    <!--<div id="toolset"></div>-->
    <div id="data">
        <tiles:insertAttribute name="data"/>
    </div>
</div>
<div id="userinfo" class="hidden">
    <tiles:insertAttribute name="userinfo"/>
</div>

<tiles:insertAttribute name="include"/>
</body>
</html>
