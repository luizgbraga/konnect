const emailInput = document.getElementById('email-input');
const passwordInput = document.getElementById('password-input');
const loginButton = document.getElementById('login-button');

let email = '';
let password = '';

const handleEmailChange = (e) => {
    email = e.target.value;
}

const handlePasswordChange = (e) => {
    password = e.target.value;
}

const handleSubmit = () => {
    console.log(email, password)
    window.LoginModel.login(email, password)
        .then((res) => {
            console.log(res);
        })
        .catch((err) => {
            console.log(err);
        });
}

emailInput.addEventListener('input', handleEmailChange);
passwordInput.addEventListener('input', handlePasswordChange);
loginButton.addEventListener('click', handleSubmit);