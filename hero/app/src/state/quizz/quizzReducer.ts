import { IQuizzState, QuizzAnswerSelectedAction, QuizzReceivedAction, IQuizz } from '.';
import { Action } from 'state/actions'

const onSelectedAnswer = (state: IQuizzState, action: QuizzAnswerSelectedAction) => ({ ...state, selectedAnswer: action.answerId });
//const onQuizzReceived = (state: IQuizzState, action: QuizzReceivedAction) => ({ ...state, quizz: action.quizz });


export function quizzReducer(state: IQuizzState = {}, action: Action):IQuizzState {
    switch (action.type) {
        case 'QUIZZ_RECEIVED':
        return {
            quizz: action.quizz
        }
        case 'QUIZZ_ANSWER_SELECTED':
            return onSelectedAnswer(state, action);
        default:
            return state;
    }
}