import { IHero } from '.'

export type HeroAction =
    // list of hero actions
    | HeroLoggedInAction
    | HeroLoggedOutAction
    | HeroLoginErrorAction
    | HeroOpenRegisteringAction
    | HeroExitRegisteringAction
    | HeroDetailsReceivedAction
    | HeroRegisteringErrorMissingValueAction
    ;


export interface HeroLoggedInAction { type: 'HERO_LOGGED_IN'}
export interface HeroLoggedOutAction { type: 'HERO_LOGGED_OUT'}
export interface HeroOpenRegisteringAction { type: 'HERO_OPEN_REGISTERING'}
export interface HeroExitRegisteringAction { type: 'HERO_EXIT_REGISTERING'}

export interface HeroRegisteringErrorMissingValueAction { 
    type: 'HERO_REGISTERING_ERROR_MISSING_VALUE';
    field:string;
}

export interface HeroLoginErrorAction { 
    type: 'HERO_LOGIN_ERROR';
    errors: string;
};
export interface HeroDetailsReceivedAction {
    type: 'HERO_DETAILS_RECEIVED';
    hero: IHero;
};

export const createHeroReceivedAction = (hero: IHero): HeroDetailsReceivedAction => ({ type: 'HERO_DETAILS_RECEIVED', hero });
export const createHeroLoggedInAction = ():HeroLoggedInAction => ({type: 'HERO_LOGGED_IN'});
export const createHeroLoggedOutAction = ():HeroLoggedOutAction => ({type: 'HERO_LOGGED_OUT'});
export const createHeroOpenRegisteringAction = ():HeroOpenRegisteringAction => ({type: 'HERO_OPEN_REGISTERING'});
export const createHeroExitRegisteringAction = ():HeroExitRegisteringAction => ({type: 'HERO_EXIT_REGISTERING'});
export const createHeroRegisteringErrorMissingValueAction = (field:string):HeroRegisteringErrorMissingValueAction => ({type: 'HERO_REGISTERING_ERROR_MISSING_VALUE', field});
export const createHeroLoginErrorAction = (errors:string):HeroLoginErrorAction => ({type: 'HERO_LOGIN_ERROR', errors});