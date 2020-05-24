import domain, {buildAuthHeader, logRequestError} from "../serviceConstants";
import * as request from 'request-promise'
import {GET_NOTIFICATIONS, READ_NOTIFICATION} from "./notificationActions";

export const notificationService =
    {
        // TODO: We require additional pylons

        getNotifications: () => (dispatch, getState) => request({
            method: 'GET',
            url: `${domain}/api/notifications`,
            headers: buildAuthHeader(getState())
        })
            .then((response: Notification[]) => dispatch({
                type: GET_NOTIFICATIONS,
                notifications: response
            }))
            .catch(logRequestError)
        ,

        readNotification: (notificationId: number) => (dispatch, getState) => request({
            method: 'PATCH',
            url: `${domain}/api/notifications/${notificationId}`,
            headers: buildAuthHeader(getState())
        })
            .then(_ => dispatch({
                type: READ_NOTIFICATION,
                notificationId: notificationId
            }))
            .catch(logRequestError)
    };
