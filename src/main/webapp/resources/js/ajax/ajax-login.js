/**
 * ajax-login.js
 *
 * based on jQuery API
 */
$('#btn-login').click(function () {
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
});