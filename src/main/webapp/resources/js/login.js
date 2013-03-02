/**
 * Login.js
 *
 * based on jQuery API
 *
 * Sets noty handler.
 */

//------------------------------------------------------------------------
// 'Noty' Notification
// type = [Alert, Success, Error, Warning, Information, Confirm]
//------------------------------------------------------------------------
function notify(type, text) {
    var n = noty({
        text: text,
        type: type,
        dismissQueue: true,
        layout: 'bottomRight',
        theme: 'default',
        timeout: 4000
    });
    console.log('noty: ' + n.options.id + ' ' + text);
}

function login() {
    if ($('#login').val() == '' || $('#password').val() == '') {
        if ($('#login').val() == '')
            notify('warning', 'Login should not empty.');
        if ($('#password').val() == '')
            notify('warning', 'Password should not be empty.');
    } else {
        $.ajax({
            type: 'POST',
            url: '/texthistory/login/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'json',
            data: {"login": $('#login').val(),
                "password": $('#password').val()}
        }).done(function (data) {
                if (data.code == 0) {
                    window.location = '/texthistory/home/';
                } else {
                    notify('error', data.msg);
                }
            }).fail(function () {
                notify('error', 'Unable to process request.');
            }).always(function () {

            });
    }
}

/** Detect Enter pressing to submit data to server without form. */
$(document).keypress(function (key) {
    if (key.keyCode == 13 && $(document.activeElement).is('input')) {
        login();
    }
});

$('#btn-login').click(login);

