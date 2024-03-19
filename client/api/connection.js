import { API } from '../utils/model';
import { getToken } from '../utils/api';

const API_URL = proccess.env.API_URL;

export class ConnectionAPI extends API {
    constructor() {
        super(`${API_URL}/connection`);
    }

    async invite(token, userId) {
        const body = JSON.stringify({ userId });
        return this.request('POST', '', token, body, null);
    }
}

const api = new ConnectionAPI();

export class ConnectionModel {
    constructor(dto) {
        this.dto = dto
    }

    static async invite(userId) {
        const token = getToken();
        const res = await api.create(token, userId);
        if (res.type === 'ERROR') throw new Error(res.cause);
        return new PostModel(res.result);
    }
}