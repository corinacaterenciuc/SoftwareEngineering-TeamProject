import * as request from 'request-promise';
import domain from './constants';
import {Proposal} from './serviceTypes';
import {Participant} from './serviceTypes';


const conferenceService = {

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
            method: "POST",
            url: `${domain}/api/conferences/`,
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

    getConferences: function () {
        return request({
            method: "GET",
            url: `${domain}/api/conferences/`
        })
    },


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

    getAllSCM: function (conferenceId: number) {
        return request({
            method: "GET",
            url: `${domain}/api/conferences/${conferenceId}/scm/`
        })
    },

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

    getAllPCM: function (conferenceId: number) {
        return request({
            method: "GET",
            url: `${domain}/api/conferences/${conferenceId}/pcm/`
        })
    },

    makeSessionChair: function (email: string, conferenceId: number, sectionId: number) {
        return request({
            method: "POST",
            url: `${domain}/api/conferences/${conferenceId}/sections/${sectionId}/chair`,
            json: true,
            body: {
                "email": email
            }
        })
    },

    removeSessionChair: function (email: string, conferenceId: number, sectionId: number) {
        return request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/sections/${sectionId}/chair`,
            json: true,
            body: {
                "email": email
            }
        })
    },

    getSessionChair: function (conferenceId: number, sectionId: number) {
        return request({
            method: "GET",
            url: `${domain}/api/conferences/${conferenceId}/sections/${sectionId}/chair`
        })
    },

    addSection: function (chair_email: string,
                          topics: string[],
                          proposals: Proposal[],
                          participants: Participant[],
                          room: number,
                          conferenceId: number ) {
        return request({
            method: "POST",
            url: `${domain}/api/conferences/${conferenceId}/sections`,
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

    removeSection: function (conferenceId: number, sectionId: number) {
        return request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/sections/${sectionId}`
        })
    },

    addParticipantsToSection: function (email: string, conferenceId: number, sectionId: number) {
        return request({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/sections/${sectionId}/participants`,
            body: {
                email: email
            }
        })
    },

    getSections: function (conferenceId: number) {
        return request({
            method: "GET",
            url: `${domain}/api/conferences/${conferenceId}/sections`
        })
    },

};

export default conferenceService;
