import { Observable, ajax } from './../rx';

import {IQuizzApi, IQuizz} from 'state/quizz'

export const quizzApi: IQuizzApi = {
    getQuizz() {
        return ajax.getJSON<IQuizz>('api/quizz');
    },
    timeout: 5000
};