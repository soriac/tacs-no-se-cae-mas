import {store} from '../state';
import {Filter} from './types';

const BASE_URL = 'http://localhost:8080';

function getToken(): string {
    const token = store.getState().auth.token;
    return token || '';
}

const SIGNUP = () => `${BASE_URL}/signup`;

export function signup(email: string, password: string) {
    return fetch(SIGNUP(), {
        method: 'POST',
        body: JSON.stringify({email, password})
    });
}

const LOGIN = () => `${BASE_URL}/login`;

export function login(email: string, password: string) {
    return fetch(LOGIN(), {
        method: 'POST',
        body: JSON.stringify({email, password})
    });
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

const SEARCH_REPOS = (filters: Filter[]) => {
    const queryString = filters.reduce((acc: string, filter: Filter) => {
        const param = `${filter.param}=${filter.op}:${filter.value}`;
        if (acc !== '') {
            return acc + '&' + param;
        } else {
            return '?' + param;
        }
    }, '');

    const url = `${BASE_URL}/repos${queryString}`;
    console.log(url);
    return 'http://localhost:8080/repos?forks=gt:30&stars=gt:0&size=gt:0&topics=gt:0&followers=gt:0';
};

export function searchRepos(filters: Filter[]) {
    return fetch(SEARCH_REPOS(filters), {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${getToken()}`
        }
    });
}

const GET_USERS = () => `${BASE_URL}/users`;

export function allUsers() {
    return fetch(GET_USERS(), {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${getToken()}`
        }
    });
}
