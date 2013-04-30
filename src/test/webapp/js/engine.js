/**
 * Engine.js
 *
 * Requirements:
 * - jQuery
 * - Rapahael & gRaphael
 * - Noty
 * - D3
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
    if (localStorage['hash'] === undefined) { // only for first time use
        localStorage['hash'] = "";
    }
    //    data --
    if (localStorage['data'] === undefined) { // only for first time use
        localStorage['data'] = "";
    }
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
    $('#data').html(data);
}

//------------------------------------------------------------------------
// TOOLSET WITH EXTERNAL FUNCTIONS
//------------------------------------------------------------------------
function Toolset() {
    // MD5 IMPLEMENTATION ==============================
    /**
     * Implementation of md5 hash function for javascript by Joseph's Myers
     * See http://www.myersdaily.org/joseph/javascript/md5-text.html
     */
    function md5cycle(x, k) {
        var a = x[0], b = x[1], c = x[2], d = x[3];

        a = ff(a, b, c, d, k[0], 7, -680876936);
        d = ff(d, a, b, c, k[1], 12, -389564586);
        c = ff(c, d, a, b, k[2], 17, 606105819);
        b = ff(b, c, d, a, k[3], 22, -1044525330);
        a = ff(a, b, c, d, k[4], 7, -176418897);
        d = ff(d, a, b, c, k[5], 12, 1200080426);
        c = ff(c, d, a, b, k[6], 17, -1473231341);
        b = ff(b, c, d, a, k[7], 22, -45705983);
        a = ff(a, b, c, d, k[8], 7, 1770035416);
        d = ff(d, a, b, c, k[9], 12, -1958414417);
        c = ff(c, d, a, b, k[10], 17, -42063);
        b = ff(b, c, d, a, k[11], 22, -1990404162);
        a = ff(a, b, c, d, k[12], 7, 1804603682);
        d = ff(d, a, b, c, k[13], 12, -40341101);
        c = ff(c, d, a, b, k[14], 17, -1502002290);
        b = ff(b, c, d, a, k[15], 22, 1236535329);

        a = gg(a, b, c, d, k[1], 5, -165796510);
        d = gg(d, a, b, c, k[6], 9, -1069501632);
        c = gg(c, d, a, b, k[11], 14, 643717713);
        b = gg(b, c, d, a, k[0], 20, -373897302);
        a = gg(a, b, c, d, k[5], 5, -701558691);
        d = gg(d, a, b, c, k[10], 9, 38016083);
        c = gg(c, d, a, b, k[15], 14, -660478335);
        b = gg(b, c, d, a, k[4], 20, -405537848);
        a = gg(a, b, c, d, k[9], 5, 568446438);
        d = gg(d, a, b, c, k[14], 9, -1019803690);
        c = gg(c, d, a, b, k[3], 14, -187363961);
        b = gg(b, c, d, a, k[8], 20, 1163531501);
        a = gg(a, b, c, d, k[13], 5, -1444681467);
        d = gg(d, a, b, c, k[2], 9, -51403784);
        c = gg(c, d, a, b, k[7], 14, 1735328473);
        b = gg(b, c, d, a, k[12], 20, -1926607734);

        a = hh(a, b, c, d, k[5], 4, -378558);
        d = hh(d, a, b, c, k[8], 11, -2022574463);
        c = hh(c, d, a, b, k[11], 16, 1839030562);
        b = hh(b, c, d, a, k[14], 23, -35309556);
        a = hh(a, b, c, d, k[1], 4, -1530992060);
        d = hh(d, a, b, c, k[4], 11, 1272893353);
        c = hh(c, d, a, b, k[7], 16, -155497632);
        b = hh(b, c, d, a, k[10], 23, -1094730640);
        a = hh(a, b, c, d, k[13], 4, 681279174);
        d = hh(d, a, b, c, k[0], 11, -358537222);
        c = hh(c, d, a, b, k[3], 16, -722521979);
        b = hh(b, c, d, a, k[6], 23, 76029189);
        a = hh(a, b, c, d, k[9], 4, -640364487);
        d = hh(d, a, b, c, k[12], 11, -421815835);
        c = hh(c, d, a, b, k[15], 16, 530742520);
        b = hh(b, c, d, a, k[2], 23, -995338651);

        a = ii(a, b, c, d, k[0], 6, -198630844);
        d = ii(d, a, b, c, k[7], 10, 1126891415);
        c = ii(c, d, a, b, k[14], 15, -1416354905);
        b = ii(b, c, d, a, k[5], 21, -57434055);
        a = ii(a, b, c, d, k[12], 6, 1700485571);
        d = ii(d, a, b, c, k[3], 10, -1894986606);
        c = ii(c, d, a, b, k[10], 15, -1051523);
        b = ii(b, c, d, a, k[1], 21, -2054922799);
        a = ii(a, b, c, d, k[8], 6, 1873313359);
        d = ii(d, a, b, c, k[15], 10, -30611744);
        c = ii(c, d, a, b, k[6], 15, -1560198380);
        b = ii(b, c, d, a, k[13], 21, 1309151649);
        a = ii(a, b, c, d, k[4], 6, -145523070);
        d = ii(d, a, b, c, k[11], 10, -1120210379);
        c = ii(c, d, a, b, k[2], 15, 718787259);
        b = ii(b, c, d, a, k[9], 21, -343485551);

        x[0] = add32(a, x[0]);
        x[1] = add32(b, x[1]);
        x[2] = add32(c, x[2]);
        x[3] = add32(d, x[3]);

    }

    function cmn(q, a, b, x, s, t) {
        a = add32(add32(a, q), add32(x, t));
        return add32((a << s) | (a >>> (32 - s)), b);
    }

    function ff(a, b, c, d, x, s, t) {
        return cmn((b & c) | ((~b) & d), a, b, x, s, t);
    }

    function gg(a, b, c, d, x, s, t) {
        return cmn((b & d) | (c & (~d)), a, b, x, s, t);
    }

    function hh(a, b, c, d, x, s, t) {
        return cmn(b ^ c ^ d, a, b, x, s, t);
    }

    function ii(a, b, c, d, x, s, t) {
        return cmn(c ^ (b | (~d)), a, b, x, s, t);
    }

    function md51(s) {
        txt = '';
        var n = s.length,
            state = [1732584193, -271733879, -1732584194, 271733878], i;
        for (i = 64; i <= s.length; i += 64) {
            md5cycle(state, md5blk(s.substring(i - 64, i)));
        }
        s = s.substring(i - 64);
        var tail = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
        for (i = 0; i < s.length; i++)
            tail[i >> 2] |= s.charCodeAt(i) << ((i % 4) << 3);
        tail[i >> 2] |= 0x80 << ((i % 4) << 3);
        if (i > 55) {
            md5cycle(state, tail);
            for (i = 0; i < 16; i++) tail[i] = 0;
        }
        tail[14] = n * 8;
        md5cycle(state, tail);
        return state;
    }

    /* there needs to be support for Unicode here, unless we pretend that we can redefine the MD-5
     * algorithm for multi-byte characters (perhaps by adding every four 16-bit characters and
     * shortening the sum to 32 bits). Otherwise I suggest performing MD-5 as if every character
     * was two bytes--e.g., 0040 0025 = @%--but then how will an ordinary MD-5 sum be matched?
     * There is no way to standardize text to something like UTF-8 before transformation; speed cost is
     * utterly prohibitive. The JavaScript standard itself needs to look at this: it should start
     * providing access to strings as preformed UTF-8 8-bit unsigned value arrays.
     */
    function md5blk(s) { /* I figured global was faster.   */
        var md5blks = [], i;
        /* Andy King said do it this way. */
        for (i = 0; i < 64; i += 4) {
            md5blks[i >> 2] = s.charCodeAt(i)
                + (s.charCodeAt(i + 1) << 8)
                + (s.charCodeAt(i + 2) << 16)
                + (s.charCodeAt(i + 3) << 24);
        }
        return md5blks;
    }

    var hex_chr = '0123456789abcdef'.split('');

    function rhex(n) {
        var s = '', j = 0;
        for (; j < 4; j++)
            s += hex_chr[(n >> (j * 8 + 4)) & 0x0F]
                + hex_chr[(n >> (j * 8)) & 0x0F];
        return s;
    }

    function hex(x) {
        for (var i = 0; i < x.length; i++)
            x[i] = rhex(x[i]);
        return x.join('');
    }

    this.md5 = function md5(s) {
        return hex(md51(s));
    };

    /* this function is much faster,
     so if possible we use it. Some IEs
     are the only ones I know of that
     need the idiotic second function,
     generated by an if clause.  */

    function add32(a, b) {
        return (a + b) & 0xFFFFFFFF;
    }

    if (this.md5('hello') != '5d41402abc4b2a76b9719d911017c592') {
        function add32(x, y) {
            var lsw = (x & 0xFFFF) + (y & 0xFFFF),
                msw = (x >> 16) + (y >> 16) + (lsw >> 16);
            return (msw << 16) | (lsw & 0xFFFF);
        }
    }
}
var toolset = new Toolset();

//------------------------------------------------------------------------
// MESSAGES
//------------------------------------------------------------------------
function Message() {
    this.CODE_SUCCESS        = 0;
    this.CODE_NOT_LOGGED     = 1;
    this.CODE_NO_PROJECT     = 2;
    this.CODE_SERVER_ERROR   = 3;
    this.CODE_UPDATED        = 4;
    this.CODE_NO_RIGHTS      = 5;
    this.CODE_ALREADY_EXISTS = 6;
}
var message = new Message();

//------------------------------------------------------------------------
// LOCAL STORAGE, SESSION AND GRAPHICS
//------------------------------------------------------------------------
function ThClient() {
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
     *
     * Possible states (return):
     * 0 -- sync successfully.
     * 1 -- hash not valid for server (no project selected)
     * 2 -- communication error.
     */
    this.sync = function sync() {
        thclient.thmsg('Sync data. Please stand by.');
        if (localStorage['data'] !== JSON.stringify(thclient.data)) {
            thclient.loadData();
            thclient.id = null;
        }
        try {
            var hash, code;
            $.ajax({
                type: 'POST',
                url: '/texthistory/session/hash/',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                dataType: 'json'
            }).done(function (data) {
                    if (data.code === 3) { // server error
                        throw 'Server error.';
                    }
                    code = data.code;
                    hash = data.hash; // hash will be empty if no project selected
                }).fail(function () {
                    throw 'Unable to sync data.';
                });
            if (code === 1) { // return to login page if not logged in
                window.location = '/texthistory';
                return 3;
            }
            if (code === 2 || hash === '') { // no project selected
                return 1;
            }
            if (localStorage['hash'] !== hash) {
                $.ajax({
                    type: 'POST',
                    url: '/texthistory/session/data/',
                    contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                    dataType: 'json'
                }).done(function (data) {
                        if (data.code == 3) { // server error
                            throw 'Server error.';
                        }
                        thclient.data = data.data;
                        localStorage['data'] = JSON.stringify(thclient.data);
                        localStorage['hash'] = hash;
                    }).fail(function () {
                        throw 'Unable to sync data.';
                    });
                thclient.id = null;
            }
        } catch (e) {
            thclient.notify('warning', e);
            console.warn(e);
            return 2;
        }
        if (thclient.noty !== null) {
            thclient.noty.close();
        }
        return 0;
    };

    this.loadData = function loadData() {
        thclient.data = JSON.parse(localStorage['data']);
    };

    this.saveData = function saveData() {
        localStorage['data'] = JSON.stringify(thclient.data);
    };

    this.createTable = function createTable(data, holder) {
        try {
            var table = $('<table/>', {id: 'wordsTable', 'class': 'list', width: '100%'}),
                colgroup = $('<colgroup/>', {span: data.table.length}),
                thead = $('<thead/>'),
                tbody = $('<tbody/>'),
                tr = $('<tr/>', {'class': 'first'}),
                td;

            $(data.table).each(function (index, col) {
                $(colgroup).append($('<col/>', {span: 1, width: col.width}));
                $(tr).append($('<td/>', {text: col.col}));
            });
            $(thead).append(tr);

            $(data.category).each(function (index, c) {
                $(tbody).append('<tr class="cat">'
                    + '<td>' + c.id + '<\/td>'
                    + '<td>' + c.rank + '<\/td>'
                    + '<td>' + c.name + '<\/td>'
                    + '<td>' + data.locale.category + '<\/td>'
                    + '<td>' + c.count + '<\/td>'
                    + '<td>' + c.average + '<\/td>'
                    + '<td>' + c.freq + '<\/td>'
                    + '<\/tr>');
                $(data.key).each(function (i, k) {
                    if (k.link === c.id) {
                        $(tbody).append('<tr class="key data-link-id-' + k.link + ' hidden">'
                            + '<td>' + k.id + '<\/td>'
                            + '<td>' + k.rank + '<\/td>'
                            + '<td>' + k.name + '<\/td>'
                            + '<td>' + data.locale.word + '<\/td>'
                            + '<td>' + k.count + '<\/td>'
                            + '<td>-<\/td>'
                            + '<td>-<\/td>'
                            + '<\/tr>');
                    }
                });
            });
            $(table).append(colgroup).append(thead).append(tbody);
            $("#" + holder).html(table);
        } catch (e) {
            console.warn(e);
        }
    };

    // Returns a flattened hierarchy containing all leaf nodes under the root.
    function toFlatArray(root) {
        var nodes = [],
            name;
        $(root.key).each(function (index, key) {
            var nodeName = "",
                i;
            for (i = 0; i < root.category.length; i++) {
                if (root.category[i].id === key.link) {
                    name = root.category[i].name;
                    break;
                }
            }
            nodes.push({packageName: name, className: key.name, value: key.count});
        });
        return nodes;
    }

    function treeToFlatArray(root) {
        var nodes = [];
        function recurse(name, node) {
            if (node.children) {
                node.children.forEach(function (child) { recurse(node.name, child); });
            } else {
                nodes.push({packageName: name, className: node.name, value: node.count});
            }
        }
        root.words.forEach(function (node) {recurse(null, node); });
        return nodes;
    }

    // holder - is an ID name
    this.drawBarChart = function drawBarChart(data, holder, size) {
        var r = new Raphael(holder, size, (data.key.length * 40)),
            txtattr = { font: "12px sans-serif", anchor: "left" },
            typeattr = {type: "sharp", fill: "#2f69bf"},
            i;
        for (i = 0; i < data.key.length; i++) {
            r.text(100, (15 + i * 40), data.key[i].name).attr(txtattr);
            r.barchart(200, (-10 + i * 40), 400, 50, data.key[i].interval, 0).attr(typeattr);
        }
    };

    // holder - is an ID name
    this.drawCircleChart = function drawCircleChart(data, holder, diameter) {
        function classes(root) {
            return {children: toFlatArray(root)};
        }
        var format = d3.format(",d"),
            color = d3.scale.category20c(),
            bubble = d3.layout.pack().sort(null).size([diameter, diameter]).padding(1.5),
            svg = d3.select("#" + holder).append("svg").attr("width", diameter).attr("height", diameter).attr("class", "bubble"),
            node = svg.selectAll(".node")
                .data(bubble.nodes(classes(data))
                    .filter(function (d) { return !d.children; }))
                .enter().append("g")
                .attr("class", "node")
                .attr("transform", function (d) { return "translate(" + d.x + "," + d.y + ")"; });

        node.append("title").text(function (d) { return d.className + " [" + d.packageName + "]: " + format(d.value); });
        node.append("circle").attr("r", function (d) { return d.r; }).style("fill", function (d) { return color(d.packageName); });
        node.append("text").attr("dy", ".2em").style("text-anchor", "middle").text(function (d) { return d.className.substring(0, d.r / 4); });
        d3.select(self.frameElement).style("height", diameter + "px");
    };

    this.drawCloudChart = function drawCloudChart(data, holder, size) {
        var fill = d3.scale.category20();
        function draw(elem) {
            d3.select("#" + holder).append("svg")
                .attr("width", size)
                .attr("height", size)
                .append("g")
                .attr("transform", "translate(" + size / 2 + "," + size / 2 + ")")
                .selectAll("text")
                .data(elem)
                .enter().append("text")
                .style("font-size", function (d) { return d.size + "px"; })
                .style("font-family", "Impact")
                .style("fill", function (d, i) { return fill(i); })
                .attr("text-anchor", "middle")
                .attr("transform", function (d) {return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")"; })
                .text(function (d) { return d.text; });
        }

        d3.layout.cloud().size([size, size])
            .words(toFlatArray(data).map(function (d) {
                return {text: d.className, size: d.value * 12}; // Set font size here only
            }))
            .rotate(function () { return ~~(Math.random() * 2) * 90; })
            .font("Impact")
            .fontSize(function (d) { return d.size; })
            .on("end", draw)
            .start();

    };
}
var thclient = new ThClient();

//------------------------------------------------------------------------
// AJAX EVENTS HANDLERS
//------------------------------------------------------------------------
// TODO: Add GET requests. Add new object with functions and use predefined in events.
function ThHandler() {
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
                    localStorage['hash'] = '';
                    localStorage['data'] = '';
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
        var status = thclient.sync();
        if (status === 0) {
            var table = $('<table/>', {id: 'statTable', 'class': 'list', width: '100%'}),
                colgroup = $('<colgroup/>', {span: '2'}).append('<col span="1" width="200px"><col span="1">'),
                thead = $('<thead/>').append('<tr class="first"><td>Параметр</td><td>Значение</td></tr>'),
                tbody = $('<tbody/>').append('<tr><td>Число слов</td><td>' + thclient.data.count + '</td></tr>')
                                     .append('<tr><td>Средняя длина слова</td><td>' + thclient.data.avLength + '</td></tr>')
                                     .append('<tr><td>Число ключевых слов</td><td>' + thclient.data.key.length + '</td></tr>');

            $(table).append(colgroup).append(thead).append(tbody);

            changePage('Статистика данных',
                [
                    {id: 'sep', text: ''}
                ],
                table);
        } else {
            if (status === 1) {
                thclient.notify('warning', 'No project selected.');
            }
        }
    };

    /** Content analysis graphics */
    this.caGraphics = function caGraphics() {
        var status = thclient.sync();
        if (status === 0) {
            changePage('Графики',
                [
                    {id: 'nav-ca-g-f', text: 'Частота'},
                    {id: 'nav-ca-g-g', text: 'Корреляция'},
                    {id: 'nav-ca-g-t', text: 'Облако тегов'},
                    {id: 'nav-ca-g-c', text: 'Круг тегов'},
                    {id: 'sep', text: ''}
                ],
                thclient.drawBarChart(thclient.data, 'content', 600));
        } else {
            if (status === 1) {
                thclient.notify('warning', 'No project selected.');
            }
        }
    };

    this.caGraphicsCor = function caGraphicsCor() {
        var status = thclient.sync();
        if (status === 0) {
            changePage('Графики - Корреляция',
                [
                    {id: 'nav-ca-g-f', text: 'Частота'},
                    {id: 'nav-ca-g-g', text: 'Корреляция'},
                    {id: 'nav-ca-g-t', text: 'Облако тегов'},
                    {id: 'nav-ca-g-c', text: 'Круг тегов'},
                    {id: 'sep', text: ''}
                ],
                'Not implemented.');
        } else {
            if (status === 1) {
                thclient.notify('warning', 'No project selected.');
            }
        }
    };

    this.caGraphicsTag = function caGraphicsTag() {
        var status = thclient.sync();
        if (status === 0) {
            changePage('Графики - Облако тегов',
                [
                    {id: 'nav-ca-g-f', text: 'Частота'},
                    {id: 'nav-ca-g-g', text: 'Корреляция'},
                    {id: 'nav-ca-g-t', text: 'Облако тегов'},
                    {id: 'nav-ca-g-c', text: 'Круг тегов'},
                    {id: 'sep', text: ''}
                ],
                thclient.drawCloudChart(thclient.data, 'data', 600));
        } else {
            if (status === 1) {
                thclient.notify('warning', 'No project selected.');
            }
        }
    };

    this.caGraphicsCat = function caGraphicsCat() {
        var status = thclient.sync();
        if (status === 0) {
            changePage('Графики - Круг тегов',
                [
                    {id: 'nav-ca-g-f', text: 'Частота'},
                    {id: 'nav-ca-g-g', text: 'Корреляция'},
                    {id: 'nav-ca-g-t', text: 'Облако тегов'},
                    {id: 'nav-ca-g-c', text: 'Круг тегов'},
                    {id: 'sep', text: ''}
                ],
                thclient.drawCircleChart(thclient.data, 'data', 600));
        } else {
            if (status === 1) {
                thclient.notify('warning', 'No project selected.');
            }
        }
    };

    // --- CONTENT ANALYSIS :: KEY LIST ---
    /** Content analysis key words list */
    this.caKeyList = function caKeyList() {
        var status = thclient.sync();
        if (status === 0) {
            changePage('Ключевые слова/категории',
                [
                    {id: 'do-cont-key-list-view', text: 'Просмотреть'},
                    {id: 'sep', text: ''}
                ],
                thclient.createTable(thclient.data, 'data'));
        } else {
            if (status === 1) {
                thclient.notify('warning', 'No project selected.');
            }
        }
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
}
var thhandler = new ThHandler();

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
$('#nav-ca-g').click(thhandler.caGraphics);      // default -- frequency

$('#nav-ca-g-f').click(thhandler.caGraphics);    // frequency

$('#nav-ca-g-g').click(thhandler.caGraphicsCor); // correlation graph

$('#nav-ca-g-t').click(thhandler.caGraphicsTag); // tag cloud

$('#nav-ca-g-c').click(thhandler.caGraphicsCat); // tag categories

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