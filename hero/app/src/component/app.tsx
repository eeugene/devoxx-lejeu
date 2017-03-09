import * as React from 'react';
import { connect } from 'react-redux';

import { AppState } from 'state';
import Quizz from './quizz/Quizz';
import { IQuizzState, IQuizz } from './../state/quizz';
import { getQuizzState } from './../state/selectors';

interface IAppProps {
    quizzReducer?: IQuizz;
    selectedAnswer?: number;
}

const component = (props: IAppProps) => (
    <div>
        <Quizz id={1} />
    </div>
);

export default connect(mapStateToProps)(component);

function mapStateToProps(state: AppState): IAppProps {
    return {
        ...getQuizzState(state)
    };
}