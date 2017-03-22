export * from './heroAction'

export interface IAvatar {
    id:string;
    isSelected?:boolean;
}

export interface IHero {
    email: string;
    firstname: string;
    lastname: string;
    nickname: string;
}

export interface IHeroState {
    email?:string;
    hero?: IHero;
    isLoggedIn?: boolean;
    isRegistering?: boolean;
    loginErrors?:string;
    registerErrors?:string[];
};