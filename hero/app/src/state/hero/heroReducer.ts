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
        case 'HERO_OPEN_REGISTERING': 
            return {
                ...state,
                isRegistering: true
            }
        case 'HERO_EXIT_REGISTERING': 
            return {
                ...state,
                isRegistering: false
            }
        case 'HERO_REGISTERING_ERROR_MISSING_VALUE': 
            return {
                ...state,
                registerErrors: 'Le champ ' + action.field + ' doit être rempli'
            }
        default:
            return state;
    }
}