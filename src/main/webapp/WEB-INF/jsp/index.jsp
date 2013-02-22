<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Texthistory</title>
    <link rel="shortcut icon" href="<c:url value="/resources/img/favicon.png" />" type="image/png">
    <link rel="stylesheet" href="<c:url value="/resources/css/normalize.css" />" type="text/css">
    <link rel="stylesheet" href="<c:url value="/resources/css/login.css" />" type="text/css">
    <script src="<c:url value="/resources/js/jquery-1.9.0.min.js" />"></script>
    <!-- Noty -->
    <script src="<c:url value="/resources/js/noty/jquery.noty.js" />"></script>
    <!-- Customization -->
    <script src="<c:url value="/resources/js/noty/layouts/bottomRight.js" />"></script>
    <script src="<c:url value="/resources/js/noty/themes/default.js" />"></script>
    <!-- Resizing -->
    <!--[if lt IE 9]>
    <script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
</head>

<body>
<div id="box">
    <div id="header" class="">Добро пожаловать в <span class="title">Texthistory</span></div>

    <div id="login-form" class="column right">
        <h2>Авторизация</h2>
        <label for="login">Логин</label>
        <input type="textfield" id="login" class="field"/>

        <label for="password">Пароль</label>
        <input type="password" id="password" class="field"/>

        <input type="button" id="btn-login" class="btn apply" value="Войти"/>
    </div>
    <div class="column">
        <h3>Облачный сервис</h3>
        <article>Texthistory является облачным сервисом, предоставляющим инструменты для анализа нарративных
            источников.
        </article>
        <h3>Три в одном</h3>
        <article>Серсис позволяет использовать интеграрованные инструменты для контент-, дискурс- и психо-анализа.
            Загружайте источники, создавайте проекты и сохраняйте результаты проведенного анализа.
        </article>
        <a id="demo">Узнать больше...</a>
    </div>
</div>
<footer>© 2012 Texthistory by Edloidas. edloidas@gmail.com | priborovich@gmail.com</footer>
<script src="<c:url value="/resources/js/login.js" />"></script>
</body>
</html>
