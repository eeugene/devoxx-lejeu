import * as React from 'react';
import { connect } from 'react-redux';

import { AppState } from 'state';
import Quizz from './quizz/Quizz';
import Hero from './hero/Hero';
import Login from './login/Login';
import Logout from './login/Logout';
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
    <div>{props.isAuthenticated &&
        <div className="menu-bar clearfix">
            <ul>
                <li><a href='/'>Home</a></li>
                <li><a href='/leaderboard'>Leaderboard</a></li>
                <li className="pull-right"><a href='https://github.com/eeugene/devoxx-lejeu' className="github ">&nbsp;</a></li>
                <li className="header-nav-link pull-right"><Logout /></li>}
            </ul>
            <div className="aneo">
                <a href="http://www.aneo.eu">aneo</a>
            </div>
        </div>
        }
            
        {!props.isAuthenticated &&
        <div className="header">
            <div>
                <a href="http://www.aneo.eu"><img src="http://www.aneo.eu/assets/img/aneo.gif" className="text-center logo" /></a>
            </div>
            <h2 className="slogan">
                <p className="">
                    <p>Grand jeu <strong>Devoxx</strong>, soyez le meilleur <span className="orange">combattant</span></p>
                    <p>et tentez de gagner de <span className="orange">nombreux lots!</span></p>
                </p>
            </h2>
        </div>
        }
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
