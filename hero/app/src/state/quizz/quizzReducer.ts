import {
    IQuizzState,
    QuizzAnswerSelectedAction,
    QuizzSubmittedAction,
    QuizzReceivedAction,
    ErrorOnQuizzAction
} from '.';
import { Action } from 'state/actions';

const onQuizzReceived = (state: IQuizzState, action: QuizzReceivedAction) => {
    return action.quizz ? { ...state, quizz: action.quizz, isQuizzSubmitted: false } : state;
};

const onSelectedAnswer = (state: IQuizzState, action: QuizzAnswerSelectedAction) =>
    ({ ...state, selectedAnswer: action.answerId });

const onQuizzSubmitted = (state: IQuizzState, action: QuizzSubmittedAction) =>
    ({ ...state, isQuizzSubmitted: true, isCorrectAnswer: action.response });

const onQuizzError = (state: IQuizzState, action: ErrorOnQuizzAction) =>
    ({ ...state, errors: action.error });

export function quizzReducer(state: IQuizzState = {}, action: Action): IQuizzState {
    switch (action.type) {
        case 'QUIZZ_RECEIVED':
            return onQuizzReceived(state, action);
        case 'QUIZZ_ANSWER_SELECTED':
            return onSelectedAnswer(state, action);
        case 'QUIZZ_SUBMITTED':
            return onQuizzSubmitted(state, action);
        case 'ERROR_ON_QUIZZ':
            return onQuizzError(state, action);
        default:
            return state;
    }
}