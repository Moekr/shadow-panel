function checkInput() {
    var invitation = document.getElementById('invitation-input');
    if (invitation != null && (invitation.value || '') === '') return false;
    var confirm = document.getElementById('confirm-input');
    if (confirm != null && (confirm.value || '') === '') return false;
    var username = document.getElementById('username-input').value || '';
    var password = document.getElementById('password-input').value || '';
    return !(username === '' || password === '');
}