export type QuizzAction = 
// list of quizz actions
    | QuizzAnswerSelectedAction;


export interface QuizzAnswerSelectedAction {
    type: 'QUIZZ_ANSWER_SELECTED';
    quizzId: number;
    answerId: number;
}

export const createQuizzAnswerSelectedAction =
    (quizzId: number, answerId: number): QuizzAnswerSelectedAction =>
        ({ type: 'QUIZZ_ANSWER_SELECTED', quizzId, answerId })