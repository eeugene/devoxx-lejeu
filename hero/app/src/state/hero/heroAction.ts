import { IHero, IAvatar, IHeroRegistrationForm, IHeroQuizzStats } from '.';

export type HeroAction =
    // list of hero actions
    | HeroLoggedInAction
    | HeroLogoutAction
    | HeroLoggedOutAction
    | HeroLoginErrorAction
    | HeroSubmitLoginAction
    | HeroOpenRegisteringAction
    | HeroExitRegisteringAction
    | HeroRegistrationDoneAction
    | HeroSubmitRegistrationAction
    | HeroDetailsReceivedAction
    | HeroAvatarsReceivedAction
    | HeroRegisteringErrorMissingValueAction
    | HeroRegisteringServerErrorAction
    | HeroQuizzStatsReceivedAction
    | ErrorOnGetHeroDetailsAction
    | ErrorOnGetHeroQuizzStatsAction
    ;


export interface HeroLogoutAction { type: 'HERO_LOG_OUT'; }
export interface HeroLoggedOutAction { type: 'HERO_LOGGED_OUT'; }
export interface HeroOpenRegisteringAction { type: 'HERO_OPEN_REGISTERING'; }
export interface HeroExitRegisteringAction { type: 'HERO_EXIT_REGISTERING'; }
export interface HeroSubmitLoginAction { type: 'HERO_SUBMIT_LOGIN'; email: string; password: string; }
export interface HeroRegistrationDoneAction { type: 'HERO_REGISTRATION_DONE'; email: string; password: string; }
export interface HeroSubmitRegistrationAction { type: 'HERO_SUBMIT_REGISTRATION'; form: IHeroRegistrationForm; }
export interface HeroQuizzStatsReceivedAction { type: 'HERO_QUIZZ_STATS_RECEIVED'; result: IHeroQuizzStats; }

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
export const createHeroLogoutAction = (): HeroLogoutAction => ({ type: 'HERO_LOG_OUT' });
export const createHeroOpenRegisteringAction = (): HeroOpenRegisteringAction => ({ type: 'HERO_OPEN_REGISTERING' });
export const createHeroExitRegisteringAction = (): HeroExitRegisteringAction => ({ type: 'HERO_EXIT_REGISTERING' });
export const createHeroRegistrationDoneAction = (email:string,password:string): HeroRegistrationDoneAction => ({ type: 'HERO_REGISTRATION_DONE', email, password });
export const createHeroSubmitLoginAction = (email:string,password:string): HeroSubmitLoginAction => ({ type: 'HERO_SUBMIT_LOGIN', email, password });
export const createHeroRegisteringErrorMissingValueAction = (errors: string[]): HeroRegisteringErrorMissingValueAction => ({ type: 'HERO_REGISTERING_ERROR_MISSING_VALUE', errors });
export const createHeroRegisteringServerErrorAction = (errors: string): HeroRegisteringServerErrorAction => ({ type: 'HERO_REGISTERING_SERVER_ERROR', errors });
export const createHeroLoginErrorAction = (errors: string): HeroLoginErrorAction => ({ type: 'HERO_LOGIN_ERROR', errors });
export const createHeroSubmitRegistrationAction = (form: IHeroRegistrationForm): HeroSubmitRegistrationAction => ({ type: 'HERO_SUBMIT_REGISTRATION', form });
export const createHeroQuizzStatsReceivedAction = (result: IHeroQuizzStats): HeroQuizzStatsReceivedAction => ({ type: 'HERO_QUIZZ_STATS_RECEIVED', result });


export interface ErrorOnGetHeroDetailsAction {
    type: 'ERROR_ON_GET_HERO_DETAILS';
    error: string;
};

export interface ErrorOnGetHeroQuizzStatsAction {
    type: 'ERROR_ON_GET_HERO_QUIZZ_STATS';
    error: string;
};
export const createErrorOnGetHeroDetailsAction = (error: string): ErrorOnGetHeroDetailsAction =>
({ type: 'ERROR_ON_GET_HERO_DETAILS', error });

export const createErrorOnGetHeroQuizzStatsAction = (error: string): ErrorOnGetHeroQuizzStatsAction =>
({ type: 'ERROR_ON_GET_HERO_QUIZZ_STATS', error });
