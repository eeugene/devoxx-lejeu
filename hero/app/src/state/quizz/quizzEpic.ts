import { Observable, IScheduler, combineLatest, of } from 'rx';
import { Epic } from 'redux-observable';

import { AppReadyAction, Action } from 'state/actions';
import { AppState } from 'state';
import { IQuizz, createQuizzReceivedAction, createQuizzSubmittedAction, SubmitQuizzAction } from '.';

export interface IQuizzApi {
    getQuizz: () => Observable<IQuizz>;
    postQuizzAnswer: (quizzId: number, answerId: number) => Observable<number>;
    timeout: number;
}
export function getCurrentQuizz(api: IQuizzApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return action$ => {
        const quizz$ = api.getQuizz()
            .timeout(api.timeout, scheduler)
            .map((quizz: IQuizz) => createQuizzReceivedAction(quizz));

        return action$.ofType('HERO_LOGGED_IN', 'QUIZZ_SUBMITTED').mergeMap(() => quizz$);
    };
}

export function postQuizzAnswer(api: IQuizzApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return action$ => {

        return action$.ofType('SUBMIT_QUIZZ').mergeMap(
            (action:SubmitQuizzAction) => 
                api.postQuizzAnswer(action.quizzId, action.answerId)
                .timeout(api.timeout, scheduler)
                .map(_ => createQuizzSubmittedAction())
            );
    }
}