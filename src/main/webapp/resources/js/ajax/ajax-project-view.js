/**
 * ajax-project-view.js
 *
 * based on jQuery API
 */
$('.nav-project-view').click(function () {
    var id = $('#project-view-id').text();

    if (id == null) {
        notify('warning', 'Project not selected.');
    } else {
        window.location = '/texthistory/project/view/' + id + '/';
    }
});

$('#btn-project-save').click(function () {
    if ($('#project-add-name').val() == '') {
        notify('warning', 'Project name can not be empty.');
    } else {
        $.ajax({
            type: 'POST',
            url: '/texthistory/project/update/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'json',
            data: {"id": $('#project-view-id').text(),
                "name": $('#project-view-name').val(),
                "desc": $('#project-view-desc').val()},
            success: function (data) {
                if (data.code == 0 || data.code == 3) {
                    notify('success', data.msg);
                    if (data.code == 3) {
                        $('#cur-project').html(data.data);
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

$('#btn-source-local').click(function () {
    var valid = true;
    var length = $('#source-local')[0].files.length;
    var file;
    var message = "Files should be selected first.";
    if (length == 0) {
        valid = false;
    }
    for (var i = 0; i < length; i++) {
        file = $('#source-local')[0].files[i];
        if (file.type != 'text/plain') {
            valid = false;
            message = "File " + file.name + " has illegal type.";
            break;
        }
    }

    if (valid == false) {
        notify('warning', message);
    } else {

        var formData = new FormData($('#source-local-form')[0]);
        $('#project-id').val($('#project-view-id').text());
        var id = $('#project-view-id').text();
        $.ajax({
            url: '/texthistory/project/' + id + '/upload/',  //server script to process data
            type: 'POST',
            xhr: function () {
                myXhr = $.ajaxSettings.xhr();
                if (myXhr.upload) { // check if upload property exists
                    myXhr.upload.addEventListener('progress', progressHandlingFunction, false); // for handling the progress of the upload
                }
                return myXhr;
            },
            // Form data
            data: formData,
            // To be able to retrieve json response
            dataType: 'json',
            // Options to tell JQuery not to process data or worry about content-type
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                if (data.code == 3) {
                    notify('success', data.msg);
                } else {
                    notify('warning', data.msg);
                }
            },
            error: function () {
                notify('error', 'Unable to process request.');
            }
        });
    }
});

function progressHandlingFunction(e) {
    if (e.lengthComputable) {
        $('#source-local-progress').attr({value: e.loaded, max: e.total});
    }
}

// Source removing
$('#btn-source-delete').click(function () {
    var id = $($('input[name=id]:checked').get(0)).val();
    var tr = $($('input[name=id]:checked').get(0)).parent().parent();

    if (id == null) {
        notify('warning', 'Source not selected.');
    } else {
        $.ajax({
            type: 'POST',
            url: '/texthistory/source/delete/',
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

// Source updating status
$('#btn-source-status').click(function () {
    var id = $($('input[name=id]:checked').get(0)).val();
    var tr = $($('input[name=id]:checked').get(0)).parent().parent();

    if (id == null) {
        notify('warning', 'Source not selected.');
    } else {
        $.ajax({
            type: 'POST',
            url: '/texthistory/source/status/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'json',
            data: {"id": id},
            success: function (data) {
                if (data.code == 3) {
                    notify('success', data.msg);
                    if (data.data == "true") {
                        $($(tr).children().get(2)).html("подключен");
                    } else {
                        $($(tr).children().get(2)).html("отключен");
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

$('#actions').hide();
// select source
$('tr').click(function () {
    var checkbox = $($(this).children().get(0)).children().get(0);
    if ($(checkbox).prop("checked") == false) {
        $('input[name=id]:checked').each(function () {
            $(this).prop("checked", false)
        });
        $(checkbox).prop("checked", true);
        $('#actions').show();
    } else {
        $('input[name=id]:checked').each(function () {
            $(this).prop("checked", false)
        });
        $('#actions').hide();
    }
});