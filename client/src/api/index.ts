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

export type Repo = {
    id: string
    name: string
    numForks: number
    numStars: number
    language: string
    favCount: number | undefined
}

export type Parameter = 'forks' | 'stars' | 'size' | 'topics' | 'followers'
export type Operator = 'gt' | 'geq' | 'lt' | 'leq'
export type Filter = {
    param: Parameter
    op: Operator
    value: number
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

export type searchReposPayload = {
    message: string
    error: string | null
    data: Repo[]
}
