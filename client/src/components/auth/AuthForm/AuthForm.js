import React, {useEffect} from 'react';
import PropTypes from 'prop-types';
import {ModalBody, ModalButton, ModalFooter, ModalHeader, ROLE} from "baseui/modal";
import {FormControl} from 'baseui/form-control';
import {Input} from 'baseui/input';
import {KIND, SIZE} from 'baseui/button';
import {StyledLink} from "baseui/link";
import {Card} from "baseui/card";
import "./AuthForm.css"


const AuthForm = (props) => {
    const [isRegister, setIsRegister] = React.useState(props.isRegister);

    const [name, setName] = React.useState('');
    const [nameValid, setNameValid] = React.useState(true);

    const [email, setEmail] = React.useState('');
    const [emailValid, setEmailValid] = React.useState(true);

    const [password, setPassword] = React.useState('');
    const [passwordValid, setPasswordValid] = React.useState(true);

    const [formValid, setFormValid] = React.useState(false);

    useEffect(() => {
        setNameValid(name !== '');
    }, [name]);

    useEffect(() => {
        setEmailValid(email !== '');
    }, [email]);

    useEffect(() => {
        setPasswordValid(password !== '');
    }, [password]);

    useEffect(() => {
            setFormValid([nameValid, passwordValid, emailValid].every(v => v === true))
        }, [nameValid, passwordValid, emailValid]
    );

    return (
        <div className="AuthForm" data-testid="AuthForm">
            <Card
                animate
                autoFocus
                size={SIZE.default}
                role={ROLE.default}>
                <ModalHeader>{isRegister ? 'Register' : 'Login'}</ModalHeader>
                <ModalBody>
                    {isRegister &&
                    <FormControl>
                        <Input
                            value={name}
                            onChange={e => setName(e.target.value)}
                            error={!nameValid}
                            placeholder="Name"
                            size={SIZE.compact}
                        />
                    </FormControl>
                    }
                    <FormControl>
                        <Input
                            value={email}
                            onChange={e => setEmail(e.target.value)}
                            error={!emailValid}
                            placeholder="Email"
                            size={SIZE.compact}
                        />
                    </FormControl>

                    <FormControl>
                        <Input
                            value2={password}
                            onChange={e => setPassword(e.target.value2)}
                            error={!passwordValid}
                            placeholder="Password"
                            size={SIZE.compact}
                        />
                    </FormControl>

                    <StyledLink
                        href="#"
                        onClick={(e) => {
                            e.preventDefault();
                            setName('');
                            setPassword('');
                            setEmail('');
                            setIsRegister(!isRegister);
                        }}>
                        {isRegister ? "Already have an account?" : "Don't have an account? Register here"}
                    </StyledLink>
                </ModalBody>

                <ModalFooter>
                    <ModalButton
                        kind={KIND.primary}
                        onClick={() => alert('Mission accomplished')}
                        disabled={!formValid}>
                        {isRegister ? 'Register' : 'Login'}
                    </ModalButton>
                </ModalFooter>
            </Card>
        </div>
    );
};

AuthForm.propTypes = {
    isRegister: PropTypes.bool,
};
AuthForm.defaultProps = {};
export default AuthForm;
