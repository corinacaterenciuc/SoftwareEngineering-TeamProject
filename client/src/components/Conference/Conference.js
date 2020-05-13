import './Conference.css'
import React from "react";
import {
  Card,
  StyledBody,
  StyledAction
} from "baseui/card";
import { Button } from "baseui/button";
import PropTypes from "prop-types";
import {conferenceType} from "../../propTypes";
import {useDispatch} from "react-redux";
import {SET_EDIT_CONFERENCE} from "../../redux/actions";


export default function Conference(props){

  const dispatch = useDispatch();

  return (
      <div>
        <Card className="cardConference">
          <StyledBody>
            <div className="center-container">
                <ul>
                    <li><h3>Name </h3> {props.conference.name}</li>
                    <li><h3>Description</h3>{props.conference.description}</li>
                    <h3>DEADLINES: </h3>
                    <li><h3>Zero Phase </h3> {props.conference.zeroDeadline.toLocaleDateString("ro-RO")}</li>
                    <li><h3>Abstract </h3> {props.conference.abstractDeadline.toLocaleDateString("ro-RO")}</li>
                    <li><h3>Full Proposal </h3>{props.conference.proposalDeadline.toLocaleDateString("ro-RO")}</li>
                    <li><h3>Bidding </h3> {props.conference.biddingDeadline.toLocaleDateString("ro-RO")}</li>
                    <li><h3>Evaluation </h3> {props.conference.evaluationDeadline.toLocaleDateString("ro-RO")}</li>
                    <li><h3>Presentation </h3> {props.conference.presentationDeadline.toLocaleDateString("ro-RO")}</li>
                </ul>
            </div>
          </StyledBody>
          <StyledAction>
            <Button
                overrides={{
                  BaseButton: { style: { width: "100%" } }}}
                onClick = {() => {
                  dispatch({
                    type: SET_EDIT_CONFERENCE,
                    conference: props.conference
                  });
                  props.setOpen(true);} }>
              Edit
            </Button>


          </StyledAction>
        </Card>
      </div>
  );

}

Conference.propTypes = {
  conference: conferenceType.isRequired,
  setEditConference: PropTypes.func.isRequired,
  setOpen: PropTypes.func.isRequired
};



