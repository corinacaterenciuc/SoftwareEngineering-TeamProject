import React from 'react';
import './ResolveProposalModal.css';
import {Modal, ModalBody, ModalButton, ModalFooter, ROLE, SIZE} from "baseui/modal";
import {KIND} from "baseui/button";

const ResolveProposalModal = (props) => {
    const {modalOpen, setModalOpen} = props;

    return (
        <div className="ResolveProposalModal" data-testid="ResolveProposalModal">
            <Modal
                onClose={() => setModalOpen(false)}
                closeable
                isOpen={modalOpen}
                animate
                autoFocus
                size={SIZE.default}
                role={ROLE.default}>
                <ModalBody>
                    <ModalFooter>
                        <ModalButton
                            kind={KIND.secondary}
                            onClick={() => setModalOpen(false)}
                            size={SIZE.default}>
                            Cancel
                        </ModalButton>
                        <ModalButton
                            kind={KIND.negative}
                            size={SIZE.compact}
                            onClick={() => setModalOpen(false)}
                            overrides={{
                                BaseButton: {style: ({$theme}) => ({backgroundColor: $theme.colors.negative400})}
                            }}>
                            Reject
                        </ModalButton>
                        <ModalButton
                            kind={KIND.positive}
                            onClick={() => setModalOpen(false)}
                            size={SIZE.default}
                            overrides={{
                                BaseButton: {style: ({$theme}) => ({backgroundColor: $theme.colors.positive400})}
                            }}>
                            Approve
                        </ModalButton>
                    </ModalFooter>
                </ModalBody>
            </Modal>
        </div>
    );
};

export default ResolveProposalModal;
