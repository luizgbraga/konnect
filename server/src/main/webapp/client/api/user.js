import { API } from '../utils/model';
import { getToken } from '../utils/api';

const API_URL = proccess.env.API_URL;

export class UserAPI extends API {
    constructor() {
        super(`${API_URL}/user`);
    }

    async list(token) {
        return this.request('GET', '', token, null, null);
    }
}

const api = new UserAPI();

export class UserModel {
    constructor(dto) {
        this.dto = dto
    }

    static async list() {
        const token = getToken();
        const res = await api.list(token);
        if (res.type === 'ERROR') throw new Error(res.cause);
        return new UserModel(res.result);
    }
}