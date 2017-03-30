import { Observable, ajax, AjaxResponse } from 'rx';
import { IHeroDto, IAvatar, IHeroRegistrationForm } from 'state/hero';
import { getAuthorizationHeader } from 'state/hero/heroService';

export interface IHeroApi {
    getHero: (email:string) => Observable<IHeroDto>;
    login: (username:string,password:string) => Observable<AjaxResponse>;
    register: (hero:IHeroRegistrationForm) => Observable<AjaxResponse>;
    getAvatars: () => Observable<IAvatar[]>;
    timeout: number;
}

export const heroApi: IHeroApi = {
    getHero(email:string) {
        return ajax.getJSON<IHeroDto>('api/hero/'+email, getAuthorizationHeader());
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
    timeout: 5000
};
