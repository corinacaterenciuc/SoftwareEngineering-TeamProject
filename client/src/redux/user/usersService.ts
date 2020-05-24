import * as request from 'request-promise';
import domain, {buildAuthHeader, logRequestError, User} from '../serviceConstants';
import {GET_USERS, REMOVE_USER, UPDATE_USER} from "./userActions";

const userService = {
    removeUser: (email: string) => (dispatch, getState) => request({
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

    getUsers: () => (dispatch, getState) => request({
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

    updateUser: (updatedUser: User) => (dispatch, getState) => request({
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


