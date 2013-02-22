/**
 * ajax-project-list.js
 *
 * based on jQuery API
 */
// Word update
$('.nav-psycho-view').click(function () {
    var id = $('#psycho-view-id').text();

    if (id == null) {
        notify('warning', 'Discourse category not found.');
    } else {
        window.location = '/texthistory/psycho/view/' + id + '/';
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