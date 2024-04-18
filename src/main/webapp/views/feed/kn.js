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

async function goToGroup(e) {
    const groupId = e.target.id;
    const url = new URL(window.location.href);

    url.searchParams.set('group', groupId);
    window.location.href = url.href;
}

KnModel.list().then((res) => {
    const kns = JSON.parse(res.message)
    console.log(kns);
    for (const kn of kns) {
        const url = new URL(window.location.href);

        const groupId = url.searchParams.get('group');

        const groupElement = document.createElement("div");
        groupElement.classList.add("box-shadow", "border-default", "w-full", "p-12", "flex", "justify-between", "align-center", "pointer");
        if (kn.id == groupId) {
            groupElement.classList.add("group-selected")
        }
        groupElement.innerHTML += `
                <p id="${kn.id}">${kn.name}</p>
            `;

        const groupButton = groupElement.querySelector("p");
        groupButton.addEventListener("click", goToGroup);

        groupsContainer.appendChild(groupElement);
    }
})
