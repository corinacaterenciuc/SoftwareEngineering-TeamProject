import React from "react";

import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import './ProposalSection.css';

import {MyProposalsPage} from "../MyProposalsPage/MyProposalsPage";
import {AllProposalsPage} from "../AllProposalsPage/AllProposalsPage";
import ProposalSectionNavigation from "../ProposalSectionNavigation/ProposalSectionNavigation";

export default () => (
    <div className={"ProposalSection"}>
        <ProposalSectionNavigation/>
        <Router>
            <Switch>
                <Route exact path="/my-proposals" component={MyProposalsPage} />
                <Route path="/all-proposals" component={AllProposalsPage} />
            </Switch>
        </Router>
    </div>
);
