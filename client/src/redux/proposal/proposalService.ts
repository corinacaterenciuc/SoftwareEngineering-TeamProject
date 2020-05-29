import {buildAuthHeader, Proposal, Review} from '../entities';
import {
    ADD_BID,
    ADD_PROPOSAL,
    ADD_REVIEW,
    ADD_SH,
    FETCH_REVIEWS,
    GET_PROPOSALS,
    REMOVE_BID,
    REMOVE_PROPOSAL,
    UPDATE_REVIEW
} from "./proposalActions";
import {RootStateGetter} from "../index";
import {Dispatch} from "redux";
import {domain} from "../../constants";

const request = require('request-promise-native');

const proposalService =
    {
        // File expected as base64 string
        addProposal: (conferenceId: number, proposal: Proposal, proposalFile: string) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request({
                method: "POST",
                url: `${domain}/api/conferences/${conferenceId}/proposals/`,
                json: true,
                headers: buildAuthHeader(getState()),
                body: {
                    author: proposal.author,
                    proposalName: proposal.proposalName,
                    file: proposalFile,
                    abstract: proposal.abstract,
                    topics: proposal.topics,
                    keywords: proposal.keywords,
                    coAuthors: proposal.coAuthors,
                    conferenceId: conferenceId
                }
            })
                .then(function (response) {
                    dispatch({
                        type: ADD_PROPOSAL,
                        payload: {proposal: response}
                    })
                })
                .catch(function (error) {
                    console.log(error)
                })
        ,

        // File expected as base64 string
        updateProposal: (conferenceId: number, proposal: Proposal, proposalFile: string) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request({
                method: "POST",
                url: `${domain}/api/conferences/${conferenceId}/proposals/`,
                json: true,
                headers: buildAuthHeader(getState()),
                body: {
                    conferenceId: proposal.conferenceId,
                    author: proposal.author,
                    proposalName: proposal.proposalName,
                    file: proposalFile,
                    abstract: proposal.abstract,
                    topics: proposal.topics,
                    keywords: proposal.keywords,
                    coAuthors: proposal.coAuthors,
                }
            })
                .then(function (response) {
                    dispatch({
                        type: ADD_PROPOSAL,
                        payload: {proposal: response}
                    })
                })
                .catch(function (error) {
                    console.log(error)
                })
        ,

        removeProposal: (conferenceId: number, proposalId: number) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request({
                method: "DELETE",
                headers: buildAuthHeader(getState()),
                url: `${domain}/api/conferences/${conferenceId}/proposals?id=${proposalId}`,
            })
                .then(function () {
                    dispatch({
                        type: REMOVE_PROPOSAL,
                        payload: {proposalId: proposalId}
                    })
                })
                .catch(function (error) {
                    console.log(error)
                })
        ,

        getProposals: (conferenceId: number) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request({
                method: "GET",
                headers: buildAuthHeader(getState()),
                url: `${domain}/api/conferences/${conferenceId}/proposals`
            })
                .then(function (response) {
                    dispatch({
                        type: GET_PROPOSALS,
                        payload: {proposals: response}
                    })
                })
                .catch(function (error) {
                    console.log(error)
                })
        ,

        addReview: (conferenceId: number, proposalId: number, review: Review) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request({
                method: "POST",
                url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/reviews`,
                json: true,
                headers: buildAuthHeader(getState()),
                body: {reviewer: review.reviewer, grade: review.grade, justification: review.justification}
            })
                .then(function (response) {
                    dispatch({
                        type: ADD_REVIEW,
                        payload: {proposalId: proposalId, review: response}
                    })
                })
                .catch(function (error) {
                    console.log(error)
                })
        ,

        updateReview: (conferenceId: number, proposalId: number, review: Review) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request({
                method: "PUT",
                url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/reviews`,
                json: true,
                headers: buildAuthHeader(getState()),
                body: {reviewer: review.reviewer, grade: review.grade, justification: review.justification}
            })
                .then(function () {
                    dispatch({
                        type: UPDATE_REVIEW,
                        payload: {proposalId: proposalId, review: review}
                    })
                })
                .catch(function (error) {
                    console.log(error)
                })
        ,

        getReviews: (conferenceId: number, proposalId: number) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request({
                method: "GET",
                headers: buildAuthHeader(getState()),
                url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/reviews`
            })
                .then(function (response) {
                    dispatch({
                        type: FETCH_REVIEWS,
                        payload: {reviews: response}
                    })
                })
                .catch(function (error) {
                    console.log(error)
                })
        ,

        bid: (conferenceId: number, proposalId: number, email: string) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request({
                method: "PUT",
                url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/bid`,
                headers: buildAuthHeader(getState()),
                json: true,
                body: {email: email}
            })
                .then(function () {
                    dispatch({
                        type: ADD_BID,
                        payload: {proposalId: proposalId, bidder: email}
                    })
                })
                .catch(function (error) {
                    console.log(error)
                })
        ,

        unbid: (conferenceId: number, proposalId: number, email: string) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request({
                method: "PUT",
                url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/unbid`,
                headers: buildAuthHeader(getState()),
                json: true,
                body: {email: email}
            })
                .then(function () {
                    dispatch({
                        type: REMOVE_BID,
                        payload: {proposalId: proposalId, bidder: email}
                    })
                })
                .catch(function (error) {
                    console.log(error)
                })
        ,

        addSecondHandReviewer: (conferenceId: number, proposalId: number, shEmail: string) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request({
                method: 'PUT',
                url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/sh`,
                headers: buildAuthHeader(getState()),
                json: true,
                body: {email: shEmail}
            })
                .then(function () {
                    dispatch({
                        type: ADD_SH,
                        payload: {proposalId: proposalId, sh: shEmail}
                    })
                })
                .catch(function (error) {
                    console.log(error)
                })
    };

export default proposalService;
