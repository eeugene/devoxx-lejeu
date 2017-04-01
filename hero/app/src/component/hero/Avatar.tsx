import * as React from 'react';
import './hero.css';
import { heroApi } from 'api/heroApi';

interface IAvatarProps {
    id:number;
    onClick?: (id:number) => void;
    getClassName?: string;
}

export class Avatar extends React.Component<IAvatarProps, any> {
    constructor(props:IAvatarProps) {
        super(props);
        this.state = this.getState(this.props.id);
    }
    render() {
        return (
            <img src={this.state.url}
                onClick={() => this.props.onClick(this.props.id)}
                className={this.props.getClassName} />
        );
    }
    getState(id:number) {
        const url = heroApi.getAvatarUrl(id);
        return { url };
    }
    componentWillReceiveProps(nextProps:IAvatarProps) {
        this.setState(this.getState(this.props.id));
    }
}