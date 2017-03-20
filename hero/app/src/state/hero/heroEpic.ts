import { Observable, IScheduler, combineLatest, of } from './../../rx';
import { Epic } from 'redux-observable';

import { AppReadyAction, Action } from 'state/actions';
import { AppState } from 'state';
import { IHero } from '.';
import { createHeroReceivedAction } from './heroAction';
import { IHeroApi } from 'api/heroApi';

export function getHeroDetails(api: IHeroApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return (action$, _) => {
        const h = api.getHero()
            .timeout(api.timeout, scheduler)
            .map((h: IHero) => createHeroReceivedAction(h));
          
        return action$.ofType('HERO_LOGGED_IN').mergeMap(() => h);
    };
}