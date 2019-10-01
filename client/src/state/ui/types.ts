export interface UIState {
    drawerOpen: boolean
}

export const TOGGLE_DRAWER = 'UI/TOGGLE_DRAWER';
export const SET_DRAWER = 'UI/SET_DRAWER';

interface ToggleDrawerAction {
    type: typeof TOGGLE_DRAWER
}

interface SetDrawerAction {
    type: typeof SET_DRAWER
    payload: {
        state: boolean
    }
}

export type UIActionTypes = ToggleDrawerAction | SetDrawerAction
