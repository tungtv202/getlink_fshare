$(document).ready(function () {

    $("#getlink-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();
        initSocket();
        fire_ajax_submit();
    });
});

var requestId = null;

function fire_ajax_submit() {
    var jsonData = {}
    if (requestId == null) {
        requestId = makeid(6);
    }
    jsonData["requestLink"] = $("#request-link").val();
    jsonData["requestId"] = requestId;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/get-link",
        data: JSON.stringify(jsonData),
        // dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log("data oke" + data);
            var htmlRender = "<div class=\"loader\"></div>";
            $('#feedback').html(htmlRender);

        },
        error: function (e) {
            var htmlRender = "<pre>"
                + e.responseText + "</pre>";
            console.log("error " + e.responseText)
            $('#feedback').html(htmlRender);
        }
    });
}

var SOCKET_URL_REGISTER = '/websocket-example';
var stompClient = null;

function initSocket() {
    var socket = new SockJS(SOCKET_URL_REGISTER);
    stompClient = Stomp.over(socket);
    stompClient.connect({"X-Token": "tokenvalue"}, onConnected, onError);

    function onConnected() {
        console.log(stompClient);
        stompClient.subscribe("/topic/" + requestId, onMessageReceived);
    }

    function onMessageReceived(payload) {
        var htmlRender = "<h4>Your link:</h4><pre id=\"your-link\"  >"
            + payload.body + "</pre>"
            + "<a href=" + payload.body + " class=\"btn btn-success\">Download" + "</a>";

        console.log("payload : " + payload.body);
        $('#feedback').html(htmlRender);
    }

    function onError(error) {
        console.log(error);
    }
}

function makeid(length) {
    var result           = '';
    var characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for ( var i = 0; i < length; i++ ) {
        result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
}