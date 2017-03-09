import {AppState} from '.';
import {IQuizz, initialState} from './quizz';

export function getQuizz(state: AppState): IQuizz {
    return state.quizzState? state.quizzState.quizz : initialState.quizz;
} 