import { Observable, ajax } from 'rx';
import {IQuizzApi, IQuizz} from 'state/quizz'
import { getAuthenticatedHeader } from 'state/hero/heroService'

export const quizzApi: IQuizzApi = {
    getQuizz() {
        return ajax.getJSON<IQuizz>('api/quizz', getAuthenticatedHeader());
    },
    postQuizzAnswer(quizzId: number, answerId: number) {
        return ajax.post('api/quizz', {quizzId, answerId}).map(resp => resp.status)
    },
    timeout: 5000
};