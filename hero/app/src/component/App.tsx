import * as React from 'react';
import { connect } from 'react-redux';

import { AppState } from 'state';
import Quizz from './quizz/Quizz';
import Hero from './hero/Hero';
import Login from './login/Login';
import Register from './login/Register';
import { IQuizz } from 'state/quizz';
import './app.css';

interface IAppProps {
    quizzReducer?: IQuizz;
    selectedAnswer?: number;
    isAuthenticated: boolean;
    isRegistering: boolean;
}

const component = (props: IAppProps) => (
    <div>
        <div className="header">
            <a href="http://www.aneo.eu"><img src="http://www.aneo.eu/assets/img/aneo.gif" className="text-center logo" /></a>
        {!props.isAuthenticated &&
            <h2 className="slogan">
                <p className="">
                    <p>Grand jeu <strong>Devoxx</strong>, soyez le meilleur <span className="orange">combattant</span></p>
                    <p>et tentez de gagner de <span className="orange">nombreux lots!</span></p>
                </p>
            </h2>
        }
        </div>
        {props.isAuthenticated &&
            <div>
                <Hero />
                <Quizz id={1} />
            </div>
        }
        {!props.isAuthenticated && props.isRegistering && <Register /> }
        {!props.isAuthenticated && !props.isRegistering && <Login /> }
    </div>
);

export default connect(mapStateToProps)(component);

function mapStateToProps(state: AppState): IAppProps {
    return {
        isAuthenticated: state.heroState.isLoggedIn,
        isRegistering: state.heroState.isRegistering
    };
}
