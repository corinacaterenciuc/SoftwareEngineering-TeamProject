import React, {useEffect} from 'react';
import request from 'request-promise';
import domain from './constants';
import Proposal from './serviceTypes';
import Participant from './serviceTypes';



var conferenceService = {
    /* add Conferences
          /api/conferences/
          POST
          body:
            {
              "name": String,
              "description": String,
              "zeroDeadline": String,
              "abstractDeadline": String,
              "proposalDeadline": String,
              "biddingDeadline": String,
              "evaluationDeadline": String,
              "presentationDeadline": String
            }
          response:
            STATUS: 200 or 400
    */
    addConference: function
    (name: string,
     description: string,
     zeroDeadline: string,
     abstractDeadline: string,
     proposalDeadline: string,
     biddingDeadline: string,
     evaluationDeadline: string,
     presentationDeadline: string)
    {
        return request({
            method : "POST",
            url : `${domain}/api/conferences/`,
            json: true,
            body:
                {
                    name: name,
                    description: description,
                    zeroDeadline: zeroDeadline,
                    abstractDeadline: abstractDeadline,
                    proposalDeadline: proposalDeadline,
                    biddingDeadline: biddingDeadline,
                    evaluationDeadline: evaluationDeadline,
                    presentationDeadline: presentationDeadline
                }
        })
    },

    /*
     remove Conferences
      /api/conferences/
      DELETE
      body:
        {
          "id": int
        }
      response:
        STATUS: 200 or 400

     */

    removeConferences: function (id: number) {
        return request({
            method: "DELETE",
            url: `${domain}/api/conferences/`,
            json: true,
            body: {
                id: id
            }
        })

    },

    /*
    get conferences
      /api/conferences/
      GET
      body: N/A
      response:
        body:
          [
            {
              "id": int
              "name": String,
              "description": String,
              "zeroDeadline": String,
              "abstractDeadline": String,
              "proposalDeadline": String,
              "biddingDeadline": String,
              "evaluationDeadline": String,
              "presentationDeadline": String
            }
          ]

     */
    getConferences: function () {
        return request({
            method: "GET",
            url: `${domain}/api/conferences/`
        })
    },

    /*

  add participants on conference
      /api/conferences/{conferenceid}/participants/
      PUT
      body:
        {
          "email": String
        }
      response:
        STATUS: 200 or 400

     */
    addParticipantsOnConference: function (email: string, conferenceId: number) {
        return request({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/participants/`,
            json: true,
            body: {
                email: email
            }
        })
    },

    /*
    remove participants on conference
      /api/conferences/{conferenceid}/participants/
      DELETE
      body:
        {
          "email": String
        }
      response:
        STATUS: 200 or 400

     */

    removeParticipantsOnConference: function (email: string, conferenceId: number) {
        return request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/participants/`,
            json: true,
            body: {
                email: email
            }
        })
    },

    /*
      make scm
      /api/conferences/{conferenceid}/scm/
      PUT
      body:
        {
          "email": string
        }
      response:
        STATUS: 200 or 400

     */
    makeSCM: function (email: string, conferenceId: number) {
        return request({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/scm/`,
            json: true,
            body: {
                email: email
            }
        })
    },
    /*
     remove scm
      /api/conferences/{conferenceid}/scm/
      DELETE
      body:
        {
          "email": string
        }
      response:
        STATUS: 200 or 400

     */
    removeSCM: function (email: string, conferenceId: number) {
        return request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/scm/`,
            json: true,
            body: {
                email: email
            }
        })
    },

    /*
    make cscm
      /api/conferences/{conferenceid}/cscm/
      PUT
      body:
        {
          "email": string
        }
      response:
        STATUS: 200 or 400
     */

    makeCSCM: function (email: string, conferenceId: number) {
        return request({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/cscm/`,
            json: true,
            body: {
                email: email
            }
        })
    },

    /*
    remove cscm
      /api/conferences/{conferenceid}/cscm/
      DELETE
      body:
        {
          "email": string
        }
      response:
        STATUS: 200 or 400

     */
    removeCSCM:  function (email: string, conferenceId: number) {
        return request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/cscm/`,
            json: true,
            body: {
                email: email
            }
        })
    },

    /*
    make ccscm
      /api/conferences/{conferenceid}/ccscm/
      PUT
      body:
        {
          "email": string
        }
      response:
        STATUS: 200 or 400

     */
    makeCCSCM: function (email: string, conferenceId: number) {
        return request({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/ccscm/`,
            json: true,
            body: {
                email: email
            }
        })
    },

    /*
     remove ccscm
      /api/conferences/{conferenceid}/ccscm/
      DELETE
      body:
        {
          "email": string
        }
      response:
        STATUS: 200 or 400

     */

    removeCCSCM: function (email: string, conferenceId: number) {
        return request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/ccscm/`,
            json: true,
            body: {
                email: email
            }
        })
    },

    /*
    get all scm
      /api/conferences/{conferenceid}/scm/
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
    getAllSCM: function (conferenceId: number) {
        return request({
            method: "GET",
            url: `${domain}/api/conferences/${conferenceId}/scm/`
        })
    },

    /*
    make pcm
      /api/conferences/{conferenceid}/pcm/
      PUT
      body:
        {
          "email": string
        }
      response:
        STATUS: 200 or 400
     */
    makePCM: function (email: string, conferenceId: number) {
        return request({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/pcm/`,
            json: true,
            body: {
                email: email
            }
        })
    },

    /*
    remove pcm
      /api/conferences/{conferenceid}/pcm/
      DELETE
      body:
        {
          "email": string
        }
      response:
        STATUS: 200 or 400
     */
    removePCM: function (email: string, conferenceId: number) {
        return request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/pcm/`,
            json: true,
            body: {
                email: email
            }
        })
    },

    /*
    make cpcm
      /api/conferences/{conferenceid}/cpcm/
      PUT
      body:
        {
          "email": string
        }
      response:
        STATUS: 200 or 400
     */

    makeCPCM: function (email: string, conferenceId: number) {
        return request({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/cpcm/`,
            json: true,
            body: {
                email: email
            }
        })
    },

    /*
    remove cpcm
      /api/conferences/{conferenceid}/cpcm
      DELETE
      body:
        {
          "email": string
        }
      response:
        STATUS: 200 or 400

     */
    removeCPCM:  function (email: string, conferenceId: number) {
        return request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/cpcm/`,
            json: true,
            body: {
                email: email
            }
        })
    },

    /*
    make ccpcm
      /api/conferences/{conferenceid}/ccpcm
      POST
      body:
        {
          "email" string
        }
      response:
        STATUS: 200 or 400

     */
    makeCCPCM: function (email: string, conferenceId: number) {
        return request({
            method: "POST",
            url: `${domain}/api/conferences/${conferenceId}/ccpcm/`,
            json: true,
            body: {
                email: email
            }
        })
    },

    /*
    remove ccpcm
      /api/conferences/{conferenceid}/ccpcm
      DELETE
      body:
        {
          "email" string
        }
      response:
        STATUS: 200 or 400
     */
    removeCCPCM: function (email: string, conferenceId: number) {
        return request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/ccpcm/`,
            json: true,
            body: {
                email: email
            }
        })
    },

    /*
    get all pcm
      /api/conferences/{conferenceid}/pcm/
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
    getAllPCM: function (conferenceId: number) {
        return request({
            method: "GET",
            url: `${domain}/api/conferences/${conferenceId}/pcm/`
        })
    },

    /*
    make session chair
      /api/conferences/{conferenceid}/sections/{sectionid}/chair
      POST
      body:
        {
          "email" string
        }
      response:
        STATUS: 200 or 400
     */
    makeSessionChair: function (email: string, conferenceId: number, sectionId: number) {
        return request({
            method: "POST",
            url: `${domain}/api/conferences/${conferenceId}/sections/${sectionId}/chair`,
            json: true,
            body: {
                "email" : email
            }
        })
    },

    /*
    remove session chair
      /api/conferences/{conferenceid}/sections/{sectionid}/chair
      DELETE
      body:
        {
          "email" string
        }
      response:
        STATUS: 200 or 400
     */
    removeSessionChair: function (email: string, conferenceId: number, sectionId: number) {
        return request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/sections/${sectionId}/chair`,
            json: true,
            body: {
                "email" : email
            }
        })
    },

    /*
    get session chair
      /api/conferences/{conferenceid}/sections/{sectionid}/chair
      GET
      body: N/A
      response:
        body:
          {
            "firstname": string,
            "lastname": string,
            "email": string,
          }
     */
    getSessionChair: function (conferenceId: number, sectionId: number) {
        return request({
            method: "GET",
            url: `${domain}/api/conferences/${conferenceId}/sections/${sectionId}/chair`
        })
    },

    /*
    add section
      /api/conferences/{conferenceid}/sections
      POST
      body:
        {
          "chair": "email",
          "topics":
          [
            "unu",
            "doua"
          ],
          "proposals":
          [
            {
              "email": String
              "title": String
            }
          ],
          "participants":
          [
            {
              "email": String
            }
          ],
          "room": int
        }
      response:
        STATUS: 200 or 400
     */
    addSection: function (chair_email: string,
                          topics: string[],
                          proposals: Proposal[],
                          participants: Participant[],
                          room: number,
                          conferenceId: number ) {
        return request({
            method : "POST",
            url : `${domain}/api/conferences/${conferenceId}/sections`,
            json: true,
            body: {
                chair: chair_email,
                topics: topics,
                proposals: proposals,
                participants: participants,
                room: room
            }
        })
    },

    /*
    remove section
      /api/conferences/{conferenceid}/sections/{sectionid}
      DELETE
      body: N/A
      response:
        STATUS: 200 or 400
     */
    removeSection: function (conferenceId: number, sectionId: number) {
        return request({
            method : "DELETE",
            url : `${domain}/api/conferences/${conferenceId}/sections/${sectionId}`
        })
    },

    /*
    add participants to section
      /api/conferences/{conferenceid}/sections/{sectionid}/participants
      PUT
      body:
        {
          "email": String
        }
      response:
        STATUS: 200 or 400
     */
    addParticipantsToSection: function (email: string, conferenceId: number, sectionId: number) {
        return request({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/sections/${sectionId}/participants`,
            body: {
                email: email
            }
        })
    },

    /*
    get sections
      /api/conferences/{conferenceid}/sections
      GET
      body: N/A
      response:
        body:
          [
            {
              "chair": "email",
              "topics":
              [
                "unu",
                "doua"
              ],
              "proposals":
              [
                {
                  "email": String
                  "title": String
                }
              ],
              "participants":
              [
                {
                  "email": String
                }
              ],
              "room": int
            }
          ]
     */
    getSections: function (conferenceId: number) {
        return request({
            method : "GET",
            url : `${domain}/api/conferences/${conferenceId}/sections`
        })
    },

}

export default conferenceService;
