import {AuthActionTypes, LOG_OUT, SET_TOKEN, SET_USER, UserState} from './types';
import {Role} from '../../api/types';

const initialState: UserState = {
    token: undefined,
    email: undefined,
    role: Role.anon
};

export function authReducer(state = initialState, action: AuthActionTypes): UserState {
    switch (action.type) {
        case SET_TOKEN:
            return {
                ...state,
                token: action.payload.token
            };
        case SET_USER:
            return {
                ...state,
                email: action.payload.email,
                role: action.payload.role,
            };
        case LOG_OUT:
            return initialState;
        default:
            return state;
    }
}
