import * as React from 'react';

import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from 'state';
import { createHeroLoggedInAction, createHeroLoginErrorAction, createHeroOpenRegisteringAction } from 'state/hero/heroAction'
import { heroApi } from 'api/heroApi'
import { login } from 'state/hero/heroService'

interface ILoginProps {
    onLogin:(email:string,password:string)=>void;
    onRegister: ()=>void;
    error: string;
}

const component = (props: ILoginProps) => (
    <div>
        
        <div className="jumbotron">
            <h2>Login</h2>
            { 
                (props.error != null) ? (
                <div className="alert alert-danger">{props.error.toString()}</div>
            ) : (
                <div></div>
            )}
            <div className="form-group">
                <label htmlFor="email">Email</label>
                <input type="text" className="form-control" id="email" placeholder="Email"
                    ref={node => {this.email = node}}
                />
            </div>
            <div className="form-group">
                <label htmlFor="password">Mot de passe</label>
                <input type="password" className="form-control" id="password" placeholder="Mot de passe"
                    ref={node => {this.password = node}}
                />
            </div>
            <button className="btn btn-primary" onClick={() => 
                props.onLogin(this.email.value,this.password.value)}>Me connecter</button>
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
    onLogin: (email:string,password:string) => login(email, password,
                () => dispatch(createHeroLoggedInAction(email)), (error:any) => dispatch(createHeroLoginErrorAction(error))),
    onRegister: () => dispatch(createHeroOpenRegisteringAction())
  }
}