import { IQuizzDto } from '.';
export type QuizzAction =
    // list of quizz actions
    | QuizzAnswerSelectedAction
    | QuizzReceivedAction
    | SubmitQuizzAction
    | RefreshQuizzAction
    | QuizzSubmittedAction;


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
    response:boolean;
};

export const createQuizzSubmittedAction =
    (response:boolean): QuizzSubmittedAction =>
        ({ type: 'QUIZZ_SUBMITTED', response });

export interface RefreshQuizzAction {
    type: 'REFRESH_QUIZZ';
};

export const createRefreshQuizzAction =
    (): RefreshQuizzAction =>
        ({ type: 'REFRESH_QUIZZ' });
