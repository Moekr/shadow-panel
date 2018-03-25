function checkInput() {
    return checkInputElement('invitation-input')
        && checkInputElement('username-input')
        && checkInputElement('origin-input')
        && checkInputElement('password-input')
        && checkInputElement('confirm-input')
        && checkInputElement('name-input')
        && checkInputElement('description-input')
        && checkInputElement('region-input')
        && checkInputElement('address-input')
        && checkInputElement('total-data-input')
        && checkInputElement('used-data-input')
        && checkInputElement('port-input')
        && checkInputElement('method-input')
        && checkInputElement('protocol-input')
        && checkInputElement('obfs-input')
        && checkInputElement('platform-input')
        && checkInputElement('link-input')
        && checkInputElement('hash-input');
}

function checkInputElement(elementId) {
    var element = document.getElementById(elementId);
    return element == null || (element.value || '') !== '';
}

function changeRevoke(origin, id, username) {
    $('#revoke-at').data("DateTimePicker").date(moment.unix(origin));
    $('#change-id-input').val(id);
    $('#change-id').text(id);
    $('#change-username').text(username);
    $('#change-modal').modal();
}

function nodeAction(id, name, title, action) {
    $('#node-id-input').val(id);
    $('#action-input').val(action);
    $('#node-modal-title').text(title);
    $('#node-id').text(id);
    $('#node-name').text(name);
    $('#node-modal').modal();
}

function removeClient(id, platform, name) {
    $('#client-id-input').val(id);
    $('#client-platform').text(platform);
    $('#client-name').text(name);
    $('#client-modal').modal();
}