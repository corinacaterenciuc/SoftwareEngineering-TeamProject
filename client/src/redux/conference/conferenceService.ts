import * as request from 'request-promise';
import domain, {buildAuthHeader, Conference, logRequestError, Section} from '../serviceConstants';
import {
    ADD_PARTICIPANT_CONFERENCE,
    ADD_PARTICIPANT_SECTION,
    ADD_SECTION,
    DELETE_CONFERENCE,
    FETCH_CONFERENCES,
    REMOVE_PARTICIPANT_CONFERENCE,
    REMOVE_SECTION,
    UPDATE_CONFERENCE,
    UPDATE_SECTION
} from "./conferenceActions";

const conferenceService = {

    addConference: (conference: Conference) => (dispatch, getState) => request({
        method: "POST",
        url: `${domain}/api/conferences/`,
        json: true,
        headers: buildAuthHeader(getState()),
        body: {...conference}
    })
        .then((response: Conference) => dispatch({
            type: FETCH_CONFERENCES,
            payload: {conference: response}
        }))
        .catch(logRequestError)
    ,

    // TODO: Should be query parameter
    removeConference: (id: number) => (dispatch, getState) => request({
        method: "DELETE",
        url: `${domain}/api/conferences?id=${id}`,
        headers: buildAuthHeader(getState()),
    })
        .then(_ => dispatch({
            type: DELETE_CONFERENCE,
            payload: {conferenceId: id}
        }))
        .catch(logRequestError)
    ,

    // TODO: 204
    updateConference: (conference: Conference) => (dispatch, getState) => request({
        method: 'PUT',
        url: `${domain}/api/conferences`,
        headers: buildAuthHeader(getState()),
        json: true,
        body: {...conference}
    })
        .then(_ => dispatch({
            type: UPDATE_CONFERENCE,
            payload: {conference: conference}
        }))
        .catch(logRequestError)
    ,

    // TODO: Allow more detailed POST body
    getConferences: () => (dispatch, getState) => request({
        method: "GET",
        url: `${domain}/api/conferences/`,
        headers: buildAuthHeader(getState()),
    })
        .then((response: Conference[]) => dispatch({
            type: FETCH_CONFERENCES,
            payload: {conferences: response}
        }))
        .catch(logRequestError)
    ,

    addParticipantToConference: (email: string, conferenceId: number) => (dispatch, getState) => request({
        method: "PUT",
        url: `${domain}/api/conferences/${conferenceId}/participants/`,
        headers: buildAuthHeader(getState()),
        json: true,
        body: {email: email}
    })
        .then(_ => dispatch({
            type: ADD_PARTICIPANT_CONFERENCE,
            payload: {participant: email, conferenceId: conferenceId}
        }))
        .catch(logRequestError)
    ,

    removeParticipantFromConference: (email: string, conferenceId: number) => (dispatch, getState) => request({
        method: "DELETE",
        url: `${domain}/api/conferences/${conferenceId}/participants/`,
        headers: buildAuthHeader(getState()),
        json: true,
        body: {email: email}
    })
        .then(_ => dispatch({
            type: REMOVE_PARTICIPANT_CONFERENCE,
            payload: {participant: email, conferenceId: conferenceId}
        }))
        .catch(logRequestError)
    ,

    addSection: (section: Section, conferenceId: number) => (dispatch, getState) => request({
        method: "POST",
        url: `${domain}/api/conferences/${conferenceId}/sections`,
        headers: buildAuthHeader(getState()),
        json: true,
        body: {...section}
    })
        .then((response: Section) => dispatch({
            type: ADD_SECTION,
            payload: {section: response, conferenceId: conferenceId}
        }))
        .catch(logRequestError)
    ,

    updateSection: (section: Section, conferenceId: number) => (dispatch, getState) => request({
        method: "PUT",
        url: `${domain}/api/conferences/${conferenceId}/sections`,
        json: true,
        headers: buildAuthHeader(getState()),
        body: {...section}
    })
        .then(_ => dispatch({
            type: UPDATE_SECTION,
            payload: {section: section, conferenceId: conferenceId}
        }))
        .catch(logRequestError)
    ,

    // TODO: query param
    removeSection: (conferenceId: number, sectionId: number) => (dispatch, getState) => request({
        method: "DELETE",
        url: `${domain}/api/conferences/${conferenceId}/sections/?id=${sectionId}`,
        headers: buildAuthHeader(getState()),
    })
        .then(_ => dispatch({
            type: REMOVE_SECTION,
            payload: {sectionId: sectionId, conferenceId: conferenceId}
        }))
        .catch(logRequestError)
    ,

    addParticipantToSection: (email: string, conferenceId: number, sectionId: number) =>
        (dispatch, getState) => request({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/sections/${sectionId}/participants`,
            headers: buildAuthHeader(getState()),
            body: {email: email}
        })
            .then(_ => dispatch({
                type: ADD_PARTICIPANT_SECTION,
                payload: {sectionId: sectionId, conferenceId: conferenceId, email: email}
            }))
            .catch(logRequestError)
    ,

    removeParticipantFromSection: (email: string, conferenceId: number, sectionId: number) =>
        (dispatch, getState) => request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/sections/${sectionId}/participants?email=${email}`,
            headers: buildAuthHeader(getState()),
            body: {email: email}
        })
            .then(_ => dispatch({
                type: REMOVE_PARTICIPANT_CONFERENCE,
                payload: {sectionId: sectionId, conferenceId: conferenceId, email: email}
            }))
            .catch(logRequestError)
};

export default conferenceService;
