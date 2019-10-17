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

const ONE_USER = (id: string | undefined) => `${BASE_URL}/users/${id}`;

export function oneUser(id: string | undefined) {
    return fetch(ONE_USER(id), {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${getToken()}`
        }
    });
}

const COMPARE = (id1: string, id2: string) => `${BASE_URL}/users/compare/${id1}/${id2}`;

export function compare(id1: string, id2: string) {
    return fetch(COMPARE(id1, id2), {
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

    return `${BASE_URL}/repos${queryString}`;
};

export function searchRepos(filters: Filter[]) {
    return fetch(SEARCH_REPOS(filters), {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${getToken()}`
        }
    });
}

const REPOS_COUNT = (since?: Date) => {
    if (since != undefined) {
        const epoch = Math.floor(since.getTime());
        return `${BASE_URL}/repos/count?since=${epoch}`;
    } else {
        return `${BASE_URL}/repos/count`;
    }
};

export function reposCount(since: Date, filterByDate: boolean) {
    return fetch(REPOS_COUNT(filterByDate? since : undefined), {
        method: 'GET',
        headers: {
            'Authorization': `Bearer ${getToken()}`
        }
    });
}

const MY_REPOS = () => `${BASE_URL}/users/me/favorites`;

export function myRepos() {
    return fetch(MY_REPOS(), {
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

const ADD_TO_FAVORITES = (id: string) => `${BASE_URL}/users/me/favorites/${id}`;

export function addToFavorites(id: string) {
    return fetch(ADD_TO_FAVORITES(id), {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${getToken()}`
        }
    });
}

const REMOVE_FROM_FAVORITES = (id: string) => `${BASE_URL}/users/me/favorites/${id}`;

export function removeFromFavorites(id: string) {
    return fetch(REMOVE_FROM_FAVORITES(id), {
        method: 'DELETE',
        headers: {
            'Authorization': `Bearer ${getToken()}`
        }
    });
}
