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
    let socket = new SockJS('')
}