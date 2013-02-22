/**
 * ajax-project-list.js
 *
 * based on jQuery API
 */
// Word update
$('.nav-cont-key-view').click(function () {
    var id = $('#key-view-id').text();

    if (id == null) {
        notify('warning', 'Meaningful word not selected.');
    } else {
        window.location = '/texthistory/content/key/view/' + id + '/';
    }
});

$("#actions").hide();

// select project
$('.mean tr').click(function () {
    if ($($(this).parent().parent()).hasClass('disabled')) {
        return;
    }

    $($(this).parent().parent()).addClass('disabled');
    //$('input[name=id]').each(function () {$(this).attr("disabled", true); });

    var checkbox = $($(this).children().get(0)).children().get(0);
    var meanId = $(checkbox).val();
    var keyId = $('#key-view-id').text();

    $.ajax({
        type: 'POST',
        url: '/texthistory/content/key/view/',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        dataType: 'json',
        data: {"keyId": keyId,
            "meanId": meanId},
        success: function (data) {
            if (data.code == 0 || data.code == 3) {
                notify('success', data.msg);
                if (data.data == "activated") {
                    $(checkbox).prop("checked", true);
                } else {
                    $(checkbox).prop("checked", false);
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

    //$('input[name=id]').each(function () {$(this).removeAttr("disabled"); });
    $($(this).parent().parent()).removeClass('disabled');
});