import logger from 'redux-logger';
import {persistReducer, persistStore} from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import {applyMiddleware, combineReducers, createStore} from 'redux';

import {authReducer as auth} from './auth/reducer';
import {uiReducer as ui} from './ui/reducer';

const reducer = combineReducers({auth, ui});

const persistanceConfig = {
    key: 'ken',
    storage,
    whitelist: ['auth']
};

const persistentReducer = persistReducer(persistanceConfig, reducer);

export type StoreState = ReturnType<typeof reducer>

export const store = createStore(persistentReducer, applyMiddleware(logger));
export const persistor = persistStore(store);

