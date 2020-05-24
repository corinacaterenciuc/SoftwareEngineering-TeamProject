import * as request from "request-promise";
import domain, {JWT, logRequestError} from "../serviceConstants";
import {LOGIN, REGISTER} from "./authenticationActions";

const authenticationService =
    {
        // TODO: We need this
        register: (firstname: string, lastname: string, email: string, password: string) =>
            dispatch => request({
                method: "POST",
                url: `${domain}/api/register/`,
                json: true,
                body: {
                    firstname: firstname,
                    lastname: lastname,
                    email: email,
                    password: password
                }
            })
                .then((response: JWT) => dispatch({
                    type: REGISTER,
                    payload: {
                        firstname: firstname,
                        lastname: lastname,
                        email: email,
                        token: response
                    }
                }))
                .catch(logRequestError)
        ,

        login: (email: string, password: string) => dispatch => request({
            method: 'POST',
            url: `${domain}/api/login`,
            json: true,
            body: {email: email, password: password}
        })
            .then((response: { firstname: string, lastname: string, token: string }) => dispatch({
                type: LOGIN,
                payload: {
                    firstname: response.firstname,
                    lastname: response.lastname,
                    email: email,
                    token: response.token
                }
            }))
            .catch(logRequestError)
    };

export default authenticationService;
