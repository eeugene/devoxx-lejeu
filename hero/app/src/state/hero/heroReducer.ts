import { IHeroState, IHero } from '.';
import { Action } from 'state/actions'

export function heroReducer(state: IHeroState = {}, action: Action):IHeroState {
    switch (action.type) {
        case 'HERO_DETAILS_RECEIVED':
            return {
                ...state,
                hero: action.hero
            }
        case 'HERO_LOGGED_IN':
            return {
                ...state,
                isLoggedIn: true
            }
        case 'HERO_LOGGED_OUT': 
            return {
                isLoggedIn: false
            }
        case 'HERO_LOGIN_ERROR': 
            return {
                ...state,
                loginErrors: action.errors
            }
        default:
            return state;
    }
}