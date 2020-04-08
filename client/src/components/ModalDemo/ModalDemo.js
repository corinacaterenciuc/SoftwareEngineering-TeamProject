import React from 'react';
import './ModalDemo.css';
import {Button} from "baseui/button";
import AddConferenceModal from "../AddConferenceModal/AddConferenceModal";

const ModalDemo = () => {

    const [isOpen, setIsOpen] = React.useState(false);

    return (
        <div className="ModalDemo" data-testid="ModalDemo">
            <h1>ModalDemo Component</h1>
            <AddConferenceModal isOpen={isOpen} setIsOpen={setIsOpen}/>
            <Button onClick={() => setIsOpen(true)}>Open</Button>
        </div>
    );
};

ModalDemo.propTypes = {};

ModalDemo.defaultProps = {};

export default ModalDemo;
