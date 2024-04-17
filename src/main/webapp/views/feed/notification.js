const notificationsContainer = document.getElementById('notifications-container');

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

class NotificationAPI extends API {
    constructor() {
        super(`${API_URL}/notification`);
    }

    async list() {
        const userId = localStorage.getItem("id")
        const query = `userId=${userId}`;
        return this.request('GET', '', null, null, query)
    }

    async accept(userFromId) {
        const userToId = localStorage.getItem("id")
        const query = `userFromId=${userFromId}&userToId=${userToId};`
        return this.request('PUT', '', null, null, query);
    }
}

const api = new NotificationAPI();

class NotificationModel {
    static async list() {
        const res = await api.list();
        return res;
    }

    static async accept(userFromId) {
        const res = await api.accept(userFromId);
        return res;
    }
}

async function acceptConnection(e)  {
    const userFromId = e.target.id;
    await NotificationModel.accept(userFromId);
}

NotificationModel.list().then((res) => {
    const notifications = JSON.parse(res.message)
    console.log(notifications);
    for (const notification of notifications) {

            const notificationElement = document.createElement("div");
            notificationElement.classList.add("box-shadow", "w-full", "p-12", "flex", "justify-between", "align-center");
            notificationElement.innerHTML = `
                <p>${notification.id}</p>
                <a class="small-btn primary-btn" id="${notification.id}">Aceitar</a>
            `;

            const acceptButton = notificationElement.querySelector("a");
            acceptButton.addEventListener("click", acceptConnection);

            notificationsContainer.appendChild(notificationElement);
    }

})
