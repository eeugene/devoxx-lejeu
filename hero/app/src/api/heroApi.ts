import { Observable, ajax } from './../rx';
import {IHero} from 'state/hero'

export interface IHeroApi {
    getHero: () => Observable<IHero>;
    login: (username:string,password:string) => Observable<any>;
    timeout: number;
}

export const heroApi: IHeroApi = {
    getHero() {
        return ajax.getJSON<IHero>('api/hero');
    },
    login(username:string,password:string) {
        const body = JSON.stringify({...{},username,password})
        const headers = {}
        return ajax.post('api/login', body, headers);
    },
    timeout: 5000
};
