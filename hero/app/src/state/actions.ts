import {QuizzAction} from './quizz'
import {HeroAction} from './hero'

export interface AppReadyAction {
    type: 'APP_READY';
};

export function createAppReadyAction(): AppReadyAction {
    return { type: 'APP_READY' };
};

export type Action = // add the list of component actions
    | AppReadyAction
    | QuizzAction
    | HeroAction;
