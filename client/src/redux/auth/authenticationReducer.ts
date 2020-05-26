// @ts-nocheck
import {PCM, AUTHOR, USER} from "../../userClaims";
import {JWT} from "../serviceConstants";
import {LOGIN, LOGOUT, REGISTER} from "./authenticationActions";

type AuthState = { firstname: string, lastname: string, email: string, token: JWT };
type Action = { type: string, payload: object }

const initState: AuthState = {firstname: '', lastname: '', email: '', token: '', claims: [PCM, USER]};

export default function (
    state: AuthState = initState,
    action: Action
) {
    console.log(action);
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
                firstname: response.user.firstname,
                lastname: response.user.lastname,
                email: response.user.email,
                token: response.token
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
