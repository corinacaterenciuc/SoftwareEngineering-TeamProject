import {Proposal, Review} from "../entities";
import {UPDATE_CURRENT_PROPOSAL, UPDATE_CURRENT_REVIEW} from "./contextActions";

type ContextState = {
    currentProposal: Proposal | null,
    currentReview: Review | null
}

type Action = {
    type: string, payload: {
        proposal: Proposal | null,
        review: Review | null
    }
};

const initialState: ContextState = {
    currentProposal: null,
    currentReview: null,
};

export default function (state: ContextState = initialState, action: Action) {
    console.log('CONTEXT', action.type, action.payload);
    let newState = {...state};
    const { type, payload } = action;
    switch (type) {
        case UPDATE_CURRENT_PROPOSAL:
            newState.currentProposal = payload.proposal;
            break;
        case UPDATE_CURRENT_REVIEW:
            newState.currentReview = payload.review;
            break;
        default:
            break;
    }
    return newState;
}
