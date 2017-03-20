import { Observable, ajax } from './../rx';
import {IQuizzApi, IQuizz} from 'state/quizz'
import { getAuthenticatedHeader } from 'state/hero/heroService'

export const quizzApi: IQuizzApi = {
    getQuizz() {
        return ajax.getJSON<IQuizz>('api/quizz', getAuthenticatedHeader());
    },
    timeout: 5000
};