let domain = "domain";

export default domain;

export type Email = string;
export type ID = number;
export type JWT = string;

export type Proposal = {
    id: ID | null,
    conference: ID | null,
    proposalName: string,
    filePath: string | null,
    abstract: string,
    topics: string[],
    keywords: string[] | null,
    author: string,
    coAuthors: Email[] | null,
    bidders: Email[] | null,
    reviews: ID[] | null
}

export type User = {
    email: string,
    firstname: string,
    lastname: string,
}

export type Section = {
    id: ID | null,
    name: string,
    topics: string[],
    proposals: ID[] | null,
    participants: Email[] | null,
    chair: Email,
    room: number
}

export type Review = {
    id: ID | null,
    proposal: ID,
    reviewer: Email,
    grade: number,
    justification: string
}

export type Conference = {
    id: ID | null,
    description: string,
    zeroDeadline: Date,
    abstractDeadline: Date,
    proposalDeadline: Date,
    biddingDeadline: Date,
    evaluationDeadline: Date,
    presentationDeadline: Date,
    participants: Email[] | null,
    // TODO Might need to add below properties
    scms: Email[] | null,
    cscm: Email | null,
    ccscm: Email | null,
    pcms: Email[] | null,
    cpcm: Email | null,
    ccpcm: Email | null,
    sections: Section[] | null
}

export type Notification = {
    id: ID | null,
    text: string,
    href: string
    read: boolean
}

// @ts-ignore
export const buildAuthHeader = (state: object): object => ({Authorization: `Bearer ${state.auth.token}`});
export const logRequestError = (error) => {
    console.log(error)
};
