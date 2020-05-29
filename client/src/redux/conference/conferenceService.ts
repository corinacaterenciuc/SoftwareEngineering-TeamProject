import {buildAuthHeader, Conference, Section} from '../entities';
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
import {Dispatch} from "redux";
import {RootStateGetter} from "../index";
import {domain} from "../../constants";

const request = require('request-promise-native');

const conferenceService = {

    addConference: (conference: Conference) => (dispatch: Dispatch, getState: RootStateGetter) => request({
        method: "POST",
        url: `${domain}/api/conferences/`,
        json: true,
        headers: buildAuthHeader(getState()),
        body: {...conference}
    })
        .then(function (response) {
            dispatch({
                type: FETCH_CONFERENCES,
                payload: {conference: response}
            })
        })
        .catch(function (error) {
            console.log(error)
        })
    ,

    removeConference: (id: number) => (dispatch: Dispatch, getState: RootStateGetter) => request({
        method: "DELETE",
        url: `${domain}/api/conferences?id=${id}`,
        headers: buildAuthHeader(getState()),
    })
        .then(function () {
            dispatch({
                type: DELETE_CONFERENCE,
                payload: {conferenceId: id}
            })
        })
        .catch(function (error) {
            console.log(error)
        })
    ,

    updateConference: (conference: Conference) => (dispatch: Dispatch, getState: RootStateGetter) => request({
        method: 'PUT',
        url: `${domain}/api/conferences`,
        headers: buildAuthHeader(getState()),
        json: true,
        body: {...conference}
    })
        .then(function () {
            dispatch({
                type: UPDATE_CONFERENCE,
                payload: {conference: conference}
            })
        })
        .catch(function (error) {
            console.log(error)
        })
    ,

    // TODO: Allow more detailed POST body
    getConferences: () => (dispatch: Dispatch, getState: RootStateGetter) => request({
        method: "GET",
        url: `${domain}/api/conferences/`,
        headers: buildAuthHeader(getState()),
    })
        .then(function (response) {
            dispatch({
                type: FETCH_CONFERENCES,
                payload: {conferences: response}
            })
        })
        .catch(function (error) {
            console.log(error)
        })
    ,

    addParticipantToConference: (email: string, conferenceId: number) =>
        (dispatch: Dispatch, getState: RootStateGetter) => request({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/participants/`,
            headers: buildAuthHeader(getState()),
            json: true,
            body: {email: email}
        })
            .then(function () {
                dispatch({
                    type: ADD_PARTICIPANT_CONFERENCE,
                    payload: {participant: email, conferenceId: conferenceId}
                })
            })
            .catch(function (error) {
                console.log(error)
            })
    ,

    removeParticipantFromConference: (email: string, conferenceId: number) =>
        (dispatch: Dispatch, getState: RootStateGetter) => request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/participants?email=${email}`,
            headers: buildAuthHeader(getState()),
        })
            .then(function () {
                dispatch({
                    type: REMOVE_PARTICIPANT_CONFERENCE,
                    payload: {participant: email, conferenceId: conferenceId}
                })
            })
            .catch(function (error) {
                console.log(error)
            })
    ,

    addSection: (section: Section, conferenceId: number) =>
        (dispatch: Dispatch, getState: RootStateGetter) => request({
            method: "POST",
            url: `${domain}/api/conferences/${conferenceId}/sections`,
            headers: buildAuthHeader(getState()),
            json: true,
            body: {...section}
        })
            .then(function (response) {
                dispatch({
                    type: ADD_SECTION,
                    payload: {section: response, conferenceId: conferenceId}
                })
            })
            .catch(function (error) {
                console.log(error)
            })
    ,

    updateSection: (section: Section, conferenceId: number) =>
        (dispatch: Dispatch, getState: RootStateGetter) => request({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/sections`,
            json: true,
            headers: buildAuthHeader(getState()),
            body: {...section}
        })
            .then(function () {
                dispatch({
                    type: UPDATE_SECTION,
                    payload: {section: section, conferenceId: conferenceId}
                })
            })
            .catch(function (error) {
                console.log(error)
            })
    ,

    removeSection: (conferenceId: number, sectionId: number) =>
        (dispatch: Dispatch, getState: RootStateGetter) => request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/sections?id=${sectionId}`,
            headers: buildAuthHeader(getState()),
        })
            .then(function () {
                dispatch({
                    type: REMOVE_SECTION,
                    payload: {sectionId: sectionId, conferenceId: conferenceId}
                })
            })
            .catch(function (error) {
                console.log(error)
            })
    ,

    addParticipantToSection: (email: string, conferenceId: number, sectionId: number) =>
        (dispatch: Dispatch, getState: RootStateGetter) => request({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/sections/${sectionId}/participants`,
            headers: buildAuthHeader(getState()),
            body: {email: email}
        })
            .then(function () {
                dispatch({
                    type: ADD_PARTICIPANT_SECTION,
                    payload: {sectionId: sectionId, conferenceId: conferenceId, email: email}
                })
            })
            .catch(function (error) {
                console.log(error)
            })
    ,

    removeParticipantFromSection: (email: string, conferenceId: number, sectionId: number) =>
        (dispatch: Dispatch, getState: RootStateGetter) => request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/sections/${sectionId}/participants?email=${email}`,
            headers: buildAuthHeader(getState()),
            body: {email: email}
        })
            .then(function () {
                dispatch({
                    type: REMOVE_PARTICIPANT_CONFERENCE,
                    payload: {sectionId: sectionId, conferenceId: conferenceId, email: email}
                })
            })
            .catch(function (error) {
                console.log(error)
            })
};

export default conferenceService;
