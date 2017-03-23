import * as React from 'react';

import { Dispatch } from 'redux';
import { connect } from 'react-redux';
import { AppState } from 'state';
import { createHeroLoggedOutAction } from 'state/hero/heroAction'
import { removeAuthenticationFromLocalStorage } from 'state/hero/heroService'

interface ILogoutProps {
    onLogout:any;
}

const component = (props: ILogoutProps) => (
    <div>
        <button className="btn" onClick={props.onLogout}>log out</button>
    </div>
);

export default connect(mapStateToProps, mapDispatchToProps)(component);

function mapStateToProps(state: AppState) {
    return {};
}

function mapDispatchToProps(dispatch: Dispatch<any>) {
  return {
    onLogout: () => {
        removeAuthenticationFromLocalStorage()
        return dispatch(createHeroLoggedOutAction())
    }
  }
}