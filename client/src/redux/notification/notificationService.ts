import {buildAuthHeader} from "../entities";
import {GET_NOTIFICATIONS, READ_NOTIFICATION} from "./notificationActions";
import {Dispatch} from "redux";
import {RootStateGetter} from "../index";
import {domain} from "../../constants";

const request = require('request-promise-native');

export const notificationService =
    {
        getNotifications: () => (dispatch: Dispatch, getState: RootStateGetter) => request({
            method: 'GET',
            url: `${domain}/api/notifications`,
            headers: buildAuthHeader(getState())
        })
            .then(function (response) {
                dispatch({
                    type: GET_NOTIFICATIONS,
                    notifications: response
                })
            })
            .catch(function (error) {
                console.log(error)
            })
        ,

        readNotification: (notificationId: number) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request({
                method: 'PATCH',
                url: `${domain}/api/notifications/${notificationId}`,
                headers: buildAuthHeader(getState())
            })
                .then(function () {
                    dispatch({
                        type: READ_NOTIFICATION,
                        notificationId: notificationId
                    })
                })
                .catch(function (error) {
                    console.log(error)
                })
};
