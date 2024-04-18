const contentInput = document.getElementById('content-input');
const searchInput = document.getElementById('search-input');
const postButton = document.getElementById('post-button');

// Get the elements for minimum degree counter
const minMinusBtn = document.getElementById("min-minus-btn");
const minPlusBtn = document.getElementById("min-plus-btn");
const minDegreeDisplay = document.getElementById("min-degree");

// Get the elements for maximum degree counter
const maxMinusBtn = document.getElementById("max-minus-btn");
const maxPlusBtn = document.getElementById("max-plus-btn");
const maxDegreeDisplay = document.getElementById("max-degree");

const newMessageContainer = document.getElementById('new-message-container');
const feedContainer = document.getElementById('feed-container');
const usersContainer = document.getElementById('users-container');

document.getElementById("username").innerHTML += localStorage.getItem("username")

let search = '';
usersContainer.style.display = 'none';
let content = '';

let minDegree = 1;
let maxDegree = 1;
const handleContentChange = (e) => {
    content = e.target.value;
}

const handleSearchChange = (e) => {
    search = e.target.value;
    ConnectionModel.
    console.log(search);
    if (search.length > 0) {
        feedContainer.style.display = 'none';
        newMessageContainer.style.display = 'none';
        usersContainer.style.display = 'flex'
    } else {
        feedContainer.style.display = 'flex';
        newMessageContainer.style.display = 'flex'
        usersContainer.style.display = 'none'
    }
}

const handleSubmit = () => {
    PostModel.post(content).then((res) => {})
    console.log(res);
}

const updateMinDegree = () => {
    minDegreeDisplay.textContent = minDegree;
    listAll(minDegree, maxDegree)
}

const updateMaxDegree = () => {
    maxDegreeDisplay.textContent = maxDegree;
    listAll(minDegree, maxDegree)
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

searchInput.addEventListener('input', handleSearchChange);
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
        let params = new URL(document.location).searchParams;
        let groupId = params.get("group");
        if (!groupId) groupId = 'null'
        const body = JSON.stringify({ userId, content, groupId });
        return this.request('POST', '', null, body, null);
    }

    async list(minDepth, maxDepth) {
        const userId = localStorage.getItem("id")
        let params = new URL(document.location).searchParams;
        let groupId = params.get("group");
        if (!groupId) groupId = 'null'
        const query = `minDepth=${minDepth}&maxDepth=${maxDepth}&userId=${userId}&groupId=${groupId}`;
        return this.request('GET', '', null, null, query)
    }

    async upvote(postId) {
        const query = `postId=${postId}&vote=upvote`;
        return this.request('PUT', '', null, null, query)
    }

    async downvote(postId) {
        const query = `postId=${postId}&vote=downvote`;
        return this.request('PUT', '', null, null, query)
    }
}

const api = new PostAPI();

class PostModel {
    static async post(content) {
        const res = await api.post(content);
        return res;
    }

    static async list(minDepth, maxDepth) {
        const res = await api.list(minDepth, maxDepth)
        return res;
    }

    static async upvote(postId) {
        const res = await api.upvote(postId);
        return res;
    }

    static async downvote(postId) {
        const res = await api.downvote(postId);
        return res;
    }
}

async function upvote(e)  {
    const postId = e.target.id;
    await PostModel.upvote(postId);
}

async function downvote(e)  {
    const postId = e.target.id;
    await PostModel.downvote(postId);
}


async function listAll(min, max) {

    PostModel.list(minDegree, maxDegree).then((res) => {
        const posts = JSON.parse(res.message)
        feedContainer.innerHTML = "";
        console.log(posts);
        for (const post of posts) {
            const postElement = document.createElement("div");
            postElement.classList.add("flex", "box-shadow", "w-full", "border-default", "bg-white");

            const postElementContainer = document.createElement("div");
            postElementContainer.classList.add("flex", "flex-column", "w-full", "gap-12", "pl-18", "pr-18", "pt-18", "pb-18");

            const usernameElement = document.createElement("div");
            usernameElement.classList.add("flex", "w-full", "sm-body", "dark-gray");
            usernameElement.innerHTML = post.username;

            const postContent = document.createElement("div");
            postContent.classList.add("flex", "w-full", "body", "black", "pt-6", "pb-6");
            postContent.innerHTML = post.content;

            const interactionsContainer = document.createElement("div");
            interactionsContainer.classList.add("flex", "w-full", "gap-12", "flex-row", "justify-between", "align-center");

            const interactionsActionsContainer = document.createElement("div");
            interactionsActionsContainer.classList.add("flex", "w-full", "gap-12", "flex-row", "justify-start", "align-center");

            const interactionsInfoContainer = document.createElement("div");
            interactionsInfoContainer.classList.add("flex", "gap-12", "flex-row", "justify-start", "align-center");

            const upvotes = document.createElement("p")
            upvotes.classList.add("green")
            upvotes.innerHTML = post.upvotes;

            const downvotes = document.createElement("p")
            downvotes.classList.add("red")
            downvotes.innerHTML = post.downvotes

            const likeButton = document.createElement("div");
            likeButton.classList.add("flex", "sm-body", "nunito",  "button", "small-btn", "primary-btn");
            likeButton.innerHTML = "Gostei";
            likeButton.id = post.id;

            const dislikeButton = document.createElement("div");
            dislikeButton.classList.add("flex", "sm-body", "nunito",  "button", "small-btn", "secondary-btn");
            dislikeButton.innerHTML = "Não gostei";
            dislikeButton.id = post.id;

            likeButton.addEventListener("click", upvote);
            dislikeButton.addEventListener("click", downvote);

            interactionsActionsContainer.appendChild(likeButton);
            interactionsActionsContainer.appendChild(dislikeButton);
            interactionsInfoContainer.appendChild(upvotes);
            interactionsInfoContainer.appendChild(downvotes);
            interactionsContainer.appendChild(interactionsActionsContainer);
            interactionsContainer.appendChild(interactionsInfoContainer);
            postElementContainer.appendChild(usernameElement);
            postElementContainer.appendChild(postContent);
            postElementContainer.appendChild(interactionsContainer);
            postElement.appendChild(postElementContainer);

            feedContainer.appendChild(postElement);
        }
    })
}

listAll(minDegree, maxDegree)