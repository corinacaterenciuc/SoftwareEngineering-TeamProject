// @ts-nocheck
import {User} from "../entities";
import {GET_USERS, REMOVE_USER, UPDATE_USER} from './userActions'

type UserState = { users: User[] };
type Action = { type: string, payload: object }

export default function (state: UserState = {users: [],}, action: Action) {
    let newState: UserState = {users: []};
    let {type, payload} = action;
    switch (type) {
        case REMOVE_USER: {
            newState = {...state};
            newState.users = newState.users.filter(u => u.email !== payload.email);
            break;
        }
        case GET_USERS: {
            newState = {...state};
            newState.users = payload.users;
            break;
        }
        case UPDATE_USER: {
            newState = {...state};
            newState.users = newState.users.map(u => u.email === payload.user.email ? payload.user : u);
            break;
        }
        default:
            return state;
    }
    return newState;
}
