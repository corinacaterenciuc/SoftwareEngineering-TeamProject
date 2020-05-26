import React from 'react';
import './AllConferencesPage.css';
import SectionNavigation from "../../navigation/SectionNavigation/SectionNavigation";
import NotFound from "../../navigation/NotFound/NotFound";
import {Route, Switch} from "react-router";
import ConferenceList from "../../ConferenceList/ConferenceList";

const AllConferencesPage = () => {


    return (
    <div className="AllConferencesPage">
        <ConferenceList />
    </div>
    );
};

AllConferencesPage.propTypes = {};

AllConferencesPage.defaultProps = {};

export default AllConferencesPage;
