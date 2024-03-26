import { Storage } from './storage';

export const TOKEN = 'token';

export const getToken = () => {
    const token = Storage.get(TOKEN);
    if (!token) throw new Error('Unauthorized');
    return token;
};
