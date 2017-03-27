import * as React from 'react';
import * as cssmodules from 'react-css-modules';
import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from 'state';
import { getQuizz, getSelectedAnswer, getIsQuizzSubmitted, getIsCorrectAnswer } from 'state/selectors';
import { createQuizzAnswerSelectedAction, createSubmitQuizzAction, IQuizzDto, IQuizz } from 'state/quizz';

interface IQuizzPropsFromState {
    quizz?: IQuizzDto;
    selectedAnswer: number;
    isQuizzSubmitted: boolean;
    isCorrectAnswer: boolean;
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
    {props.quizz.quizz && props.quizz.quizz.answers &&
    <div className="quizz panel panel-default">
        <div className="panel-heading">
            <h3 className="panel-title">Bonus quizz</h3>
        </div>
        <div className="panel-body">
            <label>{props.quizz.quizz.question}</label>
            {props.quizz.quizz.answers &&
                props.quizz.quizz.answers.map(answer => (
                    <div className="radio answer" key={answer.id}>
                        <label>
                            <input type="radio"
                            checked={answer.id === (props.selectedAnswer || (props.quizz.quizzAnswered&&props.quizz.selectedAnswer))}
                            onChange={() => props.onAnswerSelected(answer.id)}
                            disabled={props.isQuizzSubmitted || props.quizz.quizzAnswered}
                            /> <span>{answer.answer}</span>
                        </label>
                    </div>
                ))
            }
            {props.quizz && props.quizz.quizz.answers &&
                <button className="btn btn-success"
                    onClick={() => props.onQuizzSubmit(props.quizz.quizz.id, props.selectedAnswer)}
                    disabled={!props.selectedAnswer || props.isQuizzSubmitted || props.quizz.quizzAnswered}>
                    submit
                </button>
            }
            {
                (props.isQuizzSubmitted || props.quizz.quizzAnswered) &&
                <div className="">
                    {props.isCorrectAnswer || (props.quizz.quizzAnswered && props.quizz.correctAnswer) ?
                    <span className="answer-feedback good-answer"> Bonne réponse :-)</span>
                    :
                    <span className="answer-feedback bad-answer"> Mauvaise réponse :-(</span>
                    }
                </div>
            }
        </div>
    </div>
    }
</div>
);

export const Quizz = cssmodules(component, styles);
export default connect(mapStateToProps, mapDispatchToProps)(Quizz);


function mapStateToProps(state: AppState) {
    const propsFromState: IQuizzPropsFromState = {
        quizz: { ...getQuizz(state) },
        selectedAnswer: getSelectedAnswer(state),
        isQuizzSubmitted: getIsQuizzSubmitted(state),
        isCorrectAnswer: getIsCorrectAnswer(state)
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