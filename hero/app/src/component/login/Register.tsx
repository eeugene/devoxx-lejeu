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

interface IRegisterProps {
    onExitRegister: ()=>void;
    handleSubmit:(event:any)=>void;
    errors: string[];
}

interface IPropsAvatar{
    avatar:IAvatar;
    onSelectAvatar:(id:string)=>void;
}
class Avatar extends React.Component<IPropsAvatar, any> {
    url:string;
    constructor(props : IPropsAvatar) {
      super(props);
      this.url = "http://localhost:8080/api/avatar/"+this.props.avatar.id;
    }
    render() {
        return (
            <img src={this.url}
                onClick={()=>this.props.onSelectAvatar(this.props.avatar.id)}
                className={(this.props.avatar.isSelected?'avatar-selected':'avatar')} />
        )
    }
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

    selectAvatar(id:string) {
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
            <div>
                Liste des avatars
                {
                    this.state.avatars.map(a => (
                        <Avatar avatar={a} onSelectAvatar={(id) => this.selectAvatar(id)}/>
                    ))
                }
            </div>
        )
    }
}

const component = (props: IRegisterProps) => (
    <div>
        <div className="jumbotron">
            <h2>Inscription</h2>
            {props.errors &&
                <div className="alert alert-danger">
                    {props.errors.map(e => <div>{e}</div>)}
                </div>
            }
            <form onSubmit={(event) => props.handleSubmit(event)}>
            <div className="form-group">
                <label htmlFor="lastName">Nom</label>
                <input type="text" className="form-control" id="lastName" placeholder="Nom"
                    ref={node => {this.lastName = node;}}
                />
            </div>
            <div className="form-group">
                <label htmlFor="firtsName">Prénom</label>
                <input type="text" className="form-control" id="firstName" placeholder="Prénom"
                    ref={node => {this.firstName = node;}}
                />
            </div>
            <div className="form-group">
                <label htmlFor="email">Email</label>
                <input type="text" className="form-control" id="email" placeholder="Email"
                    ref={node => {this.email = node;}}
                />
            </div>
            <div className="form-group">
                <label htmlFor="password">Mot de passe</label>
                <input type="password" className="form-control" id="password" placeholder="Mot de passe"
                    ref={node => {this.password = node;}}
                />
            </div>
            <hr />
            <div className="form-group">
                <label htmlFor="nickname">Nom du Héro</label>
                <input type="text" className="form-control" id="nickname" placeholder="Nickname"
                    ref={node => {this.nickname = node;}}
                />
            </div>
            
            <input type="hidden" id="avatarId" ref={node => {this.avatarId = node;}} />
            <Avatars onSelectAvatar={(id:string) => this.avatarId.value = id} />

            <input type="submit" className="btn btn-primary" value="S'inscrire" />
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