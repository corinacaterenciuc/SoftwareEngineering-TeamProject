import React, {useEffect, useRef} from 'react';
import PropTypes from 'prop-types';
import {ModalBody, ModalButton, ModalFooter, ModalHeader, ROLE} from "baseui/modal";
import {FormControl} from 'baseui/form-control';
import {Input} from 'baseui/input';
import {KIND, SIZE} from 'baseui/button';
import {StyledLink} from "baseui/link";
import {Card} from "baseui/card";
import "./AuthForm.css"
import {KIND as TOASTER_KIND, Toast} from "baseui/toast";
import {useDispatch, useSelector} from "react-redux";
import authenticationService from "../../../redux/auth/authenticationService";


const AuthForm = (props) => {
    const dispatch = useDispatch();
    const {isRegister} = props;
    const buttonClicked = useRef(false);
    const jwt = useSelector(state => state.auth.token);
    const [firstName, setFirstName] = React.useState('');
    const [lastName, setLastName] = React.useState('');
    const [email, setEmail] = React.useState('');
    const [password, setPassword] = React.useState('');
    const [formValid, setFormValid] = React.useState(false);

    let authFailed = buttonClicked.current && jwt;

    useEffect(() => {
            setFormValid([
                !isRegister || firstName !== '',
                !isRegister || lastName !== '',
                password !== '',
                email !== ''].every(v => v))
        }, [isRegister, firstName, lastName, password, email]
    );

    return (
        <div className="AuthForm" data-testid="AuthForm">
            {authFailed && <Toast kind={TOASTER_KIND.negative}>Negative notification</Toast>}
            <Card
                animate
                autoFocus
                size={SIZE.default}
                role={ROLE.default}>
                <ModalHeader>{isRegister ? 'Register' : 'Login'}</ModalHeader>
                <ModalBody>
                    <FormControl>
                        <Input
                            type={'email'}
                            value={email}
                            onChange={e => setEmail(e.target.value)}
                            placeholder="Email"
                            size={SIZE.compact}
                        />
                    </FormControl>

                    <FormControl>
                        <Input
                            type={'password'}
                            value={password}
                            onChange={e => setPassword(e.target.value)}
                            placeholder="Password"
                            size={SIZE.compact}
                        />
                    </FormControl>

                    {isRegister &&
                    <>
                        <FormControl>
                            <Input
                                value={firstName}
                                onChange={e => setFirstName(e.target.value)}
                                placeholder="First Name"
                                size={SIZE.compact}
                            />
                        </FormControl>
                        <FormControl>
                            <Input
                                value={lastName}
                                onChange={e => setLastName(e.target.value)}
                                placeholder="Last Name"
                                size={SIZE.compact}
                            />
                        </FormControl>
                    </>
                    }
                    <StyledLink href={isRegister ? '/login' : '/register'}>
                        {isRegister ? "Already have an account?" : "Don't have an account? Register here"}
                    </StyledLink>
                </ModalBody>

                <ModalFooter>
                    <ModalButton
                        kind={KIND.primary}
                        disabled={!formValid}
                        onClick={() => {
                            buttonClicked.current = true;
                            if (isRegister) {
                                dispatch(authenticationService.register(
                                    firstName,
                                    lastName,
                                    email,
                                    password
                                ));
                            } else {
                                dispatch(authenticationService.login(
                                    email,
                                    password
                                ));
                            }
                        }}>
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
