import {combineEpics, createEpicMiddleware} from 'redux-observable';
import {combineReducers, compose, createStore, applyMiddleware} from 'redux';
import {AppState} from '.'

export function configureStore() {
    const rootReducer = combineReducers<AppState>(
        {
            firstReducer: null,
            secondReducer: null
            // reducers
        }
    );

    const rootEpic = combineEpics(/* epics calls */);
    const epicMiddleware = createEpicMiddleware(rootEpic);

    const _compose = (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

    const enhancer = _compose(
        applyMiddleware(epicMiddleware)
    );

    const store = createStore<AppState>(rootReducer, enhancer);
    return store;
}