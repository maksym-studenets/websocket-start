let stompClient = null;

function setConnected(connected) {
    $('#connect').prop('disabled', connected);
    $('#disconnect').prop('disabled', !connected);

    if (connected) {
        $('#conversation').show();
    } else {
        $('#conversation').hide();
    }
    $('#greetings').html('');
}

function connect() {
    let socket = new SockJS('/test-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function(greeting) {
            let parsedGreetings = JSON.parse(greeting.body).length;
            for (let i = 0; i < parsedGreetings; i++) {
                showGreeting(JSON.parse(greeting.body)[i].content);
            }
        });
    });
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log('Disconnected!');
}

function sendName() {
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(greeting) {
    $('#greetings').append('<tr><td>' + greeting + '</td></tr>');
}

$(function () {
   $('form').on('submit', function (e) {
       e.preventDefault();
   });
   $('#connect').click(function () { connect(); });
   $('#disconnect').click(function () { disconnect(); });
   $('#send').click(function () { sendName(); });
});