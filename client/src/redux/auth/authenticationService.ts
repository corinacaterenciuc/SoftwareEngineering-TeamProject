import {LOGIN, LOGOUT, REGISTER} from "./authenticationActions";
import {Dispatch} from "redux";
import {domain} from "../../constants";

type AuthResponse = { firstname: string, lastname: string, email: string, token: string }
const request = require('request-promise-native');

const authenticationService =
    {
        register: (firstname: string, lastname: string, email: string, password: string) =>
            (dispatch: Dispatch) => request({
                method: "POST",
                url: `${domain}/api/register`,
                json: true,
                body: {
                    firstname: firstname,
                    lastname: lastname,
                    email: email,
                    password: password
                }
            })
                .then(function (response) {
                    console.log(dispatch);
                    let payload = {
                        firstname: firstname,
                        lastname: lastname,
                        email: email,
                        token: response.token
                    };
                    dispatch({
                        type: REGISTER,
                        payload: payload
                    })
                })
                .catch(function (error) {
                    console.log(error)
                })
        ,

        login: (email: string, password: string) => (dispatch: Dispatch) => request({
            method: 'POST',
            url: `${domain}/api/login`,
            json: true,
            body: {email: email, password: password}
        })
            .then(function (response) {
                dispatch({
                    type: LOGIN,
                    payload: {
                        firstname: response.firstname,
                        lastname: response.lastname,
                        email: email,
                        token: response.token
                    }
                })
            })
            .catch(function (error) {
                console.log(error)
            })
        ,

        logout: () => (dispatch: Dispatch) => {
            dispatch({type: LOGOUT})
        }
    };

export default authenticationService;
