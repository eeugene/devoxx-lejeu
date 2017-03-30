import { Observable, IScheduler } from 'rx';
import { Epic } from 'redux-observable';

import { Action } from 'state/actions';
import { AppState } from 'state';
import { IHeroDto } from '.';
import {
    createHeroReceivedAction,
    createHeroLoggedInAction,
    createHeroLoggedOutAction,
    createHeroLoginErrorAction,
    createHeroRegistrationDoneAction,
    createHeroRegisteringServerErrorAction,
    createErrorOnGetHeroDetailsAction,
    createRefreshHeroStatsAction,
    HeroLoggedInAction,
    HeroSubmitRegistrationAction,
    HeroDetailsReceivedAction,
    RefreshHeroStatsAction,
    HeroSubmitLoginAction
} from './heroAction';
import { IHeroApi } from 'api/heroApi';
import { setAuthenticationInLocalStorage, removeAuthenticationFromLocalStorage } from './heroService';

export function getHeroDetails(api: IHeroApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return (action$, _) => {
        return action$.ofType('HERO_LOGGED_IN', 'REFRESH_HERO_STATS').mergeMap(
            (action: HeroLoggedInAction | RefreshHeroStatsAction) =>
                api.getHero(action.email)
                    .timeout(api.timeout, scheduler)
                    .map((h: IHeroDto) => createHeroReceivedAction(h))
                    .catch(err => Observable.of(createErrorOnGetHeroDetailsAction(err.message)))
        );
    };
}

export function setUpdateHeroStatsMechanism(scheduler?: IScheduler): Epic<Action, AppState> {
    return action$ =>
        action$.ofType('HERO_DETAILS_RECEIVED').delay(5000).map(
            (action: HeroDetailsReceivedAction) => createRefreshHeroStatsAction(action.data.hero.email));
}
export function submitHeroRegistration(api: IHeroApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return (action$, _) => {
        return action$.ofType('HERO_SUBMIT_REGISTRATION').mergeMap(
            (action: HeroSubmitRegistrationAction) =>
                api.register(action.form)
                    .timeout(api.timeout, scheduler)
                    .map(data => createHeroRegistrationDoneAction(action.form.email, action.form.password))
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
                            return createHeroLoggedInAction(action.email);
                        } else {
                            return createHeroLoginErrorAction("Login error: bad status " + data.status + " received");
                        }
                    })
                    .catch(error => Observable.of(createHeroLoginErrorAction(error)))
        );
    };
}

export function logoutHero(api: IHeroApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return (action$, _) => {
        return action$.ofType('HERO_LOG_OUT').mergeMap(
            (action: any) => {
                removeAuthenticationFromLocalStorage();
                return Observable.of(createHeroLoggedOutAction());
            }
        );
    };
}