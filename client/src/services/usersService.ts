import * as request from 'request-promise';
import domain from './constants';


const userService = {

    addUser: function(firstName: string, lastName: string, email: string, password: string) {

        return request({

            method: "POST",
            url: `${domain}/api/users/`,
            json: true,
            body: {
                firstname: firstName,
                lastname: lastName,
                email: email,
                password: password
            }
        })
    },

    removeUser: function(email: string) {
        return request({
            method: "DELETE",
            url: `${domain}/api/users/`,
            json: true,
            body:
                {
                    email: email
                }
        })
    },

    getUsers: function() {
        return request({
            method: "GET",
            url: `${domain}/api/users/`
        })
    },

    makeAdmin: function(email: string) {
        return request({
            method: "PUT",
            url: `${domain}/api/users/admins/`,
            json: true,
            body:
                {
                    email: email
                }
        })
    },

    removeAdmin: function(email: string) {
        return request({
            method: "DELETE",
            url: `${domain}/api/users/admins/`,
            json: true,
            body: {
                email: email
            }
        })
    }
};

export default userService;


