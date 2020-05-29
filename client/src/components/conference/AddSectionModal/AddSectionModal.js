import React, {useState} from 'react';
import './AddSectionModal.css';
import {useDispatch, useSelector} from "react-redux";

const AddSectionModal = (props) => {
    const dispatch = useDispatch();
    const {modalOpen, setModalOpen} = props;
    const ctxConference = useSelector(state => state.context.currentConference);

    const [name, setName] = useState('');

    return (
        <div className="AddSectionModal">

        </div>
    );
};

AddSectionModal.propTypes = {};

AddSectionModal.defaultProps = {};

export default AddSectionModal;
