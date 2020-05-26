import React from 'react';
import './ConferenceList.css';
import AddEditConferenceModal from "../AddEditConferenceModal/AddEditConferenceModal";
import Conference from "../Conference/Conference";
import {Button} from "baseui/button";
import PropTypes from "prop-types";
import {useSelector} from "react-redux";
import {AUTHOR, PCM, SCM} from "../../userClaims";
import ParticipateOptions from "../ParticipateOptions/ParticipateOptions";

const ConferenceList = (props) => {
  const [editConference, setEditConference] = React.useState(null);
  const [open, setOpen] = React.useState(false);
  // const [openOptions, setOpenOptions] = React.useState(false);
  // const [participateConference, setParticipateConference] = React.useState(null);



  let conferences = useSelector(state => state.conference.conferences);
  let claims = useSelector(state =>state.user.claims);
  console.log(conferences);

  return(
    <div className="ConferenceList" data-testid="ConferenceList">
      {
        claims.includes(PCM)
          &&
        conferences.map(conference => <Conference
            key={conference.id}
            conference={conference}
            setEditConference={setEditConference}
            // setParticipateConference={setParticipateConference}
            setOpen={setOpen}
            // setOpenOptions={setOpenOptions}
        />)
      }
      {/*<ParticipateOptions isOpenOptions={openOptions} setIsOpenOptions={setIsOpenOptions} conference={participateConference} />*/}
      <AddEditConferenceModal isOpen={open} setIsOpen={setOpen} conference={editConference}> </AddEditConferenceModal>
      <Button onClick={() => {setEditConference(null); setOpen(true);}}>Add Conference</Button>
    </div>
      )
};


export default ConferenceList;
