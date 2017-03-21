import * as React from 'react';
import { connect } from 'react-redux';

import { AppState } from 'state';
import Quizz from './quizz/Quizz';
import Hero from './hero/Hero';
import Login from './login/Login';
import Logout from './login/Logout';

import { IQuizzState, IQuizz } from 'state/quizz';
import { getSelectedAnswer, getHeroState } from 'state/selectors';

interface IAppProps {
    quizzReducer?: IQuizz;
    selectedAnswer?: number;
    isAuthenticated: boolean;
}

const component = (props: IAppProps) => {
    let isLoggedIn = props.isAuthenticated;
    return (
    <div>
        {!isLoggedIn ? (
            <Login />
        ) : (
            <div>
                <Logout />
                <Hero />
                <Quizz id={1} />
            </div>
        )}
    </div>
)};

export default connect(mapStateToProps)(component);

function mapStateToProps(state: AppState): IAppProps {
    return {
        ...
        {isAuthenticated: state.heroState.isLoggedIn}
    };
}