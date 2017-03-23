import { IQuizzState, QuizzAnswerSelectedAction, QuizzSubmittedAction } from '.';
import { Action } from 'state/actions';

const onSelectedAnswer = (state: IQuizzState, action: QuizzAnswerSelectedAction) => ({ ...state, selectedAnswer: action.answerId });
const onQuizzSubmitted = (state: IQuizzState, action: QuizzSubmittedAction) => ({ ...state, isQuizzSubmitted: true });

export function quizzReducer(state: IQuizzState = {}, action: Action): IQuizzState {
    switch (action.type) {
        case 'QUIZZ_RECEIVED':
            return {
                quizz: action.quizz
            };
        case 'QUIZZ_ANSWER_SELECTED':
            return onSelectedAnswer(state, action);
        case 'QUIZZ_SUBMITTED':
            return onQuizzSubmitted(state, action);
        default:
            return state;
    }
}