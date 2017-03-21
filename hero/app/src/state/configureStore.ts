import { combineEpics, createEpicMiddleware} from 'redux-observable';
import { combineReducers, compose, createStore, applyMiddleware } from 'redux';
import { AppState } from '.';
import { quizzReducer, getCurrentQuizz, postQuizzAnswer } from './quizz';

import { quizzApi } from 'api/quizzApi';
import { heroApi } from 'api/heroApi';
import { getHeroDetails } from './hero/heroEpic';
import { heroReducer } from './hero/heroReducer';

export function configureStore() {
    const rootReducer = combineReducers<AppState>(
        {
            quizzState: quizzReducer,
            heroState: heroReducer,
            // reducers
        }
    );

    const rootEpic = combineEpics(
        getCurrentQuizz(quizzApi)/* epics calls */,
        getHeroDetails(heroApi),
        postQuizzAnswer(quizzApi)
    );

    const epicMiddleware = createEpicMiddleware(rootEpic);

    const _compose = (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

    const enhancer = _compose(
        applyMiddleware(epicMiddleware)
    );

    const store = createStore<AppState>(rootReducer, enhancer);
    return store;
}