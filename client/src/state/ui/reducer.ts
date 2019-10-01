import {UIActionTypes, UIState} from './types';

const initialState: UIState = {
    drawerOpen: false
};

export function uiReducer(state = initialState, action: UIActionTypes): UIState {
    switch (action.type) {
        case 'UI/SET_DRAWER':
            return {
                ...state,
                drawerOpen: action.payload.state
            };
        case 'UI/TOGGLE_DRAWER':
            return {
                ...state,
                drawerOpen: !state.drawerOpen
            };
        default:
            return state;
    }
}
