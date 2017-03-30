import { Observable, IScheduler } from 'rx';
import { Epic } from 'redux-observable';

import { Action } from 'state/actions';
import { AppState } from 'state';
import {
    IQuizzDto,
    createQuizzReceivedAction,
    createQuizzSubmittedAction,
    SubmitQuizzAction,
    createRefreshQuizzAction,
    createErrorOnQuizzAction
} from '.';

export interface IQuizzApi {
    getQuizz: () => Observable<IQuizzDto>;
    postQuizzAnswer: (quizzId: number, answerId: number) => Observable<boolean>;
    timeout: number;
}
export function getCurrentQuizz(api: IQuizzApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return action$ => {

        return action$.ofType('HERO_LOGGED_IN', 'REFRESH_QUIZZ').mergeMap(
            (action) =>
                api.getQuizz()
                    .timeout(api.timeout, scheduler)
                    .map((quizz: IQuizzDto) => createQuizzReceivedAction(quizz))
                    .catch(err => Observable.of(createErrorOnQuizzAction(err.message)))
        );
    };
}

export function setUpdateMechanism(scheduler?: IScheduler): Epic<Action, AppState> {
    return action$ => {
        return action$.ofType('QUIZZ_RECEIVED').delay(5000).map(() => createRefreshQuizzAction());
    };
}

export function postQuizzAnswer(api: IQuizzApi, scheduler?: IScheduler): Epic<Action, AppState> {
    return action$ => {

        return action$.ofType('SUBMIT_QUIZZ').mergeMap(
            (action: SubmitQuizzAction) =>
                api.postQuizzAnswer(action.quizzId, action.answerId)
                    .timeout(api.timeout, scheduler)
                    .map((response: boolean) => createQuizzSubmittedAction(response))
                    .catch((err, caught) => Observable.of(createErrorOnQuizzAction(err.message)))
        );
    };
}