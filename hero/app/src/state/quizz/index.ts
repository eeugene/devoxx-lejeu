export * from './quizzAction';
export * from './quizzReducer';
export * from './quizzEpic';

export interface IQuizzState {
    quizz?: IQuizz;
    selectedAnswer?: number;
};

export interface IQuizz {
    question: string;
    answers: string[];
}

// interface IQuestion {
//     id: number;
//     label: string;
// }

// interface IAnswer {
//     id: number;
//     label: string;
// }
