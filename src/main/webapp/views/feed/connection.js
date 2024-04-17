const searchInput = document.getElementById('search-input');

const usersContainer = document.getElementById(('users-container'))

let search = '';

const handleSearchChange = (e) => {
    search = e.target.value;
    ConnectionModel.list(search).then((res) => {
        console.log(res);
    }).catch((err) => {
        console.log(err)
    })
}


searchInput.addEventListener('input', handleSearchChange);

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
        const res = await fetch(`${this._route}${path}${qp}`, {
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

class ConnectionAPI extends API {
    constructor() {
        super(`${API_URL}/connection`);
    }

    async create(userToId) {
        const userFromId = localStorage.getItem("id")
        const body = JSON.stringify({ userFromId, userToId })
        return this.request('POST', '', null, body, null);
    }

    async list(searchFilter) {
        const userId = localStorage.getItem("id")
        const query = `userId=${userId}&searchFilter=${searchFilter}`;
        return this.request('GET', '', null, null, query)
    }
}

const api = new ConnectionAPI();

class ConnectionModel {
    static async list(searchFilter) {
        const res = await api.list(searchFilter);
        return res;
    }

    static async create(userToId) {
        const res = await api.create(userToId);
        return res;
    }
}

async function create(e) {
    const userId = e.currentTarget.userId;
    await ConnectionModel.create(userId);
}

ConnectionModel.list('').then((res) => {
    const users = JSON.parse(res.message)
    console.log(users);
    for (const user of users) {
        if (user.id !== localStorage.getItem("id")) {
            usersContainer.innerHTML += `
            <p id="${user.id}"">${user.username}</p>
            `
            let thisInput = document.getElementById(user.id)
            thisInput.addEventListener("click", create)
            thisInput.userId = user.id;
        }
    }

})
