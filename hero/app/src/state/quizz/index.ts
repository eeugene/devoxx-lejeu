export * from './quizzAction';
export * from './quizzReducer';
export * from './quizzEpic';

export interface IQuizzState {
    quizz?: IQuizz;
    selectedAnswer?: number;
    isQuizzSubmitted?: boolean;
};

export interface IQuizz {
    id: number;
    question: string;
    answers: IAnswer[];
}

interface IAnswer {
    id: number;
    text: string;
}
