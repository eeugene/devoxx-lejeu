import { IQuizz } from '.';
export type QuizzAction =
    // list of quizz actions
    | QuizzAnswerSelectedAction
    | QuizzReceivedAction;


export interface QuizzReceivedAction {
    type: 'QUIZZ_RECEIVED';
    quizz: IQuizz;
};

export const createQuizzReceivedAction =
    (quizz: IQuizz): QuizzReceivedAction =>
        ({ type: 'QUIZZ_RECEIVED', quizz });


export interface QuizzAnswerSelectedAction {
    type: 'QUIZZ_ANSWER_SELECTED';
    quizzId: number;
    answerId: number;
};

export const createQuizzAnswerSelectedAction =
    (quizzId: number, answerId: number): QuizzAnswerSelectedAction =>
        ({ type: 'QUIZZ_ANSWER_SELECTED', quizzId, answerId });