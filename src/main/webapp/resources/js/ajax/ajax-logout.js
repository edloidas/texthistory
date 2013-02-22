$('#logout').click(function () {
    $.ajax({
        type: 'POST',
        url: '/texthistory/logout/',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        dataType: 'json'
    }).done(function (data) {
            if (data.code == 0) {
                window.location = '/texthistory/';
            } else {
                notify('error', data.msg);
            }
        }).fail(function () {
            notify('error', 'Unable to reach the server.');
        }).always(function () {

        });
});