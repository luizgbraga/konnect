export const TOKEN = 'token';

window.getToken = () => {
    const token = window.Storage.get(TOKEN);
    if (!token) throw new Error('Unauthorized');
    return token;
};
