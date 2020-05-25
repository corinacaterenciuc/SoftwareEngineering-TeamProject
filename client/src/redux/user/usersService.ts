import * as request from 'request-promise';
import domain, {buildAuthHeader, logRequestError, User} from '../serviceConstants';
import {GET_USERS, REMOVE_USER, UPDATE_USER} from "./userActions";
import {Dispatch} from "redux";
import {RootStateGetter} from "../index";

const userService = {
    removeUser: (email: string) => (dispatch: Dispatch, getState: RootStateGetter) => request.default({
        method: "DELETE",
        url: `${domain}/api/users?email=${email}`,
        headers: buildAuthHeader(getState())
    })
        .then(_ => dispatch({
            type: REMOVE_USER,
            payload: {user: email}
        }))
        .catch(logRequestError)
    ,

    getUsers: () => (dispatch: Dispatch, getState: RootStateGetter) => request.default({
        method: "GET",
        url: `${domain}/api/users/`,
        headers: buildAuthHeader(getState())
    })
        .then((response: User[]) => dispatch({
            type: GET_USERS,
            payload: {users: response}
        }))
        .catch(logRequestError)
    ,

    updateUser: (updatedUser: User) => (dispatch: Dispatch, getState: RootStateGetter) => request.default({
        method: 'PUT',
        url: `${domain}/api/users`,
        json: true,
        headers: buildAuthHeader(getState()),
        body: {...updatedUser}
    })
        .then(_ => dispatch({
            type: UPDATE_USER,
            payload: {user: updatedUser}
        }))
        .catch(logRequestError)
};

export default userService;


