import { IHero, IAvatar } from '.';

export type HeroAction =
    // list of hero actions
    | HeroLoggedInAction
    | HeroLoggedOutAction
    | HeroLoginErrorAction
    | HeroOpenRegisteringAction
    | HeroExitRegisteringAction
    | HeroDetailsReceivedAction
    | HeroAvatarsReceivedAction
    | HeroRegisteringErrorMissingValueAction
    | HeroRegisteringServerErrorAction
    ;


export interface HeroLoggedOutAction { type: 'HERO_LOGGED_OUT'; }
export interface HeroOpenRegisteringAction { type: 'HERO_OPEN_REGISTERING'; }
export interface HeroExitRegisteringAction { type: 'HERO_EXIT_REGISTERING'; }

export interface HeroLoggedInAction {
    type: 'HERO_LOGGED_IN';
    email: string;
}
export interface HeroRegisteringErrorMissingValueAction {
    type: 'HERO_REGISTERING_ERROR_MISSING_VALUE';
    errors: string[];
}
export interface HeroLoginErrorAction {
    type: 'HERO_LOGIN_ERROR';
    errors: string;
};
export interface HeroDetailsReceivedAction {
    type: 'HERO_DETAILS_RECEIVED';
    hero: IHero;
};
export interface HeroAvatarsReceivedAction {
    type: 'HERO_AVATARS_RECEIVED';
    avatars: IAvatar[];
};
export interface HeroRegisteringServerErrorAction {
    type: 'HERO_REGISTERING_SERVER_ERROR';
    errors: string;
};

export const createHeroReceivedAction = (hero: IHero): HeroDetailsReceivedAction => ({ type: 'HERO_DETAILS_RECEIVED', hero });
export const createHeroAvatarsReceivedAction = (avatars: IAvatar[]): HeroAvatarsReceivedAction => ({ type: 'HERO_AVATARS_RECEIVED', avatars });
export const createHeroLoggedInAction = (email: string): HeroLoggedInAction => ({ type: 'HERO_LOGGED_IN', email });
export const createHeroLoggedOutAction = (): HeroLoggedOutAction => ({ type: 'HERO_LOGGED_OUT' });
export const createHeroOpenRegisteringAction = (): HeroOpenRegisteringAction => ({ type: 'HERO_OPEN_REGISTERING' });
export const createHeroExitRegisteringAction = (): HeroExitRegisteringAction => ({ type: 'HERO_EXIT_REGISTERING' });
export const createHeroRegisteringErrorMissingValueAction = (errors: string[]): HeroRegisteringErrorMissingValueAction => ({ type: 'HERO_REGISTERING_ERROR_MISSING_VALUE', errors });
export const createHeroRegisteringServerErrorAction = (errors: string): HeroRegisteringServerErrorAction => ({ type: 'HERO_REGISTERING_SERVER_ERROR', errors });
export const createHeroLoginErrorAction = (errors: string): HeroLoginErrorAction => ({ type: 'HERO_LOGIN_ERROR', errors });