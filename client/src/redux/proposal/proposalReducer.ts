import {Email, Proposal, Review} from "../entities";
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

const initialState: ProposalState = {
    proposals: [],
    reviews: [{
        id: 0,
        proposalId: 0,
        reviewer: "bratuandrei0@gmail.com",
        justification: 'Foarte nice how how',
        grade: 2
    }]
};

function getProposal(oldState: ProposalState, proposalId: number) {
    return {...oldState.proposals.find(p => p.id === proposalId)};
}

function updateProposal(oldState: ProposalState, proposal: Proposal): ProposalState {
    const newState = {...oldState};
    newState.proposals = oldState.proposals.map(p => p.id === proposal.id ? proposal : p);
    return newState
}

type Action = {
    type: string,
    payload: {
        proposal: Proposal | null,
        proposals: Proposal[] | null,
        review: Review | null,
        proposalId: number | null,
        bidder: Email | null,
        bidders: Email[] | null,
        reviews: Review[]
    }
}

export default (state: ProposalState = initialState, action: Action) => {
    let newState: ProposalState = {...state};
    let {type, payload} = action;
    console.log('PR', action);
    switch (type) {
        case ADD_PROPOSAL: {
            // @ts-ignore
            newState.proposals.push(payload.proposal);
            break;
        }
        case REMOVE_PROPOSAL: {
            newState.proposals = newState.proposals.filter(p => p.id !== payload.proposalId);
            break;
        }
        case GET_PROPOSALS: {
            // @ts-ignore
            newState.proposals = proposals;
            break;
        }
        case ADD_REVIEW: {
            // @ts-ignore
            const proposal: Proposal = getProposal(state, payload.proposalId);
            // @ts-ignore
            proposal.reviews.push(payload.review);
            newState = updateProposal(state, proposal);
            break;
        }
        case FETCH_REVIEWS: {
            // @ts-ignore
            const proposal: Proposal = getProposal(state, payload.proposalId);
            // @ts-ignore
            proposal.reviews = payload.reviews;
            newState = updateProposal(state, proposal);
            break;
        }
        case ADD_BID: {
            // @ts-ignore
            const proposal: Proposal = getProposal(state, payload.proposalId);
            // @ts-ignore
            proposal.bidders.push(payload.bidder);
            newState = updateProposal(state, proposal);
            break;
        }
        case FETCH_BIDDERS: {
            // @ts-ignore
            const proposal: Proposal = getProposal(state, payload.proposalId);
            // @ts-ignore
            proposal.bidders = payload.bidders;
            newState = updateProposal(state, proposal);
            break;
        }
        default:
            break;
    }
    return newState;
}
