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

const ProposalSection = () => {
    const [modalOpen, setModalOpen] = useState(false);

    const locations = [
        {title: 'My Proposals', itemId: '/dashboard/proposals/my-proposals',},
        {title: 'All Proposals', itemId: '/dashboard/proposals/all-proposals',},
        {title: 'Bidding', itemId: '/dashboard/proposals/bidding'},
        {title: 'Review', itemId: "/dashboard/proposals/review"},
        {title: 'Resolve', itemId: '/dashboard/proposals/resolve'}
    ];

    const proposals = [
        {
            id: 0,
            conferenceId: 2,
            proposalName: 'Supercool',
            abstract: 'A really long ass proposal abstract I dont really see how we can get this through I wish I had just stayed in bed.',
            topics: ['ML', 'Economy'],
            keywords: ['Neural Networks', 'Stocks', 'Prediction'],
            bidders: ['bratuandrei0@gmail.com']
        }
    ];

    return (
        <div className={"ProposalSection"}>
            <SectionNavigation locations={locations}/>
            <Switch>
                <Route
                    exact
                    path={'/dashboard/proposals/:subsection(all-proposals)'}
                    render={(props) =>
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
                            {/*<BidModal modalOpen={modalOpen} setModalOpen={setModalOpen}/>*/}
                        </>
                    }
                />

                <Route
                    exact
                    path={'/dashboard/proposals/:subsection(my-proposals)'}
                    render={(props) =>
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
                            {/*<BidModal modalOpen={modalOpen} setModalOpen={setModalOpen}/>*/}
                        </>
                    }
                />

                <Route
                    exact
                    path={'/dashboard/proposals/:subsection(bidding)'}
                    render={(props) =>
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
                            <BidModal modalOpen={modalOpen} setModalOpen={setModalOpen}/>
                        </>
                    }
                />

                <Route
                    exact
                    path={'/dashboard/proposals/:subsection(review)'}
                    render={(props) =>
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
                            <ReviewProposalModal modalOpen={modalOpen} setModalOpen={setModalOpen}/>
                        </>
                    }
                />

                <Route
                    exact
                    path={'/dashboard/proposals/:subsection(resolve)'}
                    render={(props) =>
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
                    }
                />

                <Route path={'*'} component={NotFound}/>
            </Switch>
        </div>
    );
};

export default ProposalSection;
