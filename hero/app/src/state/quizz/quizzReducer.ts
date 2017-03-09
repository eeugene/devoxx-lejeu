import { IQuizzState, QuizzAnswerSelectedAction, QuizzReceivedAction } from '.';
import { Action } from 'state/actions'

const onSelectedAnswer = (state: IQuizzState, action: QuizzAnswerSelectedAction) => ({ ...state, selectedAnswer: action.answerId });
const onQuizzReceived = (state: IQuizzState, action: QuizzReceivedAction) => ({ ...state, quizz: action.quizz });

export const initialState: IQuizzState = {
    quizz: {
        question: {
            id: undefined,
            label: undefined
        },
        answers: []
    },
    selectedAnswer: undefined
};

export function quizzReducer(state: IQuizzState = initialState, action: Action) {
    switch (action.type) {
        case 'QUIZZ_RECEIVED':
            return onQuizzReceived(state, action);
        case 'QUIZZ_ANSWER_SELECTED':
            return onSelectedAnswer(state, action);
        default:
            return state;
    }
}