const groupsContainer = document.getElementById('groups-container');

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

class KnAPI extends API {
    constructor() {
        super(`${API_URL}/kn`);
    }

    async list() {
        const userId = localStorage.getItem("id")
        const query = `userId=${userId}`;
        return this.request('GET', '', null, null, query)
    }
}

const api = new KnAPI();

class KnModel {
    static async list() {
        const res = await api.list();
        return res;
    }

}

KnModel.list().then((res) => {
    const kns = JSON.parse(res.message)
    console.log(kns);
    for (const kn of kns) {

        const groupElement = document.createElement("div");
        groupElement.classList.add("box-shadow", "w-full", "p-12", "flex", "justify-between", "align-center");
        groupElement.innerHTML += `
                <p>${kn.name}</p>
            `;

        groupsContainer.appendChild(groupElement);
    }
})
