import {AppState} from '.';
import {IQuizz, IQuizzState} from './quizz';

export function getQuizz(state: AppState): IQuizz {
    return state.quizzState.quizz;
} 

export function getQuizzState(state: AppState): IQuizzState {
    return state.quizzState;
}