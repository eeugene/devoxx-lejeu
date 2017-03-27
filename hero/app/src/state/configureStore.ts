import { combineEpics, createEpicMiddleware} from 'redux-observable';
import { combineReducers, compose, createStore, applyMiddleware } from 'redux';
import { AppState } from '.';
import { quizzReducer, getCurrentQuizz, postQuizzAnswer, setUpdateMechanism } from './quizz';

import { quizzApi } from 'api/quizzApi';
import { heroApi } from 'api/heroApi';
import { getHeroDetails, loginHero, logoutHero, submitHeroRegistration } from './hero/heroEpic';
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
        getHeroDetails(heroApi),
        submitHeroRegistration(heroApi),
        loginHero(heroApi),
        logoutHero(heroApi),
        getCurrentQuizz(quizzApi),
        postQuizzAnswer(quizzApi),
        //setUpdateMechanism()
    );

    const epicMiddleware = createEpicMiddleware(rootEpic);

    const _compose = (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

    const enhancer = _compose(
        applyMiddleware(epicMiddleware)
    );

    const store = createStore<AppState>(rootReducer, enhancer);
    return store;
}