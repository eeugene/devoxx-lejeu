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
    <div>
        <label>{props.quizz.question}</label>
        {props.quizz.answers &&
            props.quizz.answers.map(answer => (
                <div className="radio" key={answer.id}>
                    <div><input type="radio"
                        checked={answer.id === props.selectedAnswer}
                        onChange={() => props.onAnswerSelected(answer.id)} /> {answer.answer}
                    </div>
                </div>
            ))
        }
        { props.quizz && props.quizz.answers ? (
            <button className="btn btn-success"
                onClick={() => props.onQuizzSubmit(props.quizz.id, props.selectedAnswer)}
                disabled={!props.selectedAnswer || props.isQuizzSubmitted}>
                submit
            </button>
        ) : (
            <div></div>            
            )
        }
        {
            props.isQuizzSubmitted &&
            <h1> THANK YOU FOR YOUR ANSWER </h1>
        }
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
            dispatch(createSubmitQuizzAction(quizzId, anserId))
        }
    };
}