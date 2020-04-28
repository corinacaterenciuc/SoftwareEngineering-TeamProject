import React, {useEffect} from 'react';
import PropTypes from 'prop-types'
import './AddConferenceModal.css';
import {Modal, ROLE, ModalHeader, ModalBody, ModalButton, ModalFooter} from "baseui/modal";
import {KIND} from "baseui/button";
import {Input, SIZE} from "baseui/input";
import {Datepicker} from "baseui/datepicker";
import {FormControl} from "baseui/form-control";
import {Textarea} from "baseui/textarea";

const AddConferenceModal = (props) => {

    const [formValid, setFormValid] = React.useState(false);

    const [conferenceName, setConferenceName] = React.useState('');
    const [conferenceNameValid, setConferenceNameValid] = React.useState(true);

    const [conferenceDescription, setConferenceDescription] = React.useState('');
    const [conferenceDescriptionValid, setConferenceDescriptionValid] = React.useState(true);

    const [zeroDeadline, setZeroDeadline] = React.useState(null);
    const [zeroDeadlineValid, setZeroDeadlineValid] = React.useState(true);

    const [abstractDeadline, setAbstractDeadline] = React.useState(null);
    const [abstractDeadlineValid, setAbstractDeadlineValid] = React.useState(true);

    const [proposalDeadline, setProposalDeadline] = React.useState(null);
    const [proposalDeadlineValid, setProposalDeadlineValid] = React.useState(true);

    const [biddingDeadline, setBiddingDeadline] = React.useState(null);
    const [biddingDeadlineValid, setBiddingDeadlineValid] = React.useState(true);

    const [evaluationDeadline, setEvaluationDeadline] = React.useState(null);
    const [evaluationDeadlineValid, setEvaluationDeadlineValid] = React.useState(true);

    const [presentationDeadline, setPresentationDeadline] = React.useState(null);
    const [presentationDeadlineValid, setPresentationDeadlineValid] = React.useState(true);

    useEffect(() => {
       setConferenceNameValid(conferenceName != null);
    }, [conferenceName]);

    useEffect(() => {
        setConferenceDescriptionValid(conferenceDescription != null)
    }, [conferenceDescription]);

    useEffect(() => {
        setZeroDeadlineValid(zeroDeadline != null && zeroDeadline > new Date())
    }, [zeroDeadline]);

    useEffect(() => {
        setAbstractDeadlineValid((abstractDeadline == null) || (abstractDeadline > zeroDeadline))
    }, [abstractDeadline, zeroDeadline]);

    useEffect(() => {
        setProposalDeadlineValid(proposalDeadline != null &&
            ((abstractDeadline == null && proposalDeadline > zeroDeadline) ||
                (abstractDeadline != null && proposalDeadline > abstractDeadline))
        );
    }, [zeroDeadline, abstractDeadline, proposalDeadline]);

    useEffect(() => {
        setBiddingDeadlineValid(biddingDeadline != null && biddingDeadline > proposalDeadline)
    }, [proposalDeadline, biddingDeadline]);

    useEffect(() => {
        setEvaluationDeadlineValid(evaluationDeadline != null && evaluationDeadline > biddingDeadline);
    }, [biddingDeadline, evaluationDeadline]);

    useEffect(() => {
        setPresentationDeadlineValid(presentationDeadline != null && presentationDeadline > evaluationDeadline);
    }, [presentationDeadline, evaluationDeadline]);

    useEffect(() => {
      setFormValid([
          conferenceNameValid, conferenceDescriptionValid, zeroDeadlineValid, proposalDeadlineValid,
          biddingDeadlineValid, evaluationDeadlineValid, presentationDeadlineValid].every(v => v === true))
    }, [
        conferenceNameValid, conferenceDescriptionValid, zeroDeadlineValid, proposalDeadlineValid,
        biddingDeadlineValid, evaluationDeadlineValid, presentationDeadlineValid]
    );

    return (
        <div className="ConferenceModal" data-testid="AddConferenceModal">
            <Modal
                onClose={() => props.setIsOpen(false)}
                closeable
                isOpen={props.isOpen}
                animate
                autoFocus
                size={SIZE.default}
                role={ROLE.default}
            >
                <ModalHeader>Add Conference</ModalHeader>
                <ModalBody>
                    <FormControl label={() => "Conference Name"}>
                        <Input
                            value={conferenceName}
                            onChange={e => setConferenceName(e.target.value)}
                            error={!conferenceNameValid}
                            placeholder="Input conference name"
                            size={SIZE.compact}
                        />
                    </FormControl>

                    <FormControl label={() => "Conference Description"}>
                        <Textarea
                            value={conferenceDescription}
                            onChange={e => setConferenceDescription(e.target.value)}
                            error={!conferenceDescriptionValid}
                            placeholder="Input conference description"
                            size={SIZE.compact}
                        />
                    </FormControl>

                    <FormControl label={() => "Zero Deadline"}>
                        <Datepicker
                            value={zeroDeadline}
                            onChange={({ date }) => setZeroDeadline(date)}
                            error={!zeroDeadlineValid}
                            size={SIZE.compact}
                        />
                    </FormControl>

                    <FormControl label={() => "Submit Abstract Deadline"} caption={() => "Leave blank if only integral Proposals are accepted.Z"}>
                        <Datepicker
                            value={abstractDeadline}
                            onChange={({ date }) => setAbstractDeadline(date)}
                            error={!abstractDeadlineValid}
                            size={SIZE.compact}
                        />
                    </FormControl>

                    <FormControl label={() => "Submit Proposal Deadline"}>
                        <Datepicker
                            value={proposalDeadline}
                            onChange={({ date }) => setProposalDeadline(date)}
                            error={!proposalDeadlineValid}
                            size={SIZE.compact}
                        />
                    </FormControl>

                    <FormControl label={() => "Bidding Deadline"}>
                        <Datepicker
                            value={biddingDeadline}
                            onChange={({ date }) => setBiddingDeadline(date)}
                            error={!biddingDeadlineValid}
                            size={SIZE.compact}
                        />
                    </FormControl>

                    <FormControl label={() => "Evaluation Deadline"}>
                        <Datepicker
                            value={evaluationDeadline}
                            onChange={({ date }) => setEvaluationDeadline(date)}
                            error={!evaluationDeadlineValid}
                            size={SIZE.compact}
                        />
                    </FormControl>

                    <FormControl label={() => "Presentation Deadline"}>
                        <Datepicker
                            value={presentationDeadline}
                            onChange={({ date }) => setPresentationDeadline(date)}
                            error={!presentationDeadlineValid}
                            size={SIZE.compact}
                        />
                    </FormControl>
                </ModalBody>

                <ModalFooter>
                    <ModalButton kind={KIND.secondary} onClick={() => props.setIsOpen(false)} size={SIZE.compact}>Cancel</ModalButton>
                    <ModalButton size={SIZE.compact} disabled={!formValid} onClick={() => alert('Much action, wow..')}>Submit</ModalButton>
                </ModalFooter>
            </Modal>
        </div>
    )
};

AddConferenceModal.propTypes = {
    isOpen: PropTypes.bool.isRequired,
    setIsOpen: PropTypes.func.isRequired
};

AddConferenceModal.defaultProps = {};

export default AddConferenceModal;
