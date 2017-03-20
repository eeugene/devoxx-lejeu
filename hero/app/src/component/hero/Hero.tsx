import * as React from 'react';

import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from 'state';
import { getHero } from '../../state/selectors';
import { IHero } from '../../state/hero';

interface IHeroPropsFromState {
    hero: IHero;
}

interface IHeroDispatchProps {
    onAction(): void;
}

interface IHeroOwnProps {
}

type IHeroProps = IHeroOwnProps & IHeroPropsFromState & IHeroDispatchProps;

const component = (props: IHeroProps) => (
    <div>
        <h1>{props.hero.firstname} {props.hero.lastname}</h1>
        <h2>{props.hero.nickname}</h2>
        <span>{props.hero.email}</span>
    </div>

);

export default connect(mapStateToProps, mapDispatchToProps)(component);

function mapStateToProps(state: AppState) {
    const propsFromState: IHeroPropsFromState = {
        hero: { ...getHero(state) }
    };
    return propsFromState;
}

function mapDispatchToProps(dispatch: Dispatch<any>, { }: IHeroOwnProps): IHeroDispatchProps {
    return {
        onAction() {
            //dispatch(createQuizzAnswerSelectedAction(1, 1));
        }
    };
}