import * as React from 'react';
import './hero.css';

interface IAvatarProps {
    id:number;
}
export class Avatar extends React.Component<IAvatarProps, any> {
    constructor(props:IAvatarProps) {
      super(props);
      this.state = this.getState(this.props.id)
    }
    render() {
        return (
            <img src={this.state.url} className='hero-avatar'/>
        )
    }
    getState(id:number) {
        return {url:"http://localhost:8080/api/avatar/"+id}
    }
    componentWillReceiveProps(nextProps:IAvatarProps) {
        this.setState(this.getState(this.props.id))
    }
}