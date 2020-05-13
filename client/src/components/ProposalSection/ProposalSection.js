import React from "react";

import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import { MyProposalsPage } from './MyProposalsPage';
import { AllProposalsPage } from './AllProposalsPage';
import './ProposalSection.css';

import ProposalSectionNavigation from "./ProposalSectionNavigation";

export default () => {
    
    return (
        
        <div className="ProposalSection" data-testid="ProposalSection">
        <div className={"ProposalSectionNavigation"}>
          
             <React.Fragment>
                <Router>
                     <ProposalSectionNavigation />
                         <Switch>
                            <Route exact path="/MyProposalsPage" component={MyProposalsPage} />
                            <Route path="/AllProposalsPage" component={AllProposalsPage} />
                    
                        </Switch>
                </Router>
            </React.Fragment>
        </div>
        
      </div>
        
       
    );
  };