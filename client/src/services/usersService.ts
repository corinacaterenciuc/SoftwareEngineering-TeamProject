import React, {useEffect} from 'react';
import request from 'request-promise';
import domain from './constants';

var userService = {

    /*add a user:
    /api/users/
    POST
    body:
      {
        "firstname": string,
        "lastname": string,
        "email": string,
        "password": string
      }
    response:
      STATUS: 200 or 400
    */

    addUser: function(firstname: String, lastname: String, email: String, password: String) {

        return request({

            method : "POST",
            url : `${domain}/api/users/`,
            json: true,
            body : {
              firstname : firstname,
              lastname : lastname,
              email : email,
              password : password
            }
        })
    },

      /*remove a user
      /api/users/
      DELETE
      body:
        {
          "email": string
        }
      response:
        STATUS: 200 or 400*/

    removeUser: function(email: String) {

      return request({
        
        method : "DELETE",
        url : `${domain}/api/users/`,
        json: true,
        body : 
        {
          email : email
        }
      })
    },


   /* get users
    /api/users/
    GET
    body: N/A
    response:
      body:
        [
          {
            "firstname": string,
            "lastname": string,
            "email": string,
          }
        ]
    */

    getUsers: function() {

      return request({
        method : "GET",
        url : `${domain}/api/users/`
      })
    },

    /*make admin
      /api/users/admins/
      PUT
      body:
        {
          "email": string
        }
      response:
        STATUS: 200 or 400*/

    makeAdmin: function(email : String) {

      return request({
        method : "PUT",
        url : `${domain}/api/users/admins/`,
        json: true,
        body : 
        {
          email : email
        }
      })
    },

    /*  remove admin
      /api/users/admins/
      DELETE
      body:
        {
          "email": string
        }
      response:
        STATUS: 200 or 400*/


    removeAdmin: function(email: String) {

      return request({
        method : "DELETE",
        url : `${domain}/api/users/admins/`,
        json: true,
        body : {
          email : email
        }
      })
    }
}

export default userService;


