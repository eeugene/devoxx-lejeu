import { IQuizz } from '.';
export type QuizzAction =
    // list of quizz actions
    | QuizzAnswerSelectedAction
    | QuizzReceivedAction
    | SubmitQuizzAction
    | RefreshQuizzAction
    | QuizzSubmittedAction;


export interface QuizzReceivedAction {
    type: 'QUIZZ_RECEIVED';
    quizz: IQuizz;
};

export const createQuizzReceivedAction =
    (quizz: IQuizz): QuizzReceivedAction =>
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
};

export const createQuizzSubmittedAction =
    (): QuizzSubmittedAction =>
        ({ type: 'QUIZZ_SUBMITTED' });

export interface RefreshQuizzAction {
    type: 'REFRESH_QUIZZ';
};

export const createRefreshQuizzAction =
    (): RefreshQuizzAction =>
        ({ type: 'REFRESH_QUIZZ' });
