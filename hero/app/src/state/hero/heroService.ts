
export const getTokenFromLocalStorage = () => {
    let token = localStorage.getItem('token')
    return JSON.parse(token)
}

export const setTokenInLocalStorage = (token:string) => {
    let tokenObject = {...{},token};
    localStorage.setItem('token', JSON.stringify(tokenObject));
}

export const removeTokenFromLocalStorage = () => localStorage.removeItem('token')
export const isHeroAuthenticated = () => getTokenFromLocalStorage() !== null

export const getAuthenticatedHeader = () => {
    const tokenObj = getTokenFromLocalStorage()
    return isHeroAuthenticated() ? {'Authorization': 'Bearer ' + tokenObj.token} : {}
}