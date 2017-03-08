import * as React from 'react';
import { connect } from 'react-redux';

import { AppState } from 'state';
import {Quizz} from './quizz/Quizz';

interface IAppProps {
    quizz: any;
}

const component = (props: IAppProps) => (
    <div>
        <Quizz />
    </div>
);

export default connect(mapStateToProps)(component);

function mapStateToProps(state: AppState):  IAppProps{
    return state.quizz;
}