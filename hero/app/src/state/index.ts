import {IQuizzState} from './quizz';
import {IHeroState} from './hero';

export interface AppState {
    quizzState: IQuizzState;
    heroState: IHeroState;
}