export * from './heroAction'

export interface IHeroState {
    email?:string;
    hero?: IHero;
    quizzStats?: IHeroQuizzStats;
    isLoggedIn?: boolean;
    isRegistering?: boolean;
    loginErrors?:string;
    registerErrors?:string[];
};

export interface IHeroDto {
    hero: IHero;
    quizzStats: IHeroQuizzStats;
}

export interface IHero {
    email: string;
    firstname: string;
    lastname: string;
    nickname: string;
    avatarId: number;
    attackLevel: number;
    hpLevel: number;
    heroStats:IHeroStats;
}

export interface IAvatar {
    id:string;
    isSelected?:boolean;
}

export interface IHeroStats {
    wins:number;
    losses:number;
    currentRanking:number;
    bestRanking:number;
    lastFiveBattles:string;
}

export interface IHeroRegistrationForm {
    email:string;
    password:string;
    firstname:string;
    lastname:string;
    nickname:string;
    avatarId:string;
}

export interface IHeroQuizzStats {
    totalQuizzAnswered: number;
    totalGoodAnswered: number;
    bonusesWined: string[];
}