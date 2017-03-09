import * as React from 'react';
import * as cssmodules from 'react-css-modules';
import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from './../../state';
import { getQuizz } from './../../state/selectors';
import { createQuizzAnswerSelectedAction, IQuizz, initialState } from './../../state/quizz';

interface IQuizzPropsFromState {
    quizz: IQuizz;
}

interface IQuizzDispatchProps {
    onAction(): void;
}

interface IQuizzOwnProps {
    id: number;
}

type IQuizzProps = IQuizzOwnProps & IQuizzPropsFromState & IQuizzDispatchProps;

const styles = require('./Quizz.less');

const component = (props: IQuizzProps) => (
    <div>
        <label>TEST</label> 
        <div className="checkbox checkbox-primary">
            <input type="checkbox" />
        </div>
    </div>

);

export const Quizz = cssmodules(component, styles);
export default connect(mapStateToProps, mapDispatchToProps)(Quizz);


function mapStateToProps(state: AppState = { quizzState: initialState }) {
    const propsFromState: IQuizzPropsFromState = {
        quizz: getQuizz(state)
    };
    return propsFromState;
}

function mapDispatchToProps(dispatch: Dispatch<any>, { id }: IQuizzOwnProps): IQuizzDispatchProps {
    return {
        onAction() {
            dispatch(createQuizzAnswerSelectedAction(1, 1));
        }
    };
}