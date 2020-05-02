import React, {useEffect} from 'react';
import PropTypes from 'prop-types'
import './AddEditConferenceModal.css';
import {Modal, ModalBody, ModalButton, ModalFooter, ModalHeader, ROLE} from "baseui/modal";
import {KIND} from "baseui/button";
import {Input, SIZE} from "baseui/input";
import {Datepicker} from "baseui/datepicker";
import {FormControl} from "baseui/form-control";
import {Textarea} from "baseui/textarea";
import {Select} from "baseui/select";
import {conferenceType} from "../../propTypes";
import {useSelector} from "react-redux";

const AddEditConferenceModal = (props) => {

    /*
        The form starts as valid in the edit scenario - i.e. all fields
        were successfully completed at least once before.

    */
    let conference = useSelector(state => state.conference.editConference);
    const isConferenceNotNull = conference !== null;

    const [formValid, setFormValid] = React.useState(props.scmUsers != null && props.pcmUsers != null);

    const [conferenceName, setConferenceName] = React.useState(isConferenceNotNull ? conference.name : '');
    const [conferenceNameValid, setConferenceNameValid] = React.useState(isConferenceNotNull);

    const [conferenceDescription, setConferenceDescription] = React.useState(
        isConferenceNotNull ? conference.description : ''
    );
    const [conferenceDescriptionValid, setConferenceDescriptionValid] = React.useState(isConferenceNotNull);

    const [zeroDeadline, setZeroDeadline] = React.useState(
        isConferenceNotNull ? conference.zeroDeadline : null);
    const [zeroDeadlineValid, setZeroDeadlineValid] = React.useState(isConferenceNotNull);

    const [abstractDeadline, setAbstractDeadline] = React.useState(
        isConferenceNotNull ? conference.abstractDeadline : null);
    const [abstractDeadlineValid, setAbstractDeadlineValid] = React.useState(isConferenceNotNull);

    const [proposalDeadline, setProposalDeadline] = React.useState(
        isConferenceNotNull ? conference.proposalDeadline : null);
    const [proposalDeadlineValid, setProposalDeadlineValid] = React.useState(isConferenceNotNull);

    const [biddingDeadline, setBiddingDeadline] = React.useState(
        isConferenceNotNull ? conference.biddingDeadline : null);
    const [biddingDeadlineValid, setBiddingDeadlineValid] = React.useState(isConferenceNotNull);

    const [evaluationDeadline, setEvaluationDeadline] = React.useState(
        isConferenceNotNull ? conference.evaluationDeadline : null);
    const [evaluationDeadlineValid, setEvaluationDeadlineValid] = React.useState(isConferenceNotNull);

    const [presentationDeadline, setPresentationDeadline] = React.useState(
        isConferenceNotNull ? conference.presentationDeadline : null);
    const [presentationDeadlineValid, setPresentationDeadlineValid] = React.useState(isConferenceNotNull);

    const [scmUsers, setScmUsers] = React.useState(props.scmUsers);
    const [scmUsersValid, setScmUsersValid] = React.useState(props.scmUsers != null);

    const [pcmUsers, setPcmUsers] = React.useState(props.pcmUsers);
    const [pcmUsersValid, setPcmUsersValid] = React.useState(props.pcmUsers != null);

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
        setScmUsersValid(scmUsers != null)
    }, [scmUsers]);

    useEffect(() => {
        setPcmUsersValid(pcmUsers != null)
    }, [pcmUsers]);

    useEffect(() => {
      setFormValid([
          conferenceNameValid, conferenceDescriptionValid, zeroDeadlineValid, proposalDeadlineValid,
          biddingDeadlineValid, evaluationDeadlineValid, presentationDeadlineValid, scmUsersValid, pcmUsersValid]
          .every(v => v === true))
    }, [
        conferenceNameValid, conferenceDescriptionValid, zeroDeadlineValid, proposalDeadlineValid,
        biddingDeadlineValid, evaluationDeadlineValid, presentationDeadlineValid, scmUsersValid, pcmUsersValid]
    );

    useEffect(() =>
    {
        const conferenceNotNull = conference !== null;
        setConferenceName(conferenceNotNull ? conference.name : '');
        setConferenceDescription( conferenceNotNull ? conference.description : '');
        setZeroDeadline(conferenceNotNull ? conference.zeroDeadline : null);
        setAbstractDeadline(conferenceNotNull ? conference.abstractDeadline : null);
        setProposalDeadline(conferenceNotNull ? conference.proposalDeadline : null);
        setBiddingDeadline(conferenceNotNull ? conference.biddingDeadline : null);
        setEvaluationDeadline(conferenceNotNull ? conference.biddingDeadline : null);
        setPresentationDeadline(conferenceNotNull ? conference.presentationDeadline : null);
    }, [conference]);

    return (
        <div className="ConferenceModal" data-testid="AddEditConferenceModal">
            <Modal
                onClose={() => props.setIsOpen(false)}
                closeable
                isOpen={props.isOpen}
                animate
                autoFocus
                size={SIZE.default}
                role={ROLE.default}>
                <ModalHeader>Add Conference</ModalHeader>
                <ModalBody>
                    <FormControl label={() => "Conference Name"}>
                        <Input
                            value={conferenceName}
                            onChange={e => setConferenceName(e.target.value)}
                            error={!conferenceNameValid}
                            placeholder="Input conference name"
                            size={SIZE.compact}/>
                    </FormControl>

                    <FormControl label={() => "Conference Description"}>
                        <Textarea
                            value={conferenceDescription}
                            onChange={e => setConferenceDescription(e.target.value)}
                            error={!conferenceDescriptionValid}
                            placeholder="Input conference description"
                            size={SIZE.compact}/>
                    </FormControl>

                    <FormControl label={() => "Zero Deadline"}>
                        <Datepicker
                            value={zeroDeadline}
                            onChange={({ date }) => setZeroDeadline(date)}
                            error={!zeroDeadlineValid}
                            size={SIZE.compact}/>
                    </FormControl>

                    <FormControl label={() => "Submit Abstract Deadline"} caption={() => "Leave blank if only integral Proposals are accepted."}>
                        <Datepicker
                            value={abstractDeadline}
                            onChange={({ date }) => setAbstractDeadline(date)}
                            error={!abstractDeadlineValid}
                            size={SIZE.compact}/>
                    </FormControl>

                    <FormControl label={() => "Submit Proposal Deadline"}>
                        <Datepicker
                            value={proposalDeadline}
                            onChange={({ date }) => setProposalDeadline(date)}
                            error={!proposalDeadlineValid}
                            size={SIZE.compact}/>
                    </FormControl>

                    <FormControl label={() => "Bidding Deadline"}>
                        <Datepicker
                            value={biddingDeadline}
                            onChange={({ date }) => setBiddingDeadline(date)}
                            error={!biddingDeadlineValid}
                            size={SIZE.compact}/>
                    </FormControl>

                    <FormControl label={() => "Evaluation Deadline"}>
                        <Datepicker
                            value={evaluationDeadline}
                            onChange={({ date }) => setEvaluationDeadline(date)}
                            error={!evaluationDeadlineValid}
                            size={SIZE.compact}/>
                    </FormControl>

                    <FormControl label={() => "Presentation Deadline"}>
                        <Datepicker
                            value={presentationDeadline}
                            onChange={({ date }) => setPresentationDeadline(date)}
                            error={!presentationDeadlineValid}
                            size={SIZE.compact}/>
                    </FormControl>

                    <FormControl label={() => "SCM Members"}>
                        <Select
                            options={props.users}
                            value={scmUsers}
                            placeholder="Select SCM members"
                            error={!scmUsersValid}
                            onChange={params => setScmUsers(params.value)}
                            multi
                            closeOnSelect={false}/>
                    </FormControl>

                    <FormControl label={() => "PCM Members"}>
                        <Select
                            options={props.users}
                            value={pcmUsers}
                            error={!pcmUsersValid}
                            placeholder="Select PCM members"
                            onChange={params => setPcmUsers(params.value)}
                            multi
                            closeOnSelect={false}/>
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

const userType = PropTypes.exact({label: PropTypes.string.isRequired, id: PropTypes.number.isRequired});

AddEditConferenceModal.propTypes = {
    isOpen: PropTypes.bool.isRequired,
    setIsOpen: PropTypes.func.isRequired,
    conference: conferenceType,
    scmUsers: PropTypes.arrayOf(userType),
    pcmUsers: PropTypes.arrayOf(userType),
    users: PropTypes.arrayOf(userType)
};

AddEditConferenceModal.defaultProps = {
};

export default AddEditConferenceModal;
