import React, {useState} from 'react';
import './ConferenceModalDemo.css';
import AddEditConferenceModal from "../AddEditConferenceModal/AddEditConferenceModal";
import {Button} from "baseui/button";

const ConferenceModalDemo = () => {
    const [open, setIsOpen] = useState(false);

    return (
        <div className="ConferenceModalDemo" data-testid="ConferenceModalDemo">
            <AddEditConferenceModal isOpen={open} setIsOpen={setIsOpen} users={[{label: 'Andrei', id: 0}, {label: 'Felix', id: 1}]}/>
            <Button onClick={() => setIsOpen(!open)}>Wow</Button>
        </div>
    );
};

ConferenceModalDemo.propTypes = {};

ConferenceModalDemo.defaultProps = {};

export default ConferenceModalDemo;
