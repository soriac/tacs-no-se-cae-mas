import {AuthActionTypes, LOG_OUT, Role, SET_TOKEN, SET_USER} from './types';

export function setUser(email: string, role: Role): AuthActionTypes {
    return {
        type: SET_USER,
        payload: {
            email,
            role
        }
    };
}

export function setToken(token: string) {
    return {
        type: SET_TOKEN,
        payload: {
            token
        }
    };
}

export function logOut(): AuthActionTypes {
    return {
        type: LOG_OUT
    };
}
