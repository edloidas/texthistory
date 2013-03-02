/**
 * Navigation.js
 *
 * based on jQuery API
 *
 * Set tooltips and div-links for navigation menu.
 * 'window.location.replace(url)' replaces the current location in the address
 *   bar by a new one. The page that was calling the function, won't be
 *   included in the browser history.
 * 'window.location = url' redirects to the new location. The back button in
 *   browser would point to the original page.
 */
//------------------------------------------------------------------------
// Initialization
//------------------------------------------------------------------------
// --- Tooltips ---
$(function () {
    $(".tip").tipTip();
});

// --- Noty ---
/*
 type = [Alert, Success, Error, Warning, Information, Confirm]
 */
function notify(type, text) {
    var n = noty({
        text: text,
        type: type,
        dismissQueue: true,
        layout: 'bottomRight',
        theme: 'default',
        timeout: 4000
    });
}

// --- Toolset ---
$('#do-resize').click(function () {
    if ($('#content').hasClass('resized')) {
        $('aside').hide();
        $('#content').removeClass('resized');
    } else {
        $('aside').show();
        $('#content').addClass('resized');
    }
});

// --- User Inforamtion ---
$('#account').click(function () {
    if ($('#userinfo').hasClass('hidden')) {
        $('#userinfo').removeClass('hidden');
    } else {
        $('#userinfo').addClass('hidden');
    }
});

// --- Navigation ---
$('.dir').mouseenter(function () {
    showSubMenu(this);
});

$('nav').mouseleave(function () {
    hideSubMenu();
});

$('#nav-hm').mouseenter(function () {
    hideSubMenu();
});


function showSubMenu(elem) {
    $('.dir').removeClass('active');
    $('.subdir').addClass('hidden');
    $(elem).addClass('active');
    $('.' + $(elem)[0].id).removeClass('hidden');
}

function hideSubMenu() {
    $('.dir').removeClass('active');
    $('.subdir').addClass('hidden');
    $('#newline').removeClass('.newline');
}

// --- Tables Selection ---
$(document).on('click', 'tr', function () {
    var checkbox = $($(this).children().get(0)).children().get(0);
    if ($(checkbox).prop("checked") == false) {
        $('input[name=id]:checked').each(function () {
            $(this).prop("checked", false)
        });
        $(checkbox).prop("checked", true)
    } else {
        $('input[name=id]:checked').each(function () {
            $(this).prop("checked", false)
        });
    }
});

//------------------------------------------------------------------------
// HTML GENERATION
//------------------------------------------------------------------------
function generateAside(items) {
    $('aside').html('');
    // Add links
    $.each(items, function () {
        if (this.id != 'sep') {
            $('<a/>', {
                id: this.id,
                text: this.text
            }).appendTo('aside');
        } else {
            $('<div/>', {
                class: this.id
            }).appendTo('aside');
        }
    });
}

function dataShowLoading() {
    $('#data').html('');
    $('<div/>', {
        id: 'loading',
        text: 'Loading, please wait...'
    }).appendTo('#data');
}

/*
 position    string with current position in navidation
 example: 'Project // New project'
 asideItems  array list of aside elements
 example: [{id:'id1',text:'text1'},{id:'id2',text:'text2'}]
 data        html, that will be placed into '#content'
 */
function changePage(position, asideItems, data) {
    $('#position').text(position);
    generateAside(asideItems);
    $('#content').html(data);
}

//------------------------------------------------------------------------
// LOCAL STORAGE AND SESSION
//------------------------------------------------------------------------
var storage = new function () {
    // TODO: Add localStorage support and save state and session.
};
//------------------------------------------------------------------------
// AJAX EVENTS HANDLERS
//------------------------------------------------------------------------
// TODO: Add GET requests. Add new object with functions and use predefined in events.
var handler = new function () {
    //==================================================
    // LOGOUT ==========================================
    /** Handles logout POST request. */
    this.logout = function logout() {
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
    }

    // HOME ============================================
    /** Navigates to Home page */
    this.home = function home() {
        dataShowLoading();
        $.ajax({
            type: 'GET',
            url: '/texthistory/home/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'html'
        }).done(function (data) {
                changePage('Главная',
                    [
                        {id: 'do-home-sort-data', text: 'Сортировать по дате'},
                        {id: 'do-home-sort-rating', text: 'Сортировать по рейтингу'},
                        {id: 'sep', text: ''}
                    ],
                    data);
            }).fail(function () {
                notify('error', 'Unable to reach the server.');
            });
    }

    // PROJECT =========================================
    /** List of projects */
    this.projectList = function projectList() {
        dataShowLoading();
        $.ajax({
            type: 'GET',
            url: '/texthistory/project/list/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'html'
        }).done(function (data) {
                changePage('Список проектов',
                    [
                        {id: 'do-proj-list-open', text: 'Сделать активным'},
                        {id: 'do-proj-list-view', text: 'Просмотреть'},
                        {id: 'do-proj-list-delete', text: 'Удалить'},
                        {id: 'sep', text: ''}
                    ],
                    data);
            }).fail(function () {
                notify('error', 'Unable to reach the server.');
            });
    }

    /** Open project */
    this.projectOpen = function projectOpen() {
        var id = $($('input[name=id]:checked').get(0)).val();

        if (id == null) {
            notify('warning', 'Проект не выбран.');
        } else {
            $.ajax({
                type: 'POST',
                url: '/texthistory/project/open/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json',
                data: {"id": id}
            }).done(function (data) {
                    if (data.code == 3) {
                        notify('success', data.msg);
                        $('#cur-project').html(data.data);
                    } else {
                        notify('warning', data.msg);
                    }
                }).fail(function () {
                    notify('error', 'Unable to process request.');
                });
        }
    }

    /** Delete project */
    this.projectDelete = function projectDelete() {
        var checked = $('input[name=id]:checked').get(0);
        var id = $(checked).val();
        var tr = $(checked).parent().parent();

        if (id == null) {
            notify('warning', 'Проект не выбран.');
        } else {
            $.ajax({
                type: 'POST',
                url: '/texthistory/project/delete/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json',
                data: {"id": id}
            }).done(function (data) {
                    if (data.code == 0 || data.code == 3) {
                        notify('success', data.msg);
                        $(tr).remove();
                        if (data.code == 3) {
                            $('#cur-project').text('Проект:');
                        }
                    } else {
                        notify('warning', data.msg);
                    }
                }).fail(function () {
                    notify('error', 'Unable to process request.');
                });
        }
    }

    // --- PROJECT :: NEW ---
    /** Navigate to new project creation */
    this.projectNew = function projectNew() {
        dataShowLoading();
        $.ajax({
            type: 'GET',
            url: '/texthistory/project/new/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'html'
        }).done(function (data) {
                changePage('Новый проект',
                    [
                        {id: 'do-proj-new-clear', text: 'Очистить данные'},
                        {id: 'sep', text: ''},
                        {id: 'do-proj-new-save', text: 'Сохранить'},
                        {id: 'sep', text: ''}
                    ],
                    data);
            }).fail(function () {
                notify('error', 'Unable to reach the server.');
            });
    }

    /** Save new project */
    this.projectNewSave = function projectNewSave() {
        if ($('#project-new-name').val() == '') {
            notify('warning', 'Название проекта не может быть пустым.');
        } else {
            $.ajax({
                type: 'POST',
                url: '/texthistory/project/new/save/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json',
                data: {"name": $('#project-new-name').val(),
                    "desc": $('#project-new-desc').val()}
            }).done(function (data) {
                    if (data.code == 0) {
                        notify('success', data.msg);
                        $('#project-new-name').val('');
                        $('#project-new-desc').val('');
                    } else {
                        notify('warning', data.msg);
                    }
                }).fail(function () {
                    notify('error', 'Unable to process request.');
                });
        }
    }

    /** Clears new project form */
    this.projectNewClear = function projectNewClear() {
        $('#project-new-name').val('');
        $('#project-new-desc').val('');
    }

    // --- PROJECT :: VIEW ---
    /** Open project for view */
    this.projectView = function projectView() {
        var id = $($('input[name=id]:checked').get(0)).val();
        if (id == null) {
            notify('warning', 'Проект не выбран.');
        } else {
            dataShowLoading();
            $.ajax({
                type: 'GET',
                url: '/texthistory/project/view/' + id + '/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'html'
            }).done(function (data) {
                    changePage('Просмотр проекта',
                        [
                            {id: 'do-proj-view-exclude', text: 'Сделать активным'},
                            {id: 'do-proj-view-status', text: 'Изменить статус'},
                            {id: 'sep', text: ''},
                            {id: 'do-proj-view-save', text: 'Сохранить'},
                            {id: 'sep', text: ''}
                        ],
                        data);
                }).fail(function () {
                    notify('error', 'Unable to reach the server.');
                });
        }
    }

    /** Saves the project state */
    this.projectViewSave = function projectViewSave() {
        if ($('#project-add-name').val() == '') {
            notify('warning', 'Название проекта не может быть пустым.');
        } else {
            $.ajax({
                type: 'POST',
                url: '/texthistory/project/update/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json',
                data: {"id": $('#project-view-id').text(),
                    "name": $('#project-view-name').val(),
                    "desc": $('#project-view-desc').val()}
            }).done(function (data) {
                    if (data.code == 0 || data.code == 3) {
                        notify('success', data.msg);
                        if (data.code == 3) {
                            $('#cur-project').html(data.data);
                        }
                    } else {
                        notify('warning', data.msg);
                    }
                }).fail(function () {
                    notify('error', 'Unable to process request.');
                });
        }
    }

    // Upload progress
    function progressHandlingFunction(e) {
        if (e.lengthComputable) {
            $('#source-local-progress').attr({value: e.loaded, max: e.total});
        }
    }

    /** Download sources */
    // TODO : Add drag n' drop
    this.projectViewSource = function projectViewSource() {
        var valid = true;
        var length = $('#source-local')[0].files.length;
        var file;
        var message = "необходимо выбрать файлы.";
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
                data: formData,
                dataType: 'json',
                cache: false,
                contentType: false,
                processData: false
            }).done(function (data) {
                    if (data.code == 3) {
                        notify('success', data.msg);
                    } else {
                        notify('warning', data.msg);
                    }
                }).fail(function () {
                    notify('error', 'Unable to process request.');
                });
        }
    }

    /** Removes source from project */
    this.projectViewSourceExclude = function projectViewSourceExclude() {
        var checked = $('input[name=id]:checked').get(0);
        var id = $(checked).val();
        var tr = $(checked).parent().parent();

        if (id == null) {
            notify('warning', 'Source not selected.');
        } else {
            $.ajax({
                type: 'POST',
                url: '/texthistory/source/delete/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json',
                data: {"id": id}
            }).done(function (data) {
                    if (data.code == 0) {
                        notify('success', data.msg);
                        $(tr).remove();
                    } else {
                        notify('warning', data.msg);
                    }
                }).fail(function () {
                    notify('error', 'Unable to process request.');
                });
        }
    }

    /** Change status of the source */
    this.projectViewSourceStatus = function projectViewSourceStatus() {
        var checked = $('input[name=id]:checked').get(0);
        var id = $(checked).val();
        var tr = $(checked).parent().parent();

        if (id == null) {
            notify('warning', 'Source not selected.');
        } else {
            $.ajax({
                type: 'POST',
                url: '/texthistory/source/status/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json',
                data: {"id": id}
            }).done(function (data) {
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
                }).fail(function () {
                    notify('error', 'Unable to process request.');
                });
        }
    }

    // --- CONTENT ANALYSIS :: STATISTICS ---
    /** Content analysis statistics */
    this.caStatistics = function caStatistics() {
        dataShowLoading();
        $.ajax({
            type: 'GET',
            url: '/texthistory/content/statistic/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'html'
        }).done(function (data) {
                changePage('Статистика данных',
                    [
                        {id: 'sep', text: ''}
                    ],
                    data);
            }).fail(function () {
                notify('error', 'Unable to reach the server.');
            });
    }

    /** Content analysis graphics */
    this.caGraphics = function caGraphics() {
        dataShowLoading();
        $.ajax({
            type: 'GET',
            url: '/texthistory/content/graph/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'html'
        }).done(function (data) {
                changePage('Графики распределенности',
                    [
                        {id: 'sep', text: ''}
                    ],
                    data);
            }).fail(function () {
                notify('error', 'Unable to reach the server.');
            });
    }

    // --- CONTENT ANALYSIS :: KEY LIST ---
    /** Content analysis key words list */
    this.caKeyList = function caKeyList() {
        dataShowLoading();
        $.ajax({
            type: 'GET',
            url: '/texthistory/content/key/list/',
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
            dataType: 'html'
        }).done(function (data) {
                changePage('Ключевые слова/категории',
                    [
                        {id: 'do-cont-key-list-view', text: 'Просмотреть'},
                        {id: 'sep', text: ''}
                    ],
                    data);
            }).fail(function () {
                notify('error', 'Unable to reach the server.');
            });
    }

    // --- CONTENT ANALYSIS :: KEY VIEW ---
    /** Content analysis key words view */
    this.caKeyView = function caKeyView() {
        var id = $($('input[name=id]:checked').get(0)).val();
        if (id == null) {
            notify('warning', 'Категория не выбрана.');
        } else {
            dataShowLoading();
            $.ajax({
                type: 'GET',
                url: '/texthistory/content/key/view/' + id + '/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'html'
            }).done(function (data) {
                    changePage('Просмотр категории',
                        [
                            {id: 'do-cat-view-add', text: 'Добавить в категорию'},
                            {id: 'do-cat-view-split', text: 'Расформировать категорию'},
                            {id: 'sep', text: ''}
                        ],
                        data);
                }).fail(function () {
                    notify('error', 'Unable to reach the server.');
                });
        }
    }
};
//------------------------------------------------------------------------
// AJAX EVENTS
//------------------------------------------------------------------------
// --- Logout ---
$('#logout').click(handler.logout);

// --- HOME ---
$('#nav-hm').click(handler.home);

// --- PROJECT :: LIST ---
$('#nav-pm-l').click(handler.projectList);
// active
$(document).on('click', '#do-proj-list-open', handler.projectOpen);
// delete
$(document).on('click', '#do-proj-list-delete', handler.projectDelete);

// --- PROJECT :: NEW ---
$('#nav-pm-n').click(handler.projectNew);
// save
$(document).on('click', '#do-proj-new-save', handler.projectNewSave);
// clear
$(document).on('click', '#do-proj-new-clear', handler.projectNewClear);

// --- PROJECT :: VIEW ---
// list view
$(document).on('click', '#do-proj-list-view', handler.projectView);

$(document).on('click', '#do-proj-view-save', handler.projectViewSave);

// Download new sources
$(document).on('click', '#btn-source-local', handler.projectViewSource);

// exclude
$(document).on('click', '#do-proj-view-exclude', handler.projectViewSourceExclude);

// Source updating status
$(document).on('click', '#do-proj-view-status', handler.projectViewSourceStatus);

// --- CONTENT ANALYSIS :: STATISTICS ---
$('#nav-ca-s').click(handler.caStatistics);

// --- CONTENT ANALYSIS :: GRAPHICS ---
$('#nav-ca-g').click(handler.caGraphics);

// --- CONTENT ANALYSIS :: KEY LIST ---
$('#nav-ca-t').click(handler.caKeyList);

// --- CONTENT ANALYSIS :: KEY VIEW ---
$(document).on('click', '#do-cont-key-list-view', handler.caKeyView);

