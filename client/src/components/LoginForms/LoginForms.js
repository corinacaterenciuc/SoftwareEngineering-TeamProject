import React from 'react';
//import './ModalDemo.css';
import {Button} from "baseui/button";


import LoginFormDemo from "../LoginFormDemo/LoginFormDemo";
import RegisterFormDemo from "../RegisterFormDemo/RegisterFormDemo";


const LoginForms = () => {

    const [isLoginOpen, setLoginOpen] = React.useState(false);
    const [isRegisterOpen, setRegisterOpen] = React.useState(false);


    return(
        <div className="LoginForms" data-testid="LoginForms">
            <h1>Demo for Login/Register Forms</h1>
            <LoginFormDemo isOpen={isLoginOpen} setIsOpen={setLoginOpen}/>
            <Button onClick={ () => setLoginOpen(true)}>Login</Button>
            <RegisterFormDemo isOpen={isRegisterOpen} setIsOpen={setRegisterOpen}/>
            <Button onClick={ () => setRegisterOpen(true)}>Register into your account</Button>
        </div>
    );
};

LoginForms.propTypes = {};
LoginForms.defaultForms = {};

export default LoginForms;