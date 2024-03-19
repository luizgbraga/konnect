import { API } from '../utils/model';
import { getToken } from '../utils/api';

const API_URL = proccess.env.API_URL;

export class PostAPI extends API {
    constructor() {
        super(`${API_URL}/post`);
    }

    async create(token, content) {
        const body = JSON.stringify({ content });
        return this.request('POST', '', token, body, null);
    }

    async vote(token, id, vote) {
        const body = JSON.stringify({ id, vote });
        return this.request('POST', `/vote`, token, body, null);
    
    }
}

const api = new PostAPI();

export class PostModel {
    constructor(dto) {
        this.dto = dto
    }

    static async create(content) {
        const token = getToken();
        const res = await api.create(token, content);
        if (res.type === 'ERROR') throw new Error(res.cause);
        return new PostModel(res.result);
    }

    async upvote() {
        const token = getToken();
        const res = await api.vote(token, this.id, 1);
        if (res.type === 'ERROR') throw new Error(res.cause);
        return new PostModel(res.result);
    }

    async downvote() {
        const token = getToken();
        const res = await api.vote(token, this.id, -1);
        if (res.type === 'ERROR') throw new Error(res.cause);
        return new PostModel(res.result);
    }

    get id() {
        return this.dto.id;
    }
}