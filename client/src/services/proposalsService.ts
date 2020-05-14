import * as request from 'request-promise';
import domain from './constants';
import {Proposal} from './serviceTypes';
import {Review} from './serviceTypes';


const proposalsService = {

    addProposal: function (
        conferenceId: number,
        author: string,
        proposalName: string,
        filePath: string,
        abstractPath: string,
        topics: string[],
        keywords: string[],
        coAuthors: string[],
        bidders: string[],
        reviewers: string[],
        reviews: Review[]
    ) {
        return request({

            method: "POST",
            url: `${domain}/api/conferences/${conferenceId}/proposals`,
            json: true,
            body: {
                author: author,
                proposalName: proposalName,
                filePath: filePath,
                abstractPath: abstractPath,
                topics: topics,
                keywords: keywords,
                coAuthors: coAuthors,
                bidders: bidders,
                reviewers: reviewers,
                reviews: reviews
            }
        })
    },

    removeProposal: function(conferenceId: number, proposalName: string, author: string)
    {
        return request({
            method: "DELETE",
            url: `${domain}/api/conferences/${conferenceId}/proposals`,
            json: true,
            body: {
                proposalName: proposalName,
                author: author
            }

        })
    },

    getProposals: function (conferenceId: number) {

        return request({
            method: "GET",
            url: `${domain}/api/conferences/${conferenceId}/proposals`
        })
    },

    addReview: function (conferenceId: number, proposalId: number, email: string, proposal: Proposal) {
        return request({
            method: "POST",
            url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/reviews`,
            json: true,
            body: {
                email: email,
                proposal: proposal
            }
        })
    },

    getReviews: function(conferenceId: number, proposalId: number) {
        return request({
            method: "GET",
            url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/reviews`
        })
    },

    bid: function(conferenceId: number, proposalId: number, email: string) {
        return request({
            method: "PUT",
            url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/bid`,
            json: true,
            body: {
                email: email
            }

        })
    },

    getBidders: function(conferenceId: number, proposalId: number) {
        return request({
            method: "GET",
            url: `${domain}/api/conferences/${conferenceId}/proposals/${proposalId}/bid`
        })
    }
};

export default proposalsService;
