import * as React from 'react';
import * as cssmodules from 'react-css-modules';
import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from 'state';
import { getQuizz, getSelectedAnswer, getIsQuizzSubmitted } from 'state/selectors';
import { createQuizzAnswerSelectedAction, createSubmitQuizzAction, IQuizz } from 'state/quizz';

interface IQuizzPropsFromState {
    quizz?: IQuizz;
    selectedAnswer: number;
    isQuizzSubmitted: boolean;
}

interface IQuizzDispatchProps {
    onAnswerSelected(answerId: number): void;
    onQuizzSubmit(quizzId: number, anserId: number): void;
}

interface IQuizzOwnProps {
    id: number;
}

type IQuizzProps = IQuizzOwnProps & IQuizzPropsFromState & IQuizzDispatchProps;

const styles = require('./Quizz.less');

const component = (props: IQuizzProps) => (
    <div className="quizz panel panel-default">
        <div className="panel-heading">
            <h3 className="panel-title">Bonus quizz</h3>
        </div>
        <div className="panel-body">
            <label>{props.quizz.question}</label>
            &&
            props.quizz.answers.map(answer => (
                <div className="radio answer" key={answer.id}>
                    <label><input type="radio"
                        checked={answer.id === props.selectedAnswer}
                        onChange={() => props.onAnswerSelected(answer.id)}
                        disabled={props.isQuizzSubmitted} /> <span>{answer.answer}</span>
                        </label>
                </div>
            ))
            &&
            <button className="btn btn-success"
                onClick={() => props.onQuizzSubmit(props.quizz.id, props.selectedAnswer)}
                disabled={!props.selectedAnswer || props.isQuizzSubmitted}>
                Envoyer
            </button>
        }
        {
            props.isQuizzSubmitted &&
            <h1> THANK YOU FOR YOUR ANSWER! </h1>
        }
    </div>
    </div>

);

export const Quizz = cssmodules(component, styles);
export default connect(mapStateToProps, mapDispatchToProps)(Quizz);


function mapStateToProps(state: AppState) {
    const propsFromState: IQuizzPropsFromState = {
        quizz: { ...getQuizz(state) },
        selectedAnswer: getSelectedAnswer(state),
        isQuizzSubmitted: getIsQuizzSubmitted(state)
    };
    return propsFromState;
}

function mapDispatchToProps(dispatch: Dispatch<any>, { id }: IQuizzOwnProps): IQuizzDispatchProps {
    return {
        onAnswerSelected(answerId: number) {
            dispatch(createQuizzAnswerSelectedAction(answerId));
        },
        onQuizzSubmit(quizzId: number, anserId: number) {
            dispatch(createSubmitQuizzAction(quizzId, anserId));
        }
    };
}