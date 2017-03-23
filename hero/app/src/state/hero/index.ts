export * from './heroAction'

export interface IAvatar {
    id:string;
    isSelected?:boolean;
}

export interface IHeroStats {
    wins:number;
    losses:number;
    currentRanking:number;
    bestRanking:number;
}
export interface IHero {
    email: string;
    firstname: string;
    lastname: string;
    nickname: string;
    avatarId: number;
    heroStats:IHeroStats;
}

export interface IHeroState {
    email?:string;
    hero?: IHero;
    isLoggedIn?: boolean;
    isRegistering?: boolean;
    loginErrors?:string;
    registerErrors?:string[];
};