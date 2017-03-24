import { heroApi } from 'api/heroApi';

export const AUTHENTICATION_STORAGE_KEY = "Authentication";

export const getAuthenticationFromLocalStorage = () => {
    let authentication = localStorage.getItem(AUTHENTICATION_STORAGE_KEY);
    return JSON.parse(authentication);
};

export const setAuthenticationInLocalStorage = (email: string, token: string) => {
    let authentication = { email, token };
    removeAuthenticationFromLocalStorage();
    localStorage.setItem(AUTHENTICATION_STORAGE_KEY, JSON.stringify(authentication));
};

export const removeAuthenticationFromLocalStorage =
    () => localStorage.removeItem(AUTHENTICATION_STORAGE_KEY);

export const isHeroAuthenticated = () => getAuthenticationFromLocalStorage() !== null;

export const getAuthorizationHeader = () => {
    const authentication = getAuthenticationFromLocalStorage();
    return isHeroAuthenticated() ? { 'Authorization': 'Bearer ' + authentication.token } : {};
};
