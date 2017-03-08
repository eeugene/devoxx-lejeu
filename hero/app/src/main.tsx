import * as React from 'react';
import * as ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
//import {configureStore} from 'state';
//import {Quizz} from 'component/quizz';

//let store = configureStore();

ReactDOM.render(
  <Provider>
    <h1> Hello world</h1>
  </Provider>,
  document.getElementById('main')
);
