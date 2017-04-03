import * as React from 'react';

import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from 'state';
import { createHeroLogoutAction } from 'state/hero/heroAction';

interface ILogoutProps {
    onLogout:any;
}

const component = (props: ILogoutProps) => (
    <a onClick={props.onLogout} href='#'>Log out</a>
);

export default connect(mapStateToProps, mapDispatchToProps)(component);

function mapStateToProps(state: AppState) {
    return {};
}

function mapDispatchToProps(dispatch: Dispatch<any>) {
  return {
    onLogout: () => {
        return dispatch(createHeroLogoutAction());
    }
  }
}