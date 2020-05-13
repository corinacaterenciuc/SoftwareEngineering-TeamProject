import React, {useEffect} from 'react';
import 'D:\\An II\\Sem2\\ISS\\VSCODE\\super-conference\\client\\src\\components\\LoginFormDemo\\LoginFormDemo.css'
import {Modal, ROLE, ModalHeader, ModalBody, ModalButton, ModalFooter} from "baseui/modal";
import { FormControl } from 'baseui/form-control';
import { Input } from 'baseui/input';
import { SIZE, KIND } from 'baseui/button';


const LoginFormDemo = (props) => {

    const[email, setEmail] = React.useState('');
    const[emailValid, setEmailValid] = React.useState('');

    const[password, setPassword] = React.useState('');
    const[passwordValid, setPasswordValid] = React.useState('');

    useEffect(() => {
        setEmailValid(email!=null);
    }, [email]);

    useEffect(() =>{
        setPasswordValid(password!=null);
    }, [password]);


    return(
        <div className="LoginFormDemo" data-testid="LoginFormDemo">
            <Modal
                onClose={() => props.setIsOpen(false)}
                closeable
                isOpen={props.isOpen}
                animate
                autoFocus
                size={SIZE.default}
                role={ROLE.default}
            >
                    <ModalHeader>Login into your account</ModalHeader>
                    <ModalBody>
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
                                value2={password}
                                onChange={e => setPassword(e.target.value2)}
                                error = {!passwordValid}
                                placeholder = "Password"
                                size={SIZE.compact}
                            />
                        </FormControl>
                    </ModalBody>

                    <ModalFooter>
                        <ModalButton kind = {KIND.primary} onClick={() => alert('Logged in')}>Login</ModalButton>
                        <ModalButton kind = {KIND.secondary} onClick={() => props.setIsOpen(false)}>Cancel</ModalButton>

                    </ModalFooter>
            </Modal>
        </div>
    );
};

LoginFormDemo.propTypes = {};
LoginFormDemo.defaultProps = {};
export default LoginFormDemo;