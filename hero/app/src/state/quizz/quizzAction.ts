import { IQuizzDto, IQuizzAnswerResult } from '.';

export type QuizzAction =
    // list of quizz actions
    | QuizzAnswerSelectedAction
    | QuizzReceivedAction
    | SubmitQuizzAction
    | RefreshQuizzAction
    | QuizzSubmittedAction
    | ErrorOnQuizzAction;


export interface QuizzReceivedAction {
    type: 'QUIZZ_RECEIVED';
    quizz: IQuizzDto;
};

export const createQuizzReceivedAction =
    (quizz: IQuizzDto): QuizzReceivedAction =>
        ({ type: 'QUIZZ_RECEIVED', quizz });


export interface QuizzAnswerSelectedAction {
    type: 'QUIZZ_ANSWER_SELECTED';
    answerId: number;
};

export const createQuizzAnswerSelectedAction =
    (answerId: number): QuizzAnswerSelectedAction =>
        ({ type: 'QUIZZ_ANSWER_SELECTED', answerId });

export interface SubmitQuizzAction {
    type: 'SUBMIT_QUIZZ';
    quizzId: number;
    answerId: number;
};

export const createSubmitQuizzAction =
    (quizzId: number, answerId: number): SubmitQuizzAction =>
        ({ type: 'SUBMIT_QUIZZ', quizzId, answerId });

export interface QuizzSubmittedAction {
    type: 'QUIZZ_SUBMITTED';
    result: IQuizzAnswerResult;
};

export const createQuizzSubmittedAction =
    (result: IQuizzAnswerResult): QuizzSubmittedAction =>
        ({ type: 'QUIZZ_SUBMITTED', result });

export interface RefreshQuizzAction {
    type: 'REFRESH_QUIZZ';
};

export const createRefreshQuizzAction =
    (): RefreshQuizzAction =>
        ({ type: 'REFRESH_QUIZZ' });

export interface ErrorOnQuizzAction {
    type: 'ERROR_ON_QUIZZ';
    error: string;
};

export const createErrorOnQuizzAction =
    (error: string): ErrorOnQuizzAction =>
        ({ type: 'ERROR_ON_QUIZZ', error });
