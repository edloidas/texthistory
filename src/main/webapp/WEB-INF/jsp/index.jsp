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
        <table>
            <tbody>
            <tr>
                <td>
                    <h3>Облачный сервис</h3>
                    <article>Texthistory является облачным сервисом, предоставляющим инструменты для анализа нарративных источников.</article>
                    <h3>Три в одном</h3>
                    <article>Серсис позволяет использовать интеграрованные инструменты для контент-, дискурс- и психо-анализа. Загружайте источники, создавайте проекты и сохраняйте результаты проведенного анализа.</article>
                    <br/><a id="demo">Узнать больше...</a>
                </td>
                <td class="right">
                    <h2>Авторизация</h2>
                    <label for="loginField">Логин</label>
                    <input type="textfield" id="loginField" class="field"/>

                    <label for="passwordField">Пароль</label>
                    <input type="password" id="passwordField" class="field"/>

                    <input type="button" id="btn-login" class="btn apply" value="Войти"/>
                </td>
            </tr>
            </tbody>
        </table>

        <div id="support" class="invisible">Ваш браузер не поддерживает некоторые возможности, необходимые для работы с сервисом. Обновите существующий браузер или загрузите один из предложенных ниже.
            <ul id="browsers">
                <li><a class="logo" href="http://www.google.com/chrome/" title="Chrome"><img src="<c:url value="/resources/img/icons/chrome.png" />" alt="Chrome"/></a></li>
                <li><a class="logo" href="http://www.mozilla.org/firefox/new/" title="Firefox"><img src="<c:url value="/resources/img/icons/firefox.png" />" alt="Firefox"/></a></li>
                <li><a class="logo" href="http://www.opera.com/computer/" title="Opera"><img src="<c:url value="/resources/img/icons/opera.png" />" alt="Opera"/></a></li>
            </ul>
        </div>
    </div>
    <footer>© 2012 Texthistory by Edloidas. edloidas@gmail.com | priborovich@gmail.com</footer>
    <script src="<c:url value="/resources/js/login.js" />"></script>
</body>
</html>
