export * from './heroAction'

export interface IHero {
    email: string;
    firstname: string;
    lastname: string;
    nickname: string;
}

export interface IHeroState {
    hero?: IHero;
    isLoggedIn?: boolean;
    isRegistering?: boolean;
    loginErrors?:string;
    registerErrors?:string;
};