import React, { Component } from 'react';
import './ParticipateOptions.css';
import {useSelector} from "react-redux";
import {Modal, ROLE, ModalHeader, ModalBody, ModalButton, ModalFooter} from "baseui/modal";
import {Input, SIZE} from "baseui/input";


const ParticipateOptions = (props) => {
  let conference = useSelector(state => state.conference.participateOptions);
  const isConferenceNotNull = conference !== null;

  return (
    <div className= "ParticipateOptions">
      <Modal
          onClose={() => props.setIsOpen(false)}
          closeable
          isOpenOptions={props.isOpen}
          animate
          autoFocus
          size={SIZE.default}
          role={ROLE.default}>
        <ModalHeader>Get ready!</ModalHeader>
      </Modal>
    </div>
    )
}

export default ParticipateOptions;