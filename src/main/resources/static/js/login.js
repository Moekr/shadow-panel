function checkInput() {
    return checkInputElement('invitation-input')
        && checkInputElement('username-input')
        && checkInputElement('origin-input')
        && checkInputElement('password-input')
        && checkInputElement('confirm-input');
}

function checkInputElement(elementId) {
    var element = document.getElementById(elementId);
    return element == null || (element.value || '') !== '';
}