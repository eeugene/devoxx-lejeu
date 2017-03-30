import { IHeroState } from '.';
import { Action } from 'state/actions';

export function heroReducer(state: IHeroState = {}, action: Action): IHeroState {
    switch (action.type) {
        case 'HERO_DETAILS_RECEIVED':
            return {
                ...state,
                hero: action.data.hero,
                quizzStats: action.data.quizzStats
            };
        case 'HERO_LOGGED_IN':
            return {
                ...state,
                email: action.email,
                isLoggedIn: true
            };
        case 'HERO_LOGGED_OUT': return <IHeroState>{};
        case 'HERO_LOGIN_ERROR':
            return {
                ...state,
                loginErrors: action.errors
            };
        case 'HERO_OPEN_REGISTERING':
            return {
                ...state,
                isRegistering: true
            };
        case 'HERO_EXIT_REGISTERING':
            return {
                ...state,
                isRegistering: false
            };
        case 'HERO_REGISTERING_ERROR_MISSING_VALUE':
            return {
                ...state,
                registerErrors: action.errors
            };
        case 'HERO_REGISTERING_SERVER_ERROR':
            return {
                ...state,
                registerErrors: [action.errors]
            };
        default:
            return state;
    }
}