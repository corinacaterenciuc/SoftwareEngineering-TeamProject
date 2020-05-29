// @ts-nocheck
import {JWT} from "../entities";
import {LOGIN, LOGOUT, REGISTER} from "./authenticationActions";

type AuthState = { firstname: string, lastname: string, email: string, token: JWT };
type Action = { type: string, payload: object }

const initState: AuthState = {
    firstname: 'Mihalca',
    lastname: 'Razvan',
    email: 'mihalcarazvan@gmail.com',
    token: 'thisisafancyjwttoken'
};

export default function (state: AuthState = initState, action: Action) {
    let newState: AuthState = null;
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
                firstname: payload.firstname,
                lastname: payload.lastname,
                email: payload.email,
                token: payload.token
            };
            break;
        }
        case LOGOUT: {
            newState = {...initState};
            break;
        }
        default:
            return state;
    }
    return newState;
}
