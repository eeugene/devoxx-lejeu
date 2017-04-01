import * as React from 'react';
import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from 'state';
import { IAvatar, IHeroRegistrationForm } from 'state/hero';
import {
    createHeroExitRegisteringAction,
    createHeroRegisteringErrorMissingValueAction,
    createHeroSubmitRegistrationAction
 } from 'state/hero/heroAction';
import { heroApi } from 'api/heroApi';
import './register.css';
import { Avatar } from 'component/hero/Avatar';

interface IRegisterProps {
    onExitRegister: ()=>void;
    handleSubmit:(event:any)=>void;
    errors: string[];
}

interface IStateAvatars{
    avatars?:IAvatar[];
}
class Avatars extends React.Component<any,IStateAvatars>{

    constructor(props:any) {
        super(props);
        this.state = {avatars: []};
        heroApi.getAvatars()
        .subscribe(
            data => this.setState({avatars: data})
        );
    }

    selectAvatar(id:number) {
        const {avatars} = this.state;
        const newAvatars = avatars.map( (avatar) => {
            return avatar.id === id ?
                Object.assign({}, avatar, {isSelected: true}) :
                Object.assign({}, avatar, {isSelected: false});
        });
        this.setState({avatars: newAvatars });
        this.props.onSelectAvatar(id);
    }

    render() {
        return (
            <div className="form-group">
                <label className="col-sm-3">Choisi ton avatar </label>
                <div className="col-sm-9">
                {
                    this.state.avatars.map(avatar => (
                            <Avatar
                                key={avatar.id}
                                id={avatar.id}
                                onClick={(id) => this.selectAvatar(id)}
                                getClassName={(avatar.isSelected?'avatar-selected':'avatar')}
                            />
                    ))
                }
                </div>
            </div>
        )
    }
}

const component = (props: IRegisterProps) => (
    <div className="register">
        <h2 className="text-center">Inscription au jeu</h2>
        <div className="jumbotron">    
            {props.errors &&
                <div className="alert alert-danger">
                    {props.errors.map(e => <div>{e}</div>)}
                </div>
            }
            <form onSubmit={(event) => props.handleSubmit(event)} className="form-horizontal">
            <div className="form-group">
                <label htmlFor="lastName" className="col-sm-3 control_label">Nom</label>
                <div className="col-sm-9">
                <input type="text" className="form-control" id="lastName" placeholder="Nom"
                    ref={node => {this.lastName = node;}}
                />
                </div>
            </div>
            <div className="form-group">
                <label htmlFor="firtsName" className="col-sm-3 control_label">Prénom</label>
                <div className="col-sm-9">
                <input type="text" className="form-control" id="firstName" placeholder="Prénom"
                    ref={node => {this.firstName = node;}}
                />
                </div>
            </div>
            <div className="form-group">
                <label htmlFor="email" className="col-sm-3 control_label">Email</label>
                <div className="col-sm-9">
                <input type="text" className="form-control" id="email" placeholder="Email"
                    ref={node => {this.email = node;}}
                />
                </div>
            </div>
            <div className="form-group">
                <label htmlFor="password" className="col-sm-3 control_label">Mot de passe</label>
                <div className="col-sm-9">
                <input type="password" className="form-control" id="password" placeholder="Mot de passe"
                    ref={node => {this.password = node;}}
                />
                </div>
            </div>
            <hr />
            <div className="form-group">
                <label htmlFor="nickname" className="col-sm-3 control_label">Nom du Héro</label>
                <div className="col-sm-9">
                <input type="text" className="form-control" id="nickname" placeholder="Nickname"
                    ref={node => {this.nickname = node;}}
                />
                </div>
            </div>
            
            <input type="hidden" id="avatarId" ref={node => {this.avatarId = node;}} />
            <Avatars onSelectAvatar={(id:string) => this.avatarId.value = id} />

            <div className="register-button">
                <div className="text-center">
                <input type="submit" className="btn btn-primary btn-block" value="S'inscrire" />
                </div>
            </div>
            </form>
        </div>
        
        <div>
            <p className="text-center">
                <a href="#" className="btn btn-default" onClick={props.onExitRegister}>Aller au Login</a>
            </p>
        </div>
    </div>
)

export default connect(mapStateToProps, mapDispatchToProps)(component);

function mapStateToProps(state: AppState) {
    return {
        errors: state.heroState.registerErrors
    };
}

function mapDispatchToProps(dispatch: Dispatch<any>) {
  return {
    onExitRegister: () => dispatch(createHeroExitRegisteringAction()),
    handleSubmit: (event:any) => {
        event.preventDefault();
        const email = event.target.querySelector('#email').value;
        const firstname = event.target.querySelector('#firstName').value;
        const lastname = event.target.querySelector('#lastName').value;
        const password = event.target.querySelector('#password').value;
        const nickname = event.target.querySelector('#nickname').value;
        const avatarId = event.target.querySelector('#avatarId').value;

        const heroForm = {email,password,firstname,lastname,nickname, avatarId};
        const formErrors = validateForm(heroForm);
        if (formErrors.length == 0) {
            dispatch(createHeroSubmitRegistrationAction(heroForm));
        } else {
            dispatch(createHeroRegisteringErrorMissingValueAction(formErrors));
        }
    }
  };
}

function validateForm(form:IHeroRegistrationForm) {
    let errors:string[] = [];
    Object.keys(form).forEach(k => {
        if (form[k] === '') {
            errors.push('Le champ ' + k + ' est manquant');
        }
    });
    return errors;
}