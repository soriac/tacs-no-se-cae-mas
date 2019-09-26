export interface UserState {
    token: (string | undefined);
    email: (string | undefined);
    role: Role;
}

export enum Role {
    anon = 'ANONYMOUS',
    user = 'USER',
    admin = 'ADMIN'
}

export const SET_USER = 'AUTH/USER';
export const SET_TOKEN = 'AUTH/TOKEN';
export const LOG_OUT = 'AUTH/LOG_OUT';

interface SetTokenAction {
    type: typeof SET_TOKEN;
    payload: {
        token: string;
    };
}

interface SetUserAction {
    type: typeof SET_USER;
    payload: {
        email: string;
        role: Role;
    };
}

interface LogOutAction {
    type: typeof LOG_OUT;
}

export type AuthActionTypes = SetTokenAction | SetUserAction | LogOutAction
