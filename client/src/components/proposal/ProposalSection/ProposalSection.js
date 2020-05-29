import React, {useState} from "react";

import './ProposalSection.css';
import SectionNavigation from "../../navigation/SectionNavigation/SectionNavigation";
import {Route, Switch} from "react-router-dom";
import NotFound from "../../navigation/NotFound/NotFound";
import ListContainer from "../../generic/ListContainer/ListContainer";
import ProposalCard from "../ProposalCard/ProposalCard";
import BidModal from "../BidModal/BidModal";
import ReviewProposalModal from "../ReviewProposalModal/ReviewProposalModal";
import ResolveProposalModal from "../ResolveProposalModal/ResolveProposalModal";
import {useDispatch, useSelector} from "react-redux";
import AddEditProposalModal from "../AddEditProposalModal/AddEditProposalModal";
import {Button, KIND as BUTTON_KIND} from "baseui/button";
import {RESET_CONTEXT_PROPOSAL} from "../../../redux/context/contextActions";
import {PROPOSAL_ACCEPTED} from "../../../redux/entities";

const ProposalSection = () => {
    const dispatch = useDispatch();
    let proposals = useSelector(state => state.proposal.proposals);
    let conferences = useSelector(state => state.conference.conferences);
    const currEmail = useSelector(state => state.auth.email);
    const [modalOpen, setModalOpen] = useState(false);

    const locations = [
        {title: 'My Proposals', itemId: '/dashboard/proposals/my-proposals'},
        {title: 'Bidding', itemId: '/dashboard/proposals/bidding'},
        {title: 'Review', itemId: "/dashboard/proposals/review"},
        {title: 'Resolve', itemId: '/dashboard/proposals/resolve'},
        {title: 'Improve Proposals', itemId: '/dashboard/proposals/improve'},
    ];

    return (
        <div className={"ProposalSection"}>
            <SectionNavigation locations={locations}/>
            <Switch>
                <Route
                    exact
                    path={'/dashboard/proposals/:subsection(my-proposals)'}
                    render={(props) => {
                        // proposals = proposals.filter(p => p.author === currEmail &&
                        //     conferences.find(c => c.id === p.conferenceId && c.proposalDeadline > new Date())
                        // );
                        return (<>
                            <ListContainer {...props}>
                                <Button kind={BUTTON_KIND.secondary} style={{width: '50%'}} onClick={() => {
                                    /*
                                    Non-null proposal context would imply that
                                    AddEditProposalModal should operate in edit mode.
                                    */
                                    dispatch({type: RESET_CONTEXT_PROPOSAL});
                                    setModalOpen(true);
                                }}>
                                    Add Proposal
                                </Button>
                                {
                                    proposals.map(p =>
                                        <ProposalCard
                                            key={p.id}
                                            navProps={props}
                                            proposal={p}
                                            setModalOpen={setModalOpen}
                                        />)
                                }
                            </ListContainer>
                            <AddEditProposalModal modalOpen={modalOpen} setModalOpen={setModalOpen}/>
                        </>)
                    }}
                />

                <Route
                    exact
                    path={'/dashboard/proposals/:subsection(bidding)'}
                    render={(props) => {
                        proposals = proposals.filter(p => p.bidders.includes(currEmail));
                        return (<>
                            <ListContainer {...props}>
                                {
                                    proposals.map(p =>
                                        <ProposalCard
                                            key={p.id}
                                            navProps={props}
                                            proposal={p}
                                            setModalOpen={setModalOpen}
                                        />)
                                }
                            </ListContainer>
                            <BidModal modalOpen={modalOpen} setModalOpen={setModalOpen}/>
                        </>)
                    }}
                />

                <Route
                    exact
                    path={'/dashboard/proposals/:subsection(review)'}
                    render={(props) => {
                        proposals = proposals.filter(p => p.reviewers.includes(currEmail));
                        return (<>
                            <ListContainer {...props}>
                                {
                                    proposals.map(p =>
                                        <ProposalCard
                                            key={p.id}
                                            navProps={props}
                                            proposal={p}
                                            setModalOpen={setModalOpen}
                                        />)
                                }
                            </ListContainer>
                            <ReviewProposalModal modalOpen={modalOpen} setModalOpen={setModalOpen}/>
                        </>);
                    }}
                />

                <Route
                    exact
                    path={'/dashboard/proposals/:subsection(resolve)'}
                    render={(props) => {
                        // those where principal acts CPCM
                        // conferences = conferences.filter(c => c.cpcm === currEmail);
                        // proposals = proposals.filter(p => conferences.map(c => c.id).includes(p.id));
                        return (
                            <>
                                <ListContainer {...props}>
                                    {
                                        proposals.map(p =>
                                            <ProposalCard
                                                key={p.id}
                                                navProps={props}
                                                proposal={p}
                                                setModalOpen={setModalOpen}
                                            />)
                                    }
                                </ListContainer>
                                <ResolveProposalModal modalOpen={modalOpen} setModalOpen={setModalOpen}/>
                            </>
                        );
                    }}
                />

                <Route
                    exact
                    path={'/dashboard/proposals/:subsection(improve)'}
                    render={props => {
                        proposals = proposals.filter(p => p.author === currEmail && p.status === PROPOSAL_ACCEPTED);
                        return (
                            <>
                                <ListContainer {...props}>
                                    {
                                        proposals.map(p =>
                                            <ProposalCard
                                                key={p.id}
                                                navProps={props}
                                                proposal={p}
                                                setModalOpen={setModalOpen}
                                            />)
                                    }
                                </ListContainer>
                                <AddEditProposalModal
                                    improveScenario={true}
                                    modalOpen={modalOpen}
                                    setModalOpen={setModalOpen}
                                />
                            </>
                        );
                    }}
                />

                <Route path={'*'} component={NotFound}/>
            </Switch>
        </div>
    );
};

export default ProposalSection;
