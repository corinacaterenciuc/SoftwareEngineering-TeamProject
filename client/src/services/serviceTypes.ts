export type Proposal = { email: string, title: string }
export type Participant = { email: string }
export type Review = { email: string, proposal : Proposal, grade: number, justification: string }

