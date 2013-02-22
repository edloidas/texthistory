/**
 * ajax-project-list.js
 *
 * based on jQuery API
 */
// Key word viewing
$('#btn-key-view').click(function () {
    var id = $($('input[name=id]:checked').get(0)).val();

    if (id == null) {
        notify('warning', 'Meaningful word not selected.');
    } else {
        window.location = '/texthistory/content/key/view/' + id + '/';
    }
});

// Key word deleting
$('#btn-key-add').click(function () {
    var name = $('#key-word-name').val();

    if (name == '') {
        notify('warning', 'Name not entered.');
    } else {
        $.ajax({
            type: 'POST',
            url: '/texthistory/content/key/add/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'json',
            data: {"name": name},
            success: function (data) {
                if (data.code == 0) {
                    notify('success', data.msg);
                    window.location = '/texthistory/content/key/list/';
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

// Key word deleting
$('#btn-key-delete').click(function () {
    var id = $($('input[name=id]:checked').get(0)).val();
    var tr = $($('input[name=id]:checked').get(0)).parent().parent();

    if (id == null) {
        notify('warning', 'Category not selected.');
    } else {
        $.ajax({
            type: 'POST',
            url: '/texthistory/content/key/delete/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'json',
            data: {"id": id},
            success: function (data) {
                if (data.code == 0) {
                    notify('success', data.msg);
                    $(tr).remove();
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