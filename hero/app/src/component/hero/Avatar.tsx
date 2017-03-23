import * as React from 'react';

interface IAvatarProps {
    id:number;
    text:string;
}
export class Avatar extends React.Component<IAvatarProps, any> {
    url:string;
    constructor(props:IAvatarProps) {
        console.log(props)
      super(props);
      this.url = "http://localhost:8080/api/avatar/"+this.props.id
    }
    render() {
        return (
            <img src={this.url} />
        )
    }
}