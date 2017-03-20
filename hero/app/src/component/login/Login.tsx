import * as React from 'react';

import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from 'state';
import { createHeroLoggedInAction, createHeroLoginErrorAction } from 'state/hero/heroAction'
import { heroApi } from 'api/heroApi'
import { setTokenInLocalStorage } from 'state/hero/heroService'

interface ILoginProps {
    onLogin:(email:string,password:string)=>void;
    error: string;
}

const component = (props: ILoginProps) => (
    <div className="jumbotron">
        { 
            (props.error != null) ? (
            <div className="alert alert-danger">{props.error.toString()}</div>
        ) : (
            <div></div>
        )}
        <div className="form-group">
            <label htmlFor="email">Email address</label>
            <input type="text" className="form-control" id="email" placeholder="Email"
                ref={node => {this.email = node}}
            />
        </div>
        <div className="form-group">
            <label htmlFor="password">Email address</label>
            <input type="password" className="form-control" id="password" placeholder="Password"
                ref={node => {this.password = node}}
            />
        </div>
        <button className="btn" onClick={()=>props.onLogin(this.email.value,this.password.value)}>log in</button>
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
    onLogin: (email:string,password:string) => {
        heroApi.login(email,password)
        .subscribe(
            data => {
                if (data.status === 201 || data.status === 200) {
                    setTokenInLocalStorage(data.response.token)
                    dispatch(createHeroLoggedInAction())
                } else {
                    dispatch(createHeroLoginErrorAction("Login error: bad status " + data.status + " received"))
                }
            },
            error => {
                console.log("Login error: " + error)
                dispatch(createHeroLoginErrorAction(error))
            }
        )
    }
  }
}