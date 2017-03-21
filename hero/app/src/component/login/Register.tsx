import * as React from 'react';

import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from 'state';
import { 
    createHeroLoggedInAction, 
    createHeroLoginErrorAction, 
    createHeroExitRegisteringAction,
    createHeroRegisteringErrorMissingValueAction
 } from 'state/hero/heroAction'
import { heroApi } from 'api/heroApi'

interface IRegisterProps {
    onRegister:(email:string,password:string,nickname:string)=>void;
    onExitRegister: ()=>void;
    error: string;
}

const component = (props: IRegisterProps) => (
    <div>
        <div className="jumbotron">
            <h2>Inscription</h2>
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
            <div className="form-group">
                <label htmlFor="nickname">Nom du Héro</label>
                <input type="text" className="form-control" id="nickname" placeholder="Nickname"
                    ref={node => {this.nickname = node}}
                />
            </div>
            <button className="btn btn-primary" onClick={() => 
                props.onRegister(
                    this.email.value,
                    this.password.value,
                    this.nickname.value
                    )}>M'inscrire</button>
        </div>
        
        <div>
            <p className="text-center">
                <a href="#" className="btn btn-default" onClick={props.onExitRegister}>Aller au Login</a>
            </p>
        </div>
    </div>
);

export default connect(mapStateToProps, mapDispatchToProps)(component);

function mapStateToProps(state: AppState) {
    return {
        error: state.heroState.registerErrors
    };
}

function mapDispatchToProps(dispatch: Dispatch<any>) {
  return {
    onRegister: (email:string,password:string,nickname:string) => {
        if (email === '') {
            dispatch(createHeroRegisteringErrorMissingValueAction('Email'))
            return;
        }
        if (password === '') {
            dispatch(createHeroRegisteringErrorMissingValueAction('Mot de passe'))
            return;
        }
        if (nickname === '') {
            dispatch(createHeroRegisteringErrorMissingValueAction('Nom du Héro'))
            return;
        }
    },
    onExitRegister: () => dispatch(createHeroExitRegisteringAction())
  }
}