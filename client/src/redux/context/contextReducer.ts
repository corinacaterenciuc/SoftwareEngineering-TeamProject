import {Proposal} from "../entities";
import {UPDATE_CURRENT_PROPOSAL} from "./contextActions";

type ContextState = {
    currentProposal: Proposal | null
}
type Action = { type: string, payload: {
    proposal: Proposal
}};

const initialState: ContextState = {
    currentProposal: null,
};

export default function (state: ContextState = initialState, action: Action)
{
    let newState = { ...state };
    const { type, payload } = action;
    switch (type)
    {
        case UPDATE_CURRENT_PROPOSAL:
            newState.currentProposal = payload.proposal;
            break;
        default:
            break;
    }
    return newState;
}
