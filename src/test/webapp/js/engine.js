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
$(function initialization() {
    // tip-tip
    $('.tip').tipTip();
    // localStorage
    //    session hash -- session/project status
    localStorage['hash'] = "";
    //    data -- 
    localStorage['data'] = "";
    //    sync with server?
    localStorage['sync'] = "true";
});

//------------------------------------------------------------------------
// HTML GENERATION
//------------------------------------------------------------------------
function generateAside(items) {
    $('aside').html('');
    // Add links
    $.each(items, function () {
        if (this.id !== 'sep') {
            $('<a/>', {
                id: this.id,
                text: this.text
            }).appendTo('aside');
        } else {
            $('<div/>', {
                'class': this.id
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
// LOCAL STORAGE, SESSION AND GRAPHICS
//------------------------------------------------------------------------
var thclient = new function () {
    this.data = null;
    this.id = null;
    // NOTY =========================================
    this.noty = null;
    /*
     type = [alert, success, error, warning, information, confirm]
     */
    // Client interaction messages
    this.notify = function notify(type, text) {
        var n = noty({
            text: text,
            type: type,
            dismissQueue: true,
            layout: 'bottomRight',
            theme: 'default',
            timeout: 4000
        });
    };

    // Client background tasks messages
    this.thmsg = function thmsg(text) {
        if (thclient.noty !== null) {
            thclient.noty.close();
        }
        thclient.noty = noty({
            text: text,
            type: 'alert',
            dismissQueue: false,
            closeWith: ['button'],
            layout: 'bottomRight',
            theme: 'default'
        });
    };

    // PROJECT =========================================
    /** Steps:
     * 1. Check for data changes in localStorage
     * 2. Load data from storage, if necessary.
     * 3. Check state (project and session hash)
     * 4. Load data from server? if session hash have changed
     */
    this.sync = function sync() {
        if (localStorage['data'] !== JSON.stringify(thclient.data)) {
            thclient.loadData();
            thclient.id = null;
        }
        try {
            var hash;
            $.ajax({
                type: 'POST',
                url: '/texthistory/session/hash/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json'
            }).done(function (data) {
                    hash = data.hash;
                }).fail(function () {
                    throw 'Unable to sync data.';
                });
            if (localStorage['hash'] !== hash) {
                $.ajax({
                    type: 'POST',
                    url: '/texthistory/session/data/',
                    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                    dataType: 'json'
                }).done(function (data) {
                        thclient.data = data;
                        localStorage['data'] = JSON.stringify(thclient.data);
                        localStorage['hash'] = hash;
                    }).fail(function () {
                        throw 'Unable to sync data.';
                    });
                thclient.id = null;
            }
        } catch (e) {
            thclient.notify('warning', 'Unable to sync data.');
            console.warn(e);
        }
    };

    this.loadData = function loadData() {
        thclient.data = JSON.parse(localStorage['data']);
    } ;

    this.saveData = function saveData() {
        localStorage['data'] = JSON.stringify(thclient.data);
    };
};
//------------------------------------------------------------------------
// AJAX EVENTS HANDLERS
//------------------------------------------------------------------------
// TODO: Add GET requests. Add new object with functions and use predefined in events.
var thhandler = new function () {
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
                if (data.code === 0) {
                    window.location = '/texthistory/';
                } else {
                    thclient.notify('error', data.msg);
                }
            }).fail(function () {
                thclient.notify('error', 'Unable to reach the server.');
            }).always(function () {

            });
    };

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
                thclient.notify('error', 'Unable to reach the server.');
            });
    };

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
                thclient.notify('error', 'Unable to reach the server.');
            });
    };

    /** Open project */
    this.projectOpen = function projectOpen() {
        var id = $($('input[name=id]:checked').get(0)).val();

        if (id === undefined) {
            thclient.notify('warning', 'Проект не выбран.');
        } else {
            $.ajax({
                type: 'POST',
                url: '/texthistory/project/open/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json',
                data: {"id": id}
            }).done(function (data) {
                    if (data.code === 3) {
                        thclient.notify('success', data.msg);
                        $('#cur-project').html(data.data);
                    } else {
                        thclient.notify('warning', data.msg);
                    }
                }).fail(function () {
                    thclient.notify('error', 'Unable to process request.');
                });
        }
    };

    /** Delete project */
    this.projectDelete = function projectDelete() {
        var checked = $('input[name=id]:checked').get(0),
            id = $(checked).val(),
            tr = $(checked).parent().parent();

        if (id === undefined) {
            thclient.notify('warning', 'Проект не выбран.');
        } else {
            $.ajax({
                type: 'POST',
                url: '/texthistory/project/delete/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json',
                data: {"id": id}
            }).done(function (data) {
                    if (data.code === 0 || data.code === 3) {
                        thclient.notify('success', data.msg);
                        $(tr).remove();
                        if (data.code === 3) {
                            $('#cur-project').text('Проект:');
                        }
                    } else {
                        thclient.notify('warning', data.msg);
                    }
                }).fail(function () {
                    thclient.notify('error', 'Unable to process request.');
                });
        }
    };

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
                thclient.notify('error', 'Unable to reach the server.');
            });
    };

    /** Save new project */
    this.projectNewSave = function projectNewSave() {
        if ($('#project-new-name').val() === '') {
            thclient.notify('warning', 'Название проекта не может быть пустым.');
        } else {
            $.ajax({
                type: 'POST',
                url: '/texthistory/project/new/save/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json',
                data: {"name": $('#project-new-name').val(),
                    "desc": $('#project-new-desc').val()}
            }).done(function (data) {
                    if (data.code === 0) {
                        thclient.notify('success', data.msg);
                        $('#project-new-name').val('');
                        $('#project-new-desc').val('');
                    } else {
                        thclient.notify('warning', data.msg);
                    }
                }).fail(function () {
                    thclient.notify('error', 'Unable to process request.');
                });
        }
    };

    /** Clears new project form */
    this.projectNewClear = function projectNewClear() {
        $('#project-new-name').val('');
        $('#project-new-desc').val('');
    };

    // --- PROJECT :: VIEW ---
    /** Open project for view */
    this.projectView = function projectView() {
        var id = $($('input[name=id]:checked').get(0)).val();
        if (id === undefined) {
            thclient.notify('warning', 'Проект не выбран.');
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
                    thclient.notify('error', 'Unable to reach the server.');
                });
        }
    };

    /** Saves the project state */
    this.projectViewSave = function projectViewSave() {
        if ($('#project-add-name').val() === '') {
            thclient.notify('warning', 'Название проекта не может быть пустым.');
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
                    if (data.code === 0 || data.code === 3) {
                        thclient.notify('success', data.msg);
                        if (data.code === 3) {
                            $('#cur-project').html(data.data);
                        }
                    } else {
                        thclient.notify('warning', data.msg);
                    }
                }).fail(function () {
                    thclient.notify('error', 'Unable to process request.');
                });
        }
    };

    // Upload progress
    function progressHandlingFunction(e) {
        if (e.lengthComputable) {
            $('#source-local-progress').attr({value: e.loaded, max: e.total});
        }
    }

    /** Download sources */
        // TODO : Add drag n' drop
    this.projectViewSource = function projectViewSource() {
        var valid = true,
            length = $('#source-local')[0].files.length,
            message = "необходимо выбрать файлы.",
            file,
            i;
        if (length === 0) {
            valid = false;
        }
        for (i = 0; i < length; i++) {
            file = $('#source-local')[0].files[i];
            if (file.type !== 'text/plain') {
                valid = false;
                message = "File " + file.name + " has illegal type.";
                break;
            }
        }

        if (valid === false) {
            thclient.notify('warning', message);
        } else {

            var formData = new FormData($('#source-local-form')[0]),
                id = $('#project-view-id').text();
            $('#project-id').val(id);
            $.ajax({
                url: '/texthistory/project/' + id + '/upload/',  //server script to process data
                type: 'POST',
                xhr: function () {
                    var myXhr = $.ajaxSettings.xhr();
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
                    if (data.code === 3) {
                        thclient.notify('success', data.msg);
                    } else {
                        thclient.notify('warning', data.msg);
                    }
                }).fail(function () {
                    thclient.notify('error', 'Unable to process request.');
                });
        }
    };

    /** Removes source from project */
    this.projectViewSourceExclude = function projectViewSourceExclude() {
        var checked = $('input[name=id]:checked').get(0),
            id = $(checked).val(),
            tr = $(checked).parent().parent();

        if (id === undefined) {
            thclient.notify('warning', 'Source not selected.');
        } else {
            $.ajax({
                type: 'POST',
                url: '/texthistory/source/delete/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json',
                data: {"id": id}
            }).done(function (data) {
                    if (data.code === 0) {
                        thclient.notify('success', data.msg);
                        $(tr).remove();
                    } else {
                        thclient.notify('warning', data.msg);
                    }
                }).fail(function () {
                    thclient.notify('error', 'Unable to process request.');
                });
        }
    };

    /** Change status of the source */
    this.projectViewSourceStatus = function projectViewSourceStatus() {
        var checked = $('input[name=id]:checked').get(0),
            id = $(checked).val(),
            tr = $(checked).parent().parent();

        if (id === undefined) {
            thclient.notify('warning', 'Source not selected.');
        } else {
            $.ajax({
                type: 'POST',
                url: '/texthistory/source/status/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json',
                data: {"id": id}
            }).done(function (data) {
                    if (data.code === 3) {
                        thclient.notify('success', data.msg);
                        if (data.data == "true") {
                            $($(tr).children().get(2)).html("подключен");
                        } else {
                            $($(tr).children().get(2)).html("отключен");
                        }

                    } else {
                        thclient.notify('warning', data.msg);
                    }
                }).fail(function () {
                    thclient.notify('error', 'Unable to process request.');
                });
        }
    };

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
                thclient.notify('error', 'Unable to reach the server.');
            });
    };

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
                thclient.notify('error', 'Unable to reach the server.');
            });
    };

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
                thclient.notify('error', 'Unable to reach the server.');
            });
    };

    // --- CONTENT ANALYSIS :: KEY VIEW ---
    /** Content analysis key words view */
    this.caKeyView = function caKeyView() {
        var id = $($('input[name=id]:checked').get(0)).val();
        if (id == null) {
            thclient.notify('warning', 'Категория не выбрана.');
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
                    thclient.notify('error', 'Unable to reach the server.');
                });
        }
    };
};
//------------------------------------------------------------------------
// AJAX EVENTS
//------------------------------------------------------------------------
// --- Logout ---
$('#logout').click(thhandler.logout);

// --- HOME ---
$('#nav-hm').click(thhandler.home);

// --- PROJECT :: LIST ---
$('#nav-pm-l').click(thhandler.projectList);
// active
$(document).on('click', '#do-proj-list-open', thhandler.projectOpen);
// delete
$(document).on('click', '#do-proj-list-delete', thhandler.projectDelete);

// --- PROJECT :: NEW ---
$('#nav-pm-n').click(thhandler.projectNew);
// save
$(document).on('click', '#do-proj-new-save', thhandler.projectNewSave);
// clear
$(document).on('click', '#do-proj-new-clear', thhandler.projectNewClear);

// --- PROJECT :: VIEW ---
// list view
$(document).on('click', '#do-proj-list-view', thhandler.projectView);

$(document).on('click', '#do-proj-view-save', thhandler.projectViewSave);

// Download new sources
$(document).on('click', '#btn-source-local', thhandler.projectViewSource);

// exclude
$(document).on('click', '#do-proj-view-exclude', thhandler.projectViewSourceExclude);

// Source updating status
$(document).on('click', '#do-proj-view-status', thhandler.projectViewSourceStatus);

// --- CONTENT ANALYSIS :: STATISTICS ---
$('#nav-ca-s').click(thhandler.caStatistics);

// --- CONTENT ANALYSIS :: GRAPHICS ---
$('#nav-ca-g').click(thhandler.caGraphics);

// --- CONTENT ANALYSIS :: KEY LIST ---
$('#nav-ca-t').click(thhandler.caKeyList);

// --- CONTENT ANALYSIS :: KEY VIEW ---
$(document).on('click', '#do-cont-key-list-view', thhandler.caKeyView);

// --- User Inforamtion ---
$('#account').click(function () {
    $('#userinfo').toggleClass('hidden');
});

//------------------------------------------------------------------------
// AJAX EVENTS
//------------------------------------------------------------------------
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

// --- Controls ---
$('.dir').mouseenter(function () {
    showSubMenu(this);
});

$('nav').mouseleave(function () {
    hideSubMenu();
});

$('#nav-hm').mouseenter(function () {
    hideSubMenu();
});

$('#do-resize').click(function () {
    $('aside').toggleClass('hidden');
    if ($('aside').hasClass('hidden')) {
        $('#content').css('margin-left', 0);
    } else {
        $('#content').css('margin-left', 200);
    }
});

$('#do-sync').click(function () {

    thclient.notify('information', 'Sync with server is now disabled.');
});

// --- Tables Selection ---
$(document).on('dblclick', 'tr.cat', function (event) {
    var id = $($(this).children()[0]).html();
    $('.data-link-id-' + id).each(function (i, k) {
        $(k).toggleClass('hidden');
    });
});

$(document).on('click', 'tr.cat', function (event) {
    if ($(this).hasClass('selected')) {
        $('tr.cat.selected').removeClass('selected');
        thclient.id = null;
    } else {
        $('tr.cat.selected').removeClass('selected');
        thclient.id = $($(this).children()[0]).html();
        $(this).addClass('selected');
    }
});