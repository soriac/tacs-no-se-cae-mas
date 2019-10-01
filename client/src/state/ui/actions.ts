import {SET_DRAWER, TOGGLE_DRAWER, UIActionTypes} from './types';

export function toggleDrawer(): UIActionTypes {
    return {
        type: TOGGLE_DRAWER,
    };
}

export function setDrawer(state: boolean): UIActionTypes {
    return {
        type: SET_DRAWER,
        payload: {
            state
        }
    };
}
