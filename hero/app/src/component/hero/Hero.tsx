import * as React from 'react';

import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from 'state';
import { getHero } from 'state/selectors';
import { IHero, IHeroStats } from 'state/hero';
import { Avatar } from './Avatar'
import './hero.css';

interface IHeroPropsFromState {
    hero: IHero;
}

interface IHeroDispatchProps {
}

interface IHeroOwnProps {
}

type IHeroProps = IHeroOwnProps & IHeroPropsFromState & IHeroDispatchProps;

function getStat(stats:IHeroStats, key:string):number {
    if (stats) {
        const value = Object.keys(stats)[key]
        if (value)
            return value;
    }
    return 0
}

const component = (props: IHeroProps) => {
    const stats = props.hero.heroStats
    const currentRanking = getStat(stats, 'currentRanking')
    const bestRanking = getStat(stats, 'bestRanking')
    const wins = getStat(stats, 'wins')
    const losses = getStat(stats, 'losses')
    const url = "http://localhost:8080/api/avatar/"+props.hero.avatarId
    return (
    <div>
        <div className="hero-header">
            <img src={url} className='hero-avatar' />
            <h1>{props.hero.firstname} {props.hero.lastname}</h1>
            <h2>{props.hero.nickname}</h2>
            <span>{props.hero.email}</span>
        </div>
        { props.hero.heroStats ? (
        <div className="flex">
            <div className="item">
                <span className='item-label'>Rank</span>
                <p className="item-value">{currentRanking}</p>
            </div>
            <div className="item">
                <span className='item-label'>Best Rank</span>
                <p className="item-value">{bestRanking}</p>
            </div>
            <div className="item">
                <span className='item-label'>Wins</span>
                <p className="item-value">{bestRanking}</p>
            </div>
            <div className="item">
                <span className='item-label'>Losses</span>
                <p className="item-value">{bestRanking}</p>
            </div>
        </div>
        ) : (
            <div></div>
        )
        }
    </div>
)}

export default connect(mapStateToProps, mapDispatchToProps)(component);

function mapStateToProps(state: AppState) {
    const propsFromState: IHeroPropsFromState = {
        hero: { ...getHero(state) }
    };
    return propsFromState;
}

function mapDispatchToProps(dispatch: Dispatch<any>, { }: IHeroOwnProps): IHeroDispatchProps {
    return {};
}