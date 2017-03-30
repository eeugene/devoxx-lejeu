import 'core-js/shim';

import * as React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import { configureStore } from 'state/configureStore';
import App from 'component/App';
import { createAppReadyAction } from 'state/actions';
import { isHeroAuthenticated, getAuthenticationFromLocalStorage } from 'state/hero/heroService';
import { createHeroLoggedInAction } from 'state/hero/heroAction';

import 'bootstrap/dist/css/bootstrap.min.css';

function bootstrap() {
  let store = configureStore();

  render(
    <Provider store={store}>
      <App />
    </Provider>,
    document.getElementById('main')
  );

  store.dispatch(createAppReadyAction());
  if(isHeroAuthenticated()) {
    store.dispatch(createHeroLoggedInAction(getAuthenticationFromLocalStorage().email));
  }
}

bootstrap();