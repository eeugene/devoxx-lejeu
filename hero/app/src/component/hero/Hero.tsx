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
    // stat: IHeroStats;
}

interface IHeroDispatchProps {
}

interface IHeroOwnProps {
}

type IHeroProps = IHeroOwnProps & IHeroPropsFromState & IHeroDispatchProps;

function getStat(stats:IHeroStats, key:string):number {
    if (stats) {
        let value = stats[key]
        if (value)
            return value;
    }
    return 0
}

const component = (props: IHeroProps) => {
    let stats = props.hero.heroStats
    console.log(stats)
    let currentRanking = getStat(stats, 'currentRanking')
    let bestRanking = getStat(stats, 'bestRanking')
    let wins = getStat(stats, 'wins')
    let losses = getStat(stats, 'losses')
    return (
    <div>
        <div className="hero-header">
            <Avatar id={props.hero.avatarId} />
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
                <p className="item-value">{wins}</p>
            </div>
            <div className="item">
                <span className='item-label'>Losses</span>
                <p className="item-value">{losses}</p>
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