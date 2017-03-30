import { AppState } from '.';
import { IQuizzDto } from './quizz';
import { IHero, IHeroState } from './hero';

export function getQuizz(state: AppState): IQuizzDto {
    return state.quizzState.quizz;
}

export function getSelectedAnswer(state: AppState): number {
    return state.quizzState.selectedAnswer;
}

export function getIsQuizzSubmitted(state: AppState): boolean {
    return state.quizzState.isQuizzSubmitted;
}

export function getIsCorrectAnswer(state: AppState): boolean {
    return state.quizzState.isCorrectAnswer;
}

export function getErrors(state: AppState): string {
    return state.quizzState.errors;
}

export function getHero(state: AppState): IHero {
    return state.heroState.hero;
}

export function getHeroState(state: AppState): IHeroState {
    return state.heroState;
}