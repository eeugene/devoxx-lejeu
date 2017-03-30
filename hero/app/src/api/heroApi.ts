import { Observable, ajax, AjaxResponse } from 'rx';
import { IHero, IAvatar, IHeroQuizzStats, IHeroRegistrationForm } from 'state/hero';
import { getAuthorizationHeader } from 'state/hero/heroService';

export interface IHeroApi {
    getHero: (email:string) => Observable<IHero>;
    login: (username:string,password:string) => Observable<AjaxResponse>;
    register: (hero:IHeroRegistrationForm) => Observable<AjaxResponse>;
    getAvatars: () => Observable<IAvatar[]>;
    getQuizzStats: (email:string) => Observable<IHeroQuizzStats>;
    timeout: number;
}

export const heroApi: IHeroApi = {
    getHero(email:string) {
        return ajax.getJSON<IHero>('api/hero/'+email, getAuthorizationHeader());
    },
    getAvatars() {
        return ajax.getJSON<IAvatar[]>('api/avatar');
    },
    login(username:string,password:string) {
        const body = JSON.stringify({...{},username,password});
        const headers = {'Content-Type': 'application/json'};
        return ajax.post('login', body, headers);
    },
    register(hero:IHeroRegistrationForm) {
        const body = JSON.stringify(hero);
        const headers = {'Content-Type': 'application/json'};
        return ajax.post('api/hero/register', body, headers);
    },
    getQuizzStats(email:string) {
        return ajax.getJSON<IHeroQuizzStats>('api/hero/'+email+'/quizz-stats', getAuthorizationHeader());
    },
    timeout: 5000
};
