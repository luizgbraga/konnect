const usernameInput = document.getElementById('username-input');
const passwordInput = document.getElementById('password-input');
const loginButton = document.getElementById('login-button');

let username = '';
let password = '';

const handleUsernameChange = (e) => {
    username = e.target.value;
}

const handlePasswordChange = (e) => {
    password = e.target.value;
}

const handleSubmit = () => {
    document.body.style.filter = "brightness(50%)";
    LoginModel.login(username, password)
        .then((res) => {
            document.body.style.filter = "brightness(100%)";
            localStorage.setItem("id", res.message)
            window.location.replace('http://localhost:8080/server_war_exploded/feed')
        })
        .catch((err) => {
            console.log(err);
        });
}

usernameInput.addEventListener('input', handleUsernameChange);
passwordInput.addEventListener('input', handlePasswordChange);
loginButton.addEventListener('click', handleSubmit);

class API {
    _route;
    constructor(route) {
        this._route = route;
    }

    async request(
        method,
        path,
        token,
        body,
        query
    ) {
        const qp = query ? `?${query}` : '';
        const headers = {
            'Content-Type': 'application/json',
            ...(token && { Authorization: token }),
        };
        const res = await fetch(`${this._route}/${path}${qp}`, {
            method,
            headers,
            body,
        });
        if (!res.ok && res.status !== 500) {
            throw new Error('Unexpected error occurred.');
        }
        return res.json();
    }
}

const API_URL = 'api';

class LoginAPI extends API {
    constructor() {
        super(`${API_URL}/user`);
    }

    async login(username, password) {
        const body = JSON.stringify({ username, password });
        return this.request('POST', '/login', null, body, null);
    }
}

const api = new LoginAPI();

class LoginModel {
    static async login(username, password) {
        const res = await api.login(username, password);
        console.log(res)
        return res;
    }
}