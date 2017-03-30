import { Observable,ajax } from 'rx';
import { IQuizzApi, IQuizzDto, IQuizzAnswerResult } from 'state/quizz';
import { getAuthorizationHeader } from 'state/hero/heroService';

export const quizzApi: IQuizzApi = {
    getQuizz() {
        const header = getAuthorizationHeader();
        return ajax.getJSON<IQuizzDto>('api/quizz', header);
    },
    postQuizzAnswer(quizzId: number, answerId: number):Observable<IQuizzAnswerResult> {
        let headers = { 'Content-Type': 'application/json' };
        headers = Object.assign(headers, getAuthorizationHeader());
        return ajax.post('api/quizz', { quizzId, answerId }, headers).map(resp => resp.response);
    },
    timeout: 5000
};