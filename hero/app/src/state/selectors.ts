import {AppState} from '.';
import {IQuizz, IQuizzState} from './quizz';
import {IHero, IHeroState} from './hero';

export function getQuizz(state: AppState): IQuizz {
    return state.quizzState.quizz;
} 

export function getQuizzState(state: AppState): IQuizzState {
    return state.quizzState;
}

export function getHero(state: AppState): IHero {
    return state.heroState.hero;
}

export function getHeroState(state: AppState): IHeroState {
    return state.heroState;
}