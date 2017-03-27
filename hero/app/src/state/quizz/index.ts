export * from './quizzAction';
export * from './quizzReducer';
export * from './quizzEpic';

export interface IQuizzState {
    quizz?: IQuizzDto;
    selectedAnswer?: number;
    isQuizzSubmitted?: boolean;
    isCorrectAnswer?: boolean;
};

export interface IQuizzDto {
    quizz: IQuizz;
    quizzAnswered:boolean;
    correctAnswer:boolean;
    selectedAnswer:number;
}

export interface IQuizz {
    id: number;
    question: string;
    answers: IAnswer[];
}

interface IAnswer {
    id: number;
    answer: string;
}
