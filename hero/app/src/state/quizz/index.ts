export * from './quizzAction';
export * from './quizzReducer';
export * from './quizzEpic';

export interface IQuizzState {
    quizz: IQuizz;
    selectedAnswer: number;
};

export interface IQuizz {
    question: IQuestion;
    answers: IAnswer[];
}

interface IQuestion {
    id: number;
    label: string;
}

interface IAnswer {
    id: number;
    label: string;
}
