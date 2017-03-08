import * as React from 'react';

interface IQuizzFromStateProps {
    question: string;
    ansewers: string[];
}


export class Quizz extends React.Component<IQuizzFromStateProps, any> {
    constructor(props: IQuizzFromStateProps) {
        super(props);
    }

    render() {
        return (
            <p className="navbar-text navbar-right">Signed in as <a href="#" className="navbar-link">Mark Otto</a></p>
        )
    }
}