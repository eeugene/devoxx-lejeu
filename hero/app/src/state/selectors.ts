import { AppState } from '.';
import { IQuizz } from './quizz';
import { IHero, IHeroState } from './hero';

export function getQuizz(state: AppState): IQuizz {
    return state.quizzState.quizz;
}

export function getSelectedAnswer(state: AppState): number {
    return state.quizzState.selectedAnswer;
}

export function getIsQuizzSubmitted(state: AppState): boolean {
    return state.quizzState.isQuizzSubmitted;
}

export function getHero(state: AppState): IHero {
    return state.heroState.hero;
}

export function getHeroState(state: AppState): IHeroState {
    return state.heroState;
}