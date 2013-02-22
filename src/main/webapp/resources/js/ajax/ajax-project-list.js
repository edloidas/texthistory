/**
 * ajax-project-list.js
 *
 * based on jQuery API
 */
// Project removing
$('#btn-project-delete').click(function () {
    var id = $($('input[name=id]:checked').get(0)).val();
    var tr = $($('input[name=id]:checked').get(0)).parent().parent();

    if (id == null) {
        notify('warning', 'Project not selected.');
    } else {
        $.ajax({
            type: 'POST',
            url: '/texthistory/project/delete/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'json',
            data: {"id": id},
            success: function (data) {
                if (data.code == 0 || data.code == 3) {
                    notify('success', data.msg);
                    //$($('input[name=id]:checked').get(0)).parent().parent().remove();
                    $(tr).remove();
                    if (data.code == 3) {
                        $('#cur-project').text('Проект:');
                    }
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

$('#btn-project-open').click(function () {
    var id = $($('input[name=id]:checked').get(0)).val();

    if (id == null) {
        notify('warning', 'Project not selected.');
    } else {
        $.ajax({
            type: 'POST',
            url: '/texthistory/project/open/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'json',
            data: {"id": id},
            success: function (data) {
                if (data.code == 3) {
                    notify('success', data.msg);
                    $('#cur-project').html(data.data);
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

$('#btn-project-view').click(function () {
    var id = $($('input[name=id]:checked').get(0)).val();

    if (id == null) {
        notify('warning', 'Project not selected.');
    } else {
        window.location = '/texthistory/project/view/' + id + '/';
    }
});

$("#actions").hide();

// select project
$('tr').click(function () {
    var checkbox = $($(this).children().get(0)).children().get(0);
    if ($(checkbox).prop("checked") == false) {
        $('input[name=id]:checked').each(function () {
            $(this).prop("checked", false)
        });
        $(checkbox).prop("checked", true);
        $("#actions").show();
    } else {
        $('input[name=id]:checked').each(function () {
            $(this).prop("checked", false)
        });
        $("#actions").hide();
    }
});