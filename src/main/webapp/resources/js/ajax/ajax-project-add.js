/**
 * ajax-project-add.js
 *
 * based on jQuery API
 */
$('#btn-project-add').click(function () {
    if ($('#project-add-name').val() == '') {
        notify('warning', 'Project name can not be empty.');
    } else {
        $.ajax({
            type: 'POST',
            url: '/texthistory/project/add/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'json',
            data: {"name": $('#project-add-name').val(),
                "desc": $('#project-add-desc').val()},
            success: function (data) {
                if (data.code == 0) {
                    notify('success', data.msg);
                    $('#project-add-name').val('');
                    $('#project-add-desc').val('');
                } else {
                    notify('warning', data.msg);
                }
            },
            error: function () {
                notify('error', 'Unable to process request.');
            },
            complete: function () {
            }
        });
    }
});

// CLear fields
$('#btn-project-clear').click(function () {
    $('#project-add-name').val('');
    $('#project-add-desc').val('');
});