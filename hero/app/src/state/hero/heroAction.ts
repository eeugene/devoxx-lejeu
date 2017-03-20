import { IHero } from '.'

export type HeroAction =
    // list of hero actions
    | HeroLoggedInAction
    | HeroLoggedOutAction
    | HeroLoginErrorAction
    | HeroDetailsReceivedAction;


export interface HeroLoggedInAction { type: 'HERO_LOGGED_IN';};
export interface HeroLoggedOutAction { type: 'HERO_LOGGED_OUT';};

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
export const createHeroLoginErrorAction = (errors:string):HeroLoginErrorAction => ({type: 'HERO_LOGIN_ERROR', errors});