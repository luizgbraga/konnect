const searchInput = document.getElementById('search-input');

const usersContainer = document.getElementById(('users-container'))

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
    const userId = e.target.id;
    await ConnectionModel.create(userId);
}

ConnectionModel.list('').then((res) => {
    const users = JSON.parse(res.message)
    console.log(users);
    for (const user of users) {
        if (user.id !== localStorage.getItem("id")) {
            const userElement = document.createElement("div");
            userElement.classList.add("box-shadow", "w-full", "p-12", "flex", "justify-between", "align-center");
            userElement.innerHTML = `
                <p>${user.username}</p>
                <a class="small-btn primary-btn" id="${user.id}">Seguir</a>
            `;

            const followButton = userElement.querySelector("a");
            followButton.addEventListener("click", create);

            usersContainer.appendChild(userElement);
        }
    }

})
