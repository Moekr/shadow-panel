function checkInput() {
    var username = document.getElementById('username-input').value || '';
    var password = document.getElementById('password-input').value || '';
    return !(username === '' || password === '');
}