import React, {useState} from 'react';
import './BidModal.css';
import {useDispatch, useSelector} from "react-redux";
import {Modal, ModalBody, ModalButton, ModalFooter, ModalHeader} from "baseui/modal";
import proposalService from "../../../redux/proposal/proposalService";

const BidModal = (props) => {
    const dispatch = useDispatch();
    const currentEmail = useSelector(state => state.auth.email);
    const currentProposal = useSelector(state => state.context.currentProposal);
    const { open, setIsOpen } = props;
    const close = () => setIsOpen(false);

    return (
        <div className="BidModal">
            <Modal onClose={close} isOpen={open}>
                <ModalHeader>Confirm</ModalHeader>
                <ModalBody>Are you sure you want to bid for this proposal?</ModalBody>
                <ModalFooter>
                    <ModalButton kind="tertiary" onClick={close}>
                        Cancel
                    </ModalButton>
                    <ModalButton onClick={() => {
                        dispatch(proposalService.bid())
                    }}>Okay</ModalButton>
                </ModalFooter>
            </Modal>
        </div>
    );
};

BidModal.propTypes = {};

BidModal.defaultProps = {};

export default BidModal;
