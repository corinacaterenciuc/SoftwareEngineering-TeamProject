// @ts-nocheck
import {
    ADD_PARTICIPANT_CONFERENCE,
    ADD_PARTICIPANT_SECTION,
    ADD_SECTION,
    DELETE_CONFERENCE,
    FETCH_CONFERENCES,
    NEW_CONFERENCE,
    REMOVE_PARTICIPANT_CONFERENCE,
    REMOVE_PARTICIPANT_SECTION,
    REMOVE_SECTION,
    UPDATE_CONFERENCE,
    UPDATE_SECTION
} from "./conferenceActions";
import {Conference, Section} from "../serviceConstants";


export type ConferenceState = { conferences: Conference[] };
type Action = { type: string, payload: object }

function getConference(state: ConferenceState, id: number): Conference {
    return {...state.conferences.find(c => c.id === id)};
}

function updateConference(state: ConferenceState, conference: Conference): ConferenceState {
    const newState = {...state};
    newState.conferences = state.conferences.map(c => c.id === conference.id ? conference : c);
    return newState;
}

function getSection(state: ConferenceState, conferenceId: number, sectionId: number): Section {
    const conference: Conference = {...state.conferences.find(c => c.id === conferenceId)};
    return {...conference.sections.find(s => s.id === sectionId)};
}

function updateSection(state: ConferenceState, conferenceId: number, section: Section): ConferenceState {
    let conference: Conference = getConference(state, conferenceId);
    conference.sections = conference.sections.map(s => s.id === section.id ? section : s);
    return updateConference(state, conference);
}

export default function (state = {conferences: [],}, action: Action) {
    let newState: ConferenceState = {conferences: []};
    let {type, payload} = action;
    let conferenceU: Conference = null;
    let sectionU: Section = null;
    switch (type) {
        case FETCH_CONFERENCES:
            newState = {...state, conferences: payload.conferences};
            break;
        case NEW_CONFERENCE:
            newState = {...state};
            newState.conferences.push(payload.conference);
            break;
        case DELETE_CONFERENCE:
            newState = {...state};
            newState.conferences = newState.conferences.filter(c => c.id !== payload.conferenceId);
            break;
        case UPDATE_CONFERENCE:
            newState = updateConference(state, payload.conference);
            break;
        case ADD_PARTICIPANT_CONFERENCE:
            // TODO: User response
            conferenceU = getConference(state, payload.conferenceId);
            conferenceU.participants.push(payload.participant);
            newState = updateConference(state, conferenceU);
            break;
        case REMOVE_PARTICIPANT_CONFERENCE:
            conferenceU = getConference(state, payload.conferenceId);
            conferenceU.participants = conferenceU.participants.filter(pEmail => pEmail !== payload.email);
            newState = updateConference(state, conferenceU);
            break;
        case ADD_SECTION:
            conferenceU = getConference(state, payload.conferenceId);
            conferenceU.sections.push(payload.section);
            newState = updateConference(state, conference);
            break;
        case UPDATE_SECTION:
            conferenceU = getConference(state, payload.conferenceId);
            updatedS = payload.section;
            conferenceU.sections = conferenceU.sections.map(s => s.id === updatedS.id ? updatedS : s);
            newState = updateConference(state, conferenceU);
            break;
        case REMOVE_SECTION:
            conferenceU = getConference(state, payload.conferenceId);
            conference.sections = conference.sections.filter(s => s.id !== payload.sectionId);
            newState = updateConference(state, conference);
            break;
        case ADD_PARTICIPANT_SECTION:
            sectionU = getSection(state, payload.conferenceId, payload.sectionId);
            sectionU.participants.push(payload.email);
            newState = updateSection(state, conferenceId, sectionU);
            break;
        case REMOVE_PARTICIPANT_SECTION:
            sectionU = getSection(state, payload.conferenceId, payload.sectionId);
            sectionU.participants = sectionU.participants.filter(p => p.email !== payload.email);
            newState = updateSection(state, conferenceId, sectionU);
            break;
        default:
            return state;
    }
    return newState;
}
