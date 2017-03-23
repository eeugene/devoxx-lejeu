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

export const login = (email: string, password: string, onSuccess: (data: any) => void, onError: (error: any) => void) => {
    heroApi.login(email, password)
        .subscribe(
        data => {
            if (data.status === 201 || data.status === 200) {
                setAuthenticationInLocalStorage(email, data.response.token);
                onSuccess(data);
            } else {
                onError("Login error: bad status " + data.status + " received");
            }
        },
        error => onError(error)
        );
};