import { Observable, IScheduler, combineLatest, of } from './../../rx';
import { Epic } from 'redux-observable';

import { AppReadyAction, Action } from 'state/actions';
import { AppState } from 'state';
import { IQuizz, createQuizzReceivedAction } from '.';

export interface IQuizzApi {
    getQuizz: () => Observable<IQuizz>;
    timeout: number;
}
export function updateQuizz(api: IQuizzApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return (action$, _) => {
        const quizz$ = api.getQuizz()
            .timeout(api.timeout, scheduler)
            .map((quizz: IQuizz) => createQuizzReceivedAction(quizz));

        return action$.ofType('APP_READY').mergeMap(() => quizz$);
    };
}