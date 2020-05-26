import React from "react";

import './ProposalSection.css';
import SectionNavigation from "../../navigation/SectionNavigation/SectionNavigation";
import {Route, Switch} from "react-router-dom";
import {MyProposalsPage} from "../MyProposalsPage/MyProposalsPage";
import {AllProposalsPage} from "../AllProposalsPage/AllProposalsPage";
import NotFound from "../../navigation/NotFound/NotFound";

const ProposalSection = () => {
    const locations = [
        {title: 'My Proposals', itemId: '/dashboard/proposals/my-proposals',},
        {title: 'All Proposals', itemId: '/dashboard/proposals/all-proposals',},
    ];

    return (
        <div className={"ProposalSection"}>
            <SectionNavigation locations={locations}/>
            <Switch>
                <Route exact path={'/dashboard/proposals/my-proposals'} component={MyProposalsPage}/>

                <Route exact path={'/dashboard/proposals/all-proposals'} component={AllProposalsPage}/>

                <Route path={'*'} component={NotFound}/>
            </Switch>
        </div>
    );
};

export default ProposalSection;
