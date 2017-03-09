import { combineEpics, createEpicMiddleware} from 'redux-observable';
import { combineReducers, compose, createStore, applyMiddleware } from 'redux';
import { AppState } from '.';
import { quizzReducer, updateQuizz } from './quizz';
import {quizzApi} from './../api/quizz';

export function configureStore() {
    const rootReducer = combineReducers<AppState>(
        {
            quizzState: quizzReducer
            // reducers
        }
    );

    const rootEpic = combineEpics(updateQuizz(quizzApi)/* epics calls */);
    const epicMiddleware = createEpicMiddleware(rootEpic);

    const _compose = (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

    const enhancer = _compose(
        applyMiddleware(epicMiddleware)
    );

    const store = createStore<AppState>(rootReducer, enhancer);
    return store;
}