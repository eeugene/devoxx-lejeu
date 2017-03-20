import * as React from 'react';
import * as cssmodules from 'react-css-modules';
import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from 'state';
import { getQuizz } from 'state/selectors';
import { createQuizzAnswerSelectedAction, IQuizz } from 'state/quizz';

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
        <label>{props.quizz.question}</label>
        <div className="radio">
            {props.quizz.answers &&
                props.quizz.answers.map(answer => (<div><input type="radio" /> {answer }</div>))}
        </div>
    </div>

);

export const Quizz = cssmodules(component, styles);
export default connect(mapStateToProps, mapDispatchToProps)(Quizz);


function mapStateToProps(state: AppState) {
    console.log('styles', styles);
    const propsFromState: IQuizzPropsFromState = {
        quizz: { ...getQuizz(state) }
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