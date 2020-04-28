import React, {useEffect, useRef} from 'react';
import PropTypes from 'prop-types';
import './ReviewProposalModal.css';
import {Modal, SIZE, ROLE, ModalHeader, ModalBody, ModalFooter, ModalButton} from "baseui/modal";
import {FormControl} from "baseui/form-control";
import {Textarea} from "baseui/textarea";
import {Button, KIND} from "baseui/button";
import {Select} from "baseui/select";

const ReviewProposalModal = (props) => {
    const [formValid, setFormValid] = React.useState(props != null);
    const firstRender = useRef(true);

    const [grade, setGrade] = React.useState(props != null ? [props.grade] : []);
    const [gradeValid, setGradeValid] = React.useState(props != null);
    const [gradeInputVisited, setGradeInputVisited] = React.useState(false);

    const [justification, setJustification] = React.useState(props != null ? props.justification : '');
    const [justificationValid, setJustificationValid] = React.useState(props != null);
    const [justificationInputVisited, setJustificationInputVisited] = React.useState(false);

    useEffect(() => {
        if (firstRender.current)
        {
            // Else input fields will be marked as error from the start
            // since useEffect is also called at update but also at render
            firstRender.current = false;
            return;
        }
        setGradeValid(grade.length !== 0);
        setJustificationValid(justification !== '');
    }, [grade, justification]);

    useEffect(() => {
        setFormValid(gradeValid && justificationValid);
    }, [gradeValid, justificationValid]);

    return (
        <div className="ReviewProposalModal" data-testid="ReviewProposalModal">
            <Modal
                onClose={() => props.setIsOpen(false)}
                closeable
                isOpen={props.isOpen}
                animate
                autoFocus
                size={SIZE.default}
                role={ROLE.default}
            >
                <ModalHeader>{props.proposal.title}</ModalHeader>
                <ModalBody>
                    <FormControl label={() => "Abstract"}>
                        <Textarea
                            value={props.proposal.abstract}
                            disabled
                            size={SIZE.default}
                        />
                    </FormControl>

                    <form method="get" action={props.proposal_url}>
                        <Button
                            type="submit"
                            kind={KIND.secondary}
                            size={SIZE.compact}>
                            Download Proposal
                        </Button>
                    </form>

                    <FormControl label={() => "Grade"}>
                        <Select
                            backspaceRemoves={false}
                            clearable={false}
                            options={[
                                { label: "Strong Reject (-3)", id: -3 },
                                { label: "Reject (-2)", id: -2 },
                                { label: "Weak Reject (-1)", id: -1 },
                                { label: "Borderline (0)", id: 0 },
                                { label: "Weak Accept (1)", id: 1 },
                                { label: "Accept (2)", id: 2 },
                                { label: "Strong Accept (3)", id: 3 }
                            ]}
                            value={grade}
                            error={gradeInputVisited && !gradeValid}
                            placeholder="Grade the Proposal"
                            required
                            onChange={params => {setGradeInputVisited(true); setGrade(params.value)}}
                        />
                    </FormControl>

                    <FormControl label={() => "Justification"}>
                        <Textarea
                            value={justification}
                            error={justificationInputVisited && !justificationValid}
                            size={SIZE.default}
                            required
                            onChange={(e) => {setJustificationInputVisited(true); setJustification(e.target.value)}}
                        />
                    </FormControl>

                    <ModalFooter>
                        <ModalButton kind={KIND.secondary} onClick={() => props.setIsOpen(false)} size={SIZE.default}>Cancel</ModalButton>
                        <ModalButton size={SIZE.compact} disabled={!formValid} onClick={() => alert('Much action, wow..')}>Submit</ModalButton>
                    </ModalFooter>
                </ModalBody>
            </Modal>
        </div>
    );
};

ReviewProposalModal.propTypes = {
    grade: PropTypes.exact({
        label: PropTypes.string.isRequired,
        id: PropTypes.number.isRequired
    }),
    justification: PropTypes.string,
    proposal_url: PropTypes.string.isRequired,
    isOpen: PropTypes.bool.isRequired,
    setIsOpen: PropTypes.func.isRequired
};

ReviewProposalModal.defaultProps = {
    grade: null,
    justification: null
};

export default ReviewProposalModal;
