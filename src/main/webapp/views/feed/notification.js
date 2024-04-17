
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
}

const api = new NotificationAPI();

class NotificationModel {
    static async list() {
        const res = await api.list();
        return res;
    }
}

NotificationModel.list().then((res) => {
    console.log(res)

})
