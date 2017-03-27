import * as React from 'react';

import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from 'state';
import {
    createHeroOpenRegisteringAction,
    createHeroSubmitLoginAction,
} from 'state/hero/heroAction';
import './login.css';

interface ILoginProps {
    onLogin:(email:string,password:string)=>void;
    onRegister: ()=>void;
    error: string;
}

const component = (props: ILoginProps) => (
    <div className="login">
        <h1 className="text-center">Login</h1>
        <div className="jumbotron">
            {props.error &&
                <div className="alert alert-danger">{props.error.toString()}</div>
            }
            <form className="form-horizontal" onSubmit={(event) => {
                event.preventDefault();
                return props.onLogin(this.email.value,this.password.value);
                }}>
            <div className="form-group">
                <label htmlFor="email" className="col-sm-3 label-control">Email</label>
                <div className="col-sm-9">
                <input type="text" className="form-control" id="email" placeholder="Email"
                    ref={node => {this.email = node;}}
                />
                </div>
            </div>
            <div className="form-group">
                <label htmlFor="password" className="col-sm-3 label-control">Mot de passe</label>
                <div className="col-sm-9">
                <input type="password" className="form-control" id="password" placeholder="Mot de passe"
                    ref={node => {this.password = node;}}
                />
                </div>
            </div>
            <div className="form-group">
                <div className="text-center">
                    <input type="submit" className="btn btn-primary" value="Connexion" />
                </div>
            </div>
            </form>
        </div>
        <div>
            <p className="text-center">
                <a href="#" className="btn btn-default" onClick={props.onRegister}>Je veux m'inscrire au jeu</a>
            </p>
        </div>
    </div>

);

export default connect(mapStateToProps, mapDispatchToProps)(component);

function mapStateToProps(state: AppState) {
    return {
        error: state.heroState.loginErrors
    };
}

function mapDispatchToProps(dispatch: Dispatch<any>) {
  return {
    onLogin: (email:string,password:string) => dispatch(createHeroSubmitLoginAction(email,password)),
    onRegister: () => dispatch(createHeroOpenRegisteringAction())
  };
}