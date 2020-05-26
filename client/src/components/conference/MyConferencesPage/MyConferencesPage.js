import React from 'react';
import './MyConferencesPage.css';
import ConferenceList from "../../ConferenceList/ConferenceList";

const MyConferencesPage = () => (
    <div className="MyConferencesPage">
        <ConferenceList />
    </div>
);

MyConferencesPage.propTypes = {};

MyConferencesPage.defaultProps = {};

export default MyConferencesPage;
