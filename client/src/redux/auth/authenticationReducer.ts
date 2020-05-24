// @ts-nocheck
import {JWT} from "../serviceConstants";
import {LOGIN, REGISTER} from "./authenticationActions";

type AuthState = { firstname: string, lastname: string, email: string, token: JWT };
type Action = { type: string, payload: object }

export default function (
    state: AuthState = {firstname: '', lastname: '', email: '', token: ''},
    action: Action
) {
    let newState: AuthState = {firstname: '', lastname: '', email: '', token: ''};
    let {type, payload} = action;
    switch (type) {
        case LOGIN: {
            newState = {
                firstname: payload.firstname,
                lastname: payload.lastname,
                email: payload.email,
                token: payload.token
            };
            break;
        }
        case REGISTER: {
            newState = {
                firstname: response.user.firstname,
                lastname: response.user.lastname,
                email: response.user.email,
                token: response.token
            };
            break;
        }
        default:
            return state;
    }
    return newState;
}
