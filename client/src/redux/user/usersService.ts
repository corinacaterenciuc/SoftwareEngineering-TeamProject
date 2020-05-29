import {buildAuthHeader, User} from '../entities';
import {GET_USERS, REMOVE_USER, UPDATE_USER} from "./userActions";
import {Dispatch} from "redux";
import {RootStateGetter} from "../index";
import {domain} from "../../constants";

const request = require('request-promise-native');

const userService = {
    removeUser: (email: string) => (dispatch: Dispatch, getState: RootStateGetter) => request({
        method: "DELETE",
        url: `${domain}/api/users?email=${email}`,
        headers: buildAuthHeader(getState())
    })
        .then(function () {
            dispatch({
                type: REMOVE_USER,
                payload: {user: email}
            })
        })
        .catch(function (error) {
            console.log(error)
        })
    ,

    getUsers: () => (dispatch: Dispatch, getState: RootStateGetter) => request({
        method: "GET",
        url: `${domain}/api/users/`,
        headers: buildAuthHeader(getState())
    })
        .then(function (response) {
            dispatch({
                type: GET_USERS,
                payload: {users: response}
            })
        })
        .catch(function (error) {
            console.log(error)
        })
    ,

    updateUser: (updatedUser: User) => (dispatch: Dispatch, getState: RootStateGetter) => request({
        method: 'PUT',
        url: `${domain}/api/users`,
        json: true,
        headers: buildAuthHeader(getState()),
        body: {...updatedUser}
    })
        .then(function () {
            dispatch({
                type: UPDATE_USER,
                payload: {user: updatedUser}
            })
        })
        .catch(function (error) {
            console.log(error)
        })
};

export default userService;


