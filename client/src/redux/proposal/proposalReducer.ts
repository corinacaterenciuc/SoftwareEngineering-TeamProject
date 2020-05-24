// @ts-nocheck
import {Proposal, Review} from "../serviceConstants";
import {
    ADD_BID,
    ADD_PROPOSAL,
    ADD_REVIEW,
    FETCH_BIDDERS,
    FETCH_REVIEWS,
    GET_PROPOSALS,
    REMOVE_PROPOSAL
} from "./proposalActions";


type ProposalState = { proposals: Proposal[], reviews: Review[] };

function getProposal(oldState: ProposalState, proposalId: number): Proposal {
    return {...oldState.proposals.find(p => p.id === proposalId)};
}

function updateProposal(oldState: ProposalState, proposal: Proposal): ProposalState {
    const newState = {...oldState};
    newState.proposals = oldState.proposals.map(p => p.id === proposal.id ? proposal : p);
    return newState
}

export default (state: ProposalState = {proposals: [], reviews: []}, action: Action) => {
    let newState: ProposalState = {proposals: [], reviews: []};
    let {type, payload} = action;
    switch (type) {
        case ADD_PROPOSAL: {
            newState = {...state};
            newState.proposals.push(payload.proposal);
            break;
        }
        case REMOVE_PROPOSAL: {
            newState = {...state};
            newState.proposals = newState.proposals.filter(p => p.id !== proposalId);
            break;
        }
        case GET_PROPOSALS: {
            newState = {...state};
            newState.proposals = proposals;
            break;
        }
        case ADD_REVIEW: {
            const proposal: Proposal = getProposal(state, proposalId);
            proposal.reviews.push(review);
            newState = updateProposal(state, proposal);
            break;
        }
        case FETCH_REVIEWS: {
            const proposal: Proposal = getProposal(state, proposalId);
            proposal.reviews = reviews;
            newState = updateProposal(state, proposal);
            break;
        }
        case ADD_BID: {
            const proposal: Proposal = getProposal(state, proposalId);
            proposal.bidders.push(bidder);
            newState = updateProposal(state, proposal);
            break;
        }
        case FETCH_BIDDERS: {
            const proposal: Proposal = getProposal(state, proposalId);
            proposal.bidders = bidders;
            newState = updateProposal(state, proposal);
            break;
        }
    }
    return newState;
}
