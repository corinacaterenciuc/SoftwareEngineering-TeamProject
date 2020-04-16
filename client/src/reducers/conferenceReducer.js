import {FETCH_CONFERENCES, NEW_CONFERENCE} from "../actions/types";

const initialState = {
    conferences: [],
};

export default function(state = initialState, action)
{
    switch (action.type)
    {
        case FETCH_CONFERENCES:
            return {
                ...state,
                conferences: action.payload  // See fetchConferences action in conferenceAction
            };
        case NEW_CONFERENCE:
            let new_state = {...state};
            new_state.conferences.push(action.payload);
            return new_state;
        default:
            return state;
    }
}