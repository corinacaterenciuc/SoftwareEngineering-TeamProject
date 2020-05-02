import React from 'react';
import './ConferenceList.css';
import AddEditConferenceModal from "../AddEditConferenceModal/AddEditConferenceModal";
import Conference from "../Conference/Conference";
import {Button} from "baseui/button";
import PropTypes from "prop-types";
import {useSelector} from "react-redux";

const ConferenceList = (props) => {
  const [editConference, setEditConference] = React.useState(null);
  const [open, setOpen] = React.useState(false);

  let conferences = useSelector((state) => state.conferences);

  return(
    <div className="ConferenceList" data-testid="ConferenceList">
      {
        conferences.map(conference => <Conference
            key={conference.id}
            conference={conference}
            setEditConference={setEditConference}
            setOpen={setOpen}/>)
      }
      <AddEditConferenceModal isOpen={open} setIsOpen={setOpen} conference={editConference}> </AddEditConferenceModal>
      <Button onClick={() => {setEditConference(null); setOpen(true);}}>Add Conference</Button>
    </div>
      )
};


export default ConferenceList;