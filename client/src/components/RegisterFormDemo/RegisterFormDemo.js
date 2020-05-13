import React, {useEffect} from 'react';

import {Modal, ROLE, ModalHeader, ModalBody, ModalButton, ModalFooter} from "baseui/modal";
import { FormControl } from 'baseui/form-control';
import { Input } from 'baseui/input';
import { SIZE, KIND } from 'baseui/button';


const RegisterFormDemo = (props) => {


    const[name, setName] = React.useState('');
    const[nameValid, setNameValid] = React.useState('');

    const[email, setEmail] = React.useState('');
    const[emailValid, setEmailValid] = React.useState('');

    const[password, setPassword] = React.useState('');
    const[passwordValid, setPasswordValid] = React.useState('');

    useEffect(() =>{
        setNameValid(name!=null);
    }, [name]);

    useEffect(() =>{
        setEmailValid(name!=null);
    }, [name]);

    useEffect(() =>{
        setNameValid(email!=null);
    }, [email]);

    useEffect(() =>{
        setPasswordValid(password!=null);
    }, [password]);


    return(
        <div className="RegisterFormDemo" data-testid="RegisterFormDemo">
             <Modal
                onClose={() => props.setIsOpen(false)}
                closeable
                isOpen={props.isOpen}
                animate
                autoFocus
                size={SIZE.default}
                role={ROLE.default}
            >
                <ModalHeader>Register</ModalHeader>
                <ModalBody>
                    <FormControl>
                        <Input
                            value={name}
                            onChange={e => setName(e.target.value)}
                            error = {!nameValid}
                            placeholder = "Name"
                            size={SIZE.compact}
                        />
                    </FormControl>

                    <FormControl>
                        <Input
                            value={email}
                            onChange={e => setEmail(e.target.value)}
                            error = {!emailValid}
                            placeholder = "Email"
                            size={SIZE.compact}
                        />
                    </FormControl>

                    <FormControl>
                        <Input
                            value={password}
                            onChange={e => setPassword(e.target.value)}
                            error = {!passwordValid}
                            placeholder = "Password"
                            size={SIZE.compact}
                        />
                    </FormControl>
                </ModalBody>

                <ModalFooter>
                    <ModalButton kind = {KIND.primary} onClick={() => alert('Registered')}>Register</ModalButton>
                    <ModalButton kind = {KIND.secondary} onClick={() => props.setIsOpen(false)}>Cancel</ModalButton>
                </ModalFooter>
            </Modal>
        </div>
    );
};

RegisterFormDemo.propTypes = {};
RegisterFormDemo.defaultProps = {};

export default RegisterFormDemo;