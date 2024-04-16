const contentInput = document.getElementById('content-input');
const postButton = document.getElementById('post-button');

const feedContainer = document.getElementById(('feed-container'))

let content = '';

const handleContentChange = (e) => {
    content = e.target.value;
}


const handleSubmit = () => {
    PostModel.post(content)
        .then((res) => {
            console.log('funcionou')
            console.log(res);
        })
        .catch((err) => {
            console.log(err);
        });
}

contentInput.addEventListener('input', handleContentChange);
postButton.addEventListener('click', handleSubmit);

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

class PostAPI extends API {
    constructor() {
        super(`${API_URL}/post`);
    }

    async post(content) {
        const userId = localStorage.getItem("id")
        const body = JSON.stringify({ userId, content });
        return this.request('POST', '', null, body, null);
    }

    async list(minDepth, maxDepth, searchFilter) {
        const userId = localStorage.getItem("id")
        const query = `minDepth=${minDepth}&maxDepth=${maxDepth}&searchFilter=${searchFilter}&userId=ola`;
        return this.request('GET', '', null, null, query)
    }
}

const api = new PostAPI();

class PostModel {
    static async post(content) {
        const res = await api.post(content);
        return res;
    }

    static async list(minDepth, maxDepth, searchFilter) {
        const res = await api.list(minDepth, maxDepth, searchFilter)
        return res;
    }
}

PostModel.list(0, 5, '').then((res) => {
    feedContainer.innerHTML += "<p>qualfoi</p>"
})