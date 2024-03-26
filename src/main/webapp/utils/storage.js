window.Storage = class Storage {
    static get(key) {
        const value = localStorage.getItem(key);
        if (!value) return null;
        return value;
    }

    static set(key, value) {
        localStorage.setItem(key, value);
    }

    static remove(key) {
        localStorage.removeItem(key);
    }
}
  