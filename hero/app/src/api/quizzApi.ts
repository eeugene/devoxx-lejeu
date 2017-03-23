import { Observable, ajax } from 'rx';
import {IQuizzApi, IQuizz} from 'state/quizz'
import { getAuthorizationHeader } from 'state/hero/heroService'

export const quizzApi: IQuizzApi = {
    getQuizz() {
        const header = getAuthorizationHeader()
        return ajax.getJSON<IQuizz>('api/quizz', header);
    },
    postQuizzAnswer(quizzId: number, answerId: number) {
        let headers = {'Content-Type': 'application/json'}
        headers = Object.assign(headers, getAuthorizationHeader())
        return ajax.post('api/quizz', {quizzId, answerId}, headers).map(resp => resp.status)
    },
    timeout: 5000
};