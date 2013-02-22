/**
 * ajax-project-list.js
 *
 * based on jQuery API
 */
// Discourse word viewing
$('#btn-disc-view').click(function () {
    var id = $($('input[name=id]:checked').get(0)).val();

    if (id == null) {
        notify('warning', 'Discourse category not selected.');
    } else {
        window.location = '/texthistory/discource/view/' + id + '/';
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