import React, {useEffect} from 'react';
import {ROLE, ModalHeader, ModalBody, ModalButton, ModalFooter} from "baseui/modal";
import { FormControl } from 'baseui/form-control';
import { Input} from 'baseui/input';
import { SIZE, KIND } from 'baseui/button';
import { StyledLink } from "baseui/link";
import {
    Card
  } from "baseui/card";


const LoginRegister = (props) => {
    
    const[account, setAccount] = React.useState(true);

    const[name, setName] = React.useState('');
    const[nameValid, setNameValid] = React.useState('');

    const[email, setEmail] = React.useState('');
    const[emailValid, setEmailValid] = React.useState('');

    const[password, setPassword] = React.useState('');
    const[passwordValid, setPasswordValid] = React.useState('');

    useEffect(() =>{
        setNameValid(name!=null);
    }, [name]);

    useEffect(() => {
        setEmailValid(email!=null);
    }, [email]);

    useEffect(() =>{
        setPasswordValid(password!=null);
    }, [password]);


    return(
        <div className="LoginRegisterFormDemo" data-testid="LoginRegisterFormDemo">
            <Card
                onClose={() => props.setIsOpen(false)}
                closeable
                isOpen={props.isOpen}
                animate
                autoFocus
                size={SIZE.default}
                role={ROLE.default}
            >
                    <ModalHeader>{account ? 'Register' : 'Login' }</ModalHeader>
                    <ModalBody>
                        {account && 
                                        <FormControl>
                                        <Input
                                            value={name}
                                            onChange={e => setName(e.target.value)}
                                            error = {!nameValid}
                                            placeholder = "Name"
                                            size={SIZE.compact}
                                        />
                                        </FormControl>
                        }
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

                        <StyledLink 
                                href="#"
                                onClick={(e) => { 
                                        e.preventDefault();
                                        setAccount(!account); }} >
                                {account ? "Already have an account?" : "You don't have an account?Register here"}
                        </StyledLink>
                    </ModalBody>

                    <ModalFooter>
                        <ModalButton kind = {KIND.primary} onClick={() => alert('Mission accomplished')}>{account ? 'Register' : 'Login' }</ModalButton>
                        <ModalButton kind = {KIND.secondary} onClick={() => props.setIsOpen(false)}>Cancel</ModalButton>
                    </ModalFooter>
            </Card>
        </div>
    );
};

LoginRegister.propTypes = {};
LoginRegister.defaultProps = {};
export default LoginRegister;