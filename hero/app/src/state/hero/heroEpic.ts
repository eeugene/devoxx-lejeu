import { IScheduler } from 'rx';
import { Epic } from 'redux-observable';

import { Action } from 'state/actions';
import { AppState } from 'state';
import { IHero } from '.';
import { createHeroReceivedAction, HeroLoggedInAction } from './heroAction';
import { IHeroApi } from 'api/heroApi';

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
