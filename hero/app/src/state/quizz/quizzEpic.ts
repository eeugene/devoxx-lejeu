import { Observable, IScheduler } from 'rx';
import { Epic } from 'redux-observable';

import { Action } from 'state/actions';
import { AppState } from 'state';
import {
    IQuizz,
    createQuizzReceivedAction,
    createQuizzSubmittedAction,
    SubmitQuizzAction,
    createRefreshQuizzAction
} from '.';

export interface IQuizzApi {
    getQuizz: () => Observable<IQuizz>;
    postQuizzAnswer: (quizzId: number, answerId: number) => Observable<number>;
    timeout: number;
}
export function getCurrentQuizz(api: IQuizzApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return action$ => {

        return action$.ofType('HERO_LOGGED_IN', 'REFRESH_QUIZZ').mergeMap(
            (action) =>
                api.getQuizz()
                    .timeout(api.timeout, scheduler)
                    .map((quizz: IQuizz) => createQuizzReceivedAction(quizz))
        );
    };
}

export function setUpdateMechanism(scheduler?: IScheduler): Epic<Action, AppState> {
    return action$ => {
        return action$.ofType('QUIZZ_RECEIVED').delay(15000).map(() =>createRefreshQuizzAction());
    }
}

export function postQuizzAnswer(api: IQuizzApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return action$ => {

        return action$.ofType('SUBMIT_QUIZZ').mergeMap(
            (action: SubmitQuizzAction) =>
                api.postQuizzAnswer(action.quizzId, action.answerId)
                    .timeout(api.timeout, scheduler)
                    .map(_ => createQuizzSubmittedAction())
        );
    };
}