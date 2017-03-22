import { heroApi } from 'api/heroApi'

export const getTokenFromLocalStorage = () => {
    let token = localStorage.getItem('token')
    return JSON.parse(token)
}

export const setTokenInLocalStorage = (token:string) => {
    let tokenObject = {...{},token};
    removeTokenFromLocalStorage();
    localStorage.setItem('token', JSON.stringify(tokenObject));
}

export const removeTokenFromLocalStorage = () => localStorage.removeItem('token')
export const isHeroAuthenticated = () => getTokenFromLocalStorage() !== null

export const getAuthorizationHeader = () => {
    const tokenObj = getTokenFromLocalStorage()
    return isHeroAuthenticated() ? {'Authorization': 'Bearer ' + tokenObj.token} : {}
}

export const login = (email:string,password:string, onSuccess:(data:any)=>void, onError:(error:any)=>void) => {
    heroApi.login(email,password)
        .subscribe(
            data => {
                if (data.status === 201 || data.status === 200) {
                    setTokenInLocalStorage(data.response.token)
                    onSuccess(data)
                } else {
                    onError("Login error: bad status " + data.status + " received")
                }
            },
            error => onError(error)
        )
}