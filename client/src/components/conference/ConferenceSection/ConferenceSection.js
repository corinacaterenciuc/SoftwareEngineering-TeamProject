import React from 'react';
import './ConferenceSection.css';
import SectionNavigation from "../../navigation/SectionNavigation/SectionNavigation";
import MyConferencesPage from "../MyConferencesPage/MyConferencesPage";
import AllConferencesPage from "../AllConferencesPage/AllConferencesPage";
import NotFound from "../../navigation/NotFound/NotFound";
import {Route, Switch} from "react-router-dom";

const ConferenceSection = () => {
    const locations = [
        {title: 'My Conferences', itemId: '/dashboard/conferences/my-conferences',},
        {title: 'All Conferences', itemId: '/dashboard/conferences/all-conferences',},
    ];

    return (
        <div className={"ConferenceSection"}>
            <SectionNavigation locations={locations}/>
            <Switch>
                <Route exact path={'/dashboard/conferences/my-conferences'} component={MyConferencesPage}/>

                <Route exact path={'/dashboard/conferences/all-conferences'} component={AllConferencesPage}/>

                <Route path={'*'} component={NotFound}/>
            </Switch>
        </div>
    );
};

ConferenceSection.propTypes = {};

ConferenceSection.defaultProps = {};

export default ConferenceSection;
