import React from 'react';
import './ConferenceSection.css';
import SectionNavigation from "../../navigation/SectionNavigation/SectionNavigation";
import NotFound from "../../navigation/NotFound/NotFound";
import {Route, Switch} from "react-router-dom";
import ListContainer from "../../generic/ListContainer/ListContainer";
import ProposalCard from "../../proposal/ProposalCard/ProposalCard";

const ConferenceSection = () => {
    const locations = [
        {title: 'My Conferences', itemId: '/dashboard/conferences/my-conferences',},
        {title: 'All Conferences', itemId: '/dashboard/conferences/all-conferences',},
    ];

    return (
        <div className={"ConferenceSection"}>
            <Switch>
                <Route
                    exact
                    path={'/dashboard/conferences/:subsection(all-conferences|my-conferences)'}
                    render={(props) =>
                        <>
                            <SectionNavigation locations={locations}/>
                            <ListContainer {...props}>
                                {
                                    [1, 2, 3, 4].map(i => <ProposalCard key={i}/>)
                                }
                            </ListContainer>
                        </>
                    }
                />

                <Route path={'*'} component={NotFound}/>
            </Switch>
        </div>
    );
};

ConferenceSection.propTypes = {};

ConferenceSection.defaultProps = {};

export default ConferenceSection;
