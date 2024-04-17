const contentInput = document.getElementById('content-input');
const postButton = document.getElementById('post-button');

// Get the elements for minimum degree counter
const minMinusBtn = document.getElementById("min-minus-btn");
const minPlusBtn = document.getElementById("min-plus-btn");
const minDegreeDisplay = document.getElementById("min-degree");

// Get the elements for maximum degree counter
const maxMinusBtn = document.getElementById("max-minus-btn");
const maxPlusBtn = document.getElementById("max-plus-btn");
const maxDegreeDisplay = document.getElementById("max-degree");

const feedContainer = document.getElementById(('feed-container'))

document.getElementById("username").innerHTML += localStorage.getItem("username")

let content = '';

let minDegree = 1;
let maxDegree = 1;
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

const updateMinDegree = () => {
    minDegreeDisplay.textContent = minDegree;
}

const updateMaxDegree = () => {
    maxDegreeDisplay.textContent = maxDegree;
}

const handleMinMinusClick = () => {
    if (minDegree > 1) {
        minDegree--;
        updateMinDegree();
        if (maxDegree < minDegree) {
            maxDegree = minDegree;
            updateMaxDegree();
        }
    }
}

const handleMinPlusClick = () => {
    minDegree++;
    updateMinDegree();
}

const handleMaxMinusClick = () => {
    if (maxDegree > 1) {
        maxDegree--;
        updateMaxDegree();
    }
}

const handleMaxPlusClick = () => {
    maxDegree++;
    updateMaxDegree();
}

contentInput.addEventListener('input', handleContentChange);
postButton.addEventListener('click', handleSubmit);
minMinusBtn.addEventListener('click', () => {
    handleMinMinusClick();
    updateMinDegree();
});
minPlusBtn.addEventListener('click', () => {
    handleMinPlusClick();
    updateMinDegree();
});
maxMinusBtn.addEventListener('click', () => {
    handleMaxMinusClick();
    updateMaxDegree();
});
maxPlusBtn.addEventListener('click', () => {
    handleMaxPlusClick();
    updateMaxDegree();
});

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