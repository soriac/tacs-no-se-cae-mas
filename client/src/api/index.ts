import {Role} from '../state/auth/types';
import {store} from '../state';

const BASE_URL = 'http://localhost:8080';

function getToken(): string {
    const token = store.getState().auth.token;
    return token || '';
}

const LOGIN = () => `${BASE_URL}/login`;

export function login(email: string, password: string) {
    return fetch(LOGIN(), {
        method: 'POST',
        body: JSON.stringify({email, password})
    });
}

export type loginPayload = {
    message: string
    error: string | null
    data: {
        role: string
        email: string
    }
}

const ME = () => `${BASE_URL}/users/me`;

export function me() {
    return fetch(ME(), {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${getToken()}`
        }
    });
}

export type mePayload = {
    message: string
    error: string | null
    data: {
        role: Role
        email: string
    }
}
