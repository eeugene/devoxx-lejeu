import * as React from 'react';
import { connect } from 'react-redux';

import { AppState } from 'state';
import Quizz from './quizz/Quizz';
import Hero from './hero/Hero';
import Login from './login/Login';
import Logout from './login/Logout';
import Register from './login/Register';

import { IQuizz } from 'state/quizz';

interface IAppProps {
    quizzReducer?: IQuizz;
    selectedAnswer?: number;
    isAuthenticated: boolean;
    isRegistering: boolean;
}

const component = (props: IAppProps) => {
    let isLoggedIn = props.isAuthenticated;
    let isRegistering = props.isRegistering;
    return (
        <div>
            {!isLoggedIn ?
                isRegistering ? (
                    <Register />
                ) : (
                        <Login />
                    ) : (
                    <div>
                        <Logout />
                        <Hero />
                        <Quizz id={1} />
                    </div>
                )}
        </div>
    );
};

export default connect(mapStateToProps)(component);

function mapStateToProps(state: AppState): IAppProps {
    return {
        isAuthenticated: state.heroState.isLoggedIn,
        isRegistering: state.heroState.isRegistering
    };
}