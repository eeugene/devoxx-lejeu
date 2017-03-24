import { Observable, IScheduler } from 'rx';
import { Epic } from 'redux-observable';

import { Action } from 'state/actions';
import { AppState } from 'state';
import { IHero } from '.';
import {
    createHeroReceivedAction,
    createHeroLoggedInAction,
    createHeroLoginErrorAction,
    createHeroRegistrationDoneAction,
    createHeroRegisteringServerErrorAction,
    HeroLoggedInAction,
    HeroSubmitRegistrationAction,
    HeroSubmitLoginAction
    } from './heroAction';
import { IHeroApi } from 'api/heroApi';
import { setAuthenticationInLocalStorage } from './heroService';

export function getHeroDetails(api: IHeroApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return (action$, _) => {
        return action$.ofType('HERO_LOGGED_IN').mergeMap(
            (action: HeroLoggedInAction) =>
                api.getHero(action.email)
                    .timeout(api.timeout, scheduler)
                    .map((h: IHero) => createHeroReceivedAction(h))
        );
    };
}

export function submitHeroRegistration(api: IHeroApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return (action$, _) => {
        return action$.ofType('HERO_SUBMIT_REGISTRATION').mergeMap(
            (action: HeroSubmitRegistrationAction) =>
                api.register(action.form)
                    .timeout(api.timeout, scheduler)
                    .map(_ => createHeroRegistrationDoneAction(action.form.email, action.form.password))
                    .catch(error => Observable.of(createHeroRegisteringServerErrorAction(error.xhr.response.errors)))
        );
    };
}

export function loginHero(api: IHeroApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return (action$, _) => {
        return action$.ofType('HERO_SUBMIT_LOGIN', 'HERO_REGISTRATION_DONE').mergeMap(
            (action: HeroSubmitLoginAction) =>
                api.login(action.email, action.password)
                    .timeout(api.timeout, scheduler)
                    .map(
                    data => {
                        if (data.status === 201 || data.status === 200) {
                            setAuthenticationInLocalStorage(action.email, data.response.token);
                            return createHeroLoggedInAction(action.email)
                        } else {
                            return createHeroLoginErrorAction("Login error: bad status " + data.status + " received")
                        }
                    })
                    .catch(error => Observable.of(createHeroLoginErrorAction(error)))
        );
    };
}