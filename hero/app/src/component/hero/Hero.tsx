import * as React from 'react';

import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from 'state';
import { getHero } from 'state/selectors';
import { IHero, IHeroStats } from 'state/hero';
import { Avatar } from './Avatar';
import Logout from 'component/login/Logout';
import './hero.css';

interface IHeroPropsFromState {
    hero: IHero;
}

interface IHeroDispatchProps {
}

interface IHeroOwnProps {
}

type IHeroProps = IHeroOwnProps & IHeroPropsFromState & IHeroDispatchProps;

function getStat(stats:IHeroStats, key:string):any {
    if (stats) {
        let value = stats[key];
        if (value)
            return value;
    }
    return 0;
}

function split(text:string, delimiter:string) {
    if (text) {
        return text.split(delimiter);
    }
    return [];
}

const component = (props: IHeroProps) => {
    const stats = props.hero.heroStats;
    const currentRanking = getStat(stats, 'currentRanking');
    const bestRanking = getStat(stats, 'bestRanking');
    const wins = getStat(stats, 'wins');
    const losses = getStat(stats, 'losses');
    const lastFiveBattles = split(getStat(stats, 'lastFiveBattles'), ";");
    return (
    <div>
        <div className="hero-header">
            <div className="hero-info">{props.hero.avatarId && <Avatar id={props.hero.avatarId} /> }</div>
            <div className="hero-info">
                <span className="nickname">{props.hero.nickname}</span>
            </div>
            <div className="hero-info">
                <span>
                    {props.hero.firstname} {props.hero.lastname}
                    <br/>{props.hero.email}
                </span>
            </div>
            <div className="hero-info"><Logout /></div>
        </div>
        { props.hero.heroStats &&
        <div className="flex">
            <div className="item">
                <div className="item-content">
                    <span className='item-label'>Rank</span>
                    <p className="item-value">{currentRanking}</p>
                </div>
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
            <div className="item">
                <span className='item-label'>Last Five Battles</span>
                <p className="item-label">
                    <ol>{lastFiveBattles.map(result => <li>{result}</li>)}</ol>
                </p>
            </div>
        </div>
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