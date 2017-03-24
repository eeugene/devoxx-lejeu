import { IQuizzState, QuizzAnswerSelectedAction, QuizzSubmittedAction, QuizzReceivedAction } from '.';
import { Action } from 'state/actions';

const onQuizzReceived = (state: IQuizzState, action: QuizzReceivedAction) => ({ ...state, quizz: action.quizz });
const onSelectedAnswer = (state: IQuizzState, action: QuizzAnswerSelectedAction) => ({ ...state, selectedAnswer: action.answerId });
const onQuizzSubmitted = (state: IQuizzState, action: QuizzSubmittedAction) => ({ ...state, isQuizzSubmitted: true });

export function quizzReducer(state: IQuizzState = {}, action: Action): IQuizzState {
    switch (action.type) {
        case 'QUIZZ_RECEIVED':
            return onQuizzReceived(state, action);
        case 'QUIZZ_ANSWER_SELECTED':
            return onSelectedAnswer(state, action);
        case 'QUIZZ_SUBMITTED':
            return onQuizzSubmitted(state, action);
        default:
            return state;
    }
}