import * as request from 'request-promise';
import domain, {buildAuthHeader, Email, logRequestError, Proposal, Review} from '../serviceConstants';
import {
    ADD_BID,
    ADD_PROPOSAL,
    ADD_REVIEW,
    FETCH_BIDDERS,
    FETCH_REVIEWS,
    GET_PROPOSALS,
    REMOVE_PROPOSAL
} from "./proposalActions";
import {RootStateGetter} from "../index";
import {Dispatch} from "redux";

const proposalService =
    {
        // TODO: Changes required
        // TODO: Abstract is a string
        // TODO: Expect file as hexadecimal string
        addProposal: (conferenceId: number, proposal: Proposal) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request.default({
                method: "POST",
                url: `${domain}/api/conferences/${conferenceId}/proposals/`,
                json: true,
                headers: buildAuthHeader(getState()),
                body: {
                    author: proposal.author,
                    proposalName: proposal.proposalName,
                    // filePath: filePath, // TODO: We are sending actual file, not fP; fP will be received after
                    file: proposal.file,
                    // abstractPath: abstractPath, TODO: Rename this into abstract
                    topics: proposal.topics,
                    keywords: proposal.keywords,
                    coAuthors: proposal.coAuthors,
                    bidders: proposal.bidders,
                    reviewers: proposal.reviewers,
                    reviews: proposal.reviews
            }
        })
            .then((response: Proposal) => dispatch({
                type: ADD_PROPOSAL,
                payload: {proposal: response}
            }))
            .catch(logRequestError)
        ,

        // TODO Send as query parameter
        // TODO: 204 is syntactically preferred
        removeProposal: (conferenceId: number, proposalId: number) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request.default({
                method: "DELETE",
                headers: buildAuthHeader(getState()),
                url: `${domain}/api/conferences/${conferenceId}/proposals?id=${proposalId}`,
        })
            .then(_ => dispatch({
                type: REMOVE_PROPOSAL,
                payload: {proposalId: proposalId}
            }))
            .catch(logRequestError)
        ,

        getProposals: (conferenceId: number) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request.default({
                method: "GET",
                headers: buildAuthHeader(getState()),
                url: `${domain}/api/conferences/${conferenceId}/proposals`
        })
            .then((response: Proposal[]) => dispatch({
                type: GET_PROPOSALS,
                payload: {proposals: response}
            }))
            .catch(logRequestError)
        ,

        addReview: (conferenceId: number, proposalId: number, review: Review) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request.default({
                method: "POST",
                url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/reviews`,
                json: true,
                headers: buildAuthHeader(getState()),
                body: {reviewer: review.reviewer, grade: review.grade, justification: review.justification}
        })
            .then((response: Review) => dispatch({
                type: ADD_REVIEW,
                payload: {proposalId: proposalId, review: response}
            }))
            .catch(logRequestError)
        ,

        getReviews: (conferenceId: number, proposalId: number) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request.default({
                method: "GET",
                headers: buildAuthHeader(getState()),
                url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/reviews`
        })
            .then((response: Review[]) => dispatch({
                type: FETCH_REVIEWS,
                payload: {reviews: response}
            }))
            .catch(logRequestError)
        ,

        bid: (conferenceId: number, proposalId: number, email: string) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request.default({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/bid`,
            headers: buildAuthHeader(getState()),
            json: true,
            body: {email: email}
        })
            .then(_ => dispatch({
                type: ADD_BID,
                payload: {proposalId: proposalId, bidder: email}
            }))
            .catch(logRequestError)
        ,

        // TODO: return only email addresses
        // TODO: Do we need this?
        getBidders: (conferenceId: number, proposalId: number) =>
            (dispatch: Dispatch, getState: RootStateGetter) => request.default({
            method: "GET",
            headers: buildAuthHeader(getState()),
            url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/bid`
        })
            .then((response: Email[]) => dispatch({
                type: FETCH_BIDDERS,
                payload: {bidders: response, proposalId: proposalId}
            }))
        ,
    };

export default proposalService;
