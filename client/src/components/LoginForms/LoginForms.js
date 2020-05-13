import React from 'react';
import './LoginForms.css';
import LoginRegister from "../LoginRegister/LoginRegister";

const LoginForms = () => {

    const [isLoginRegisterOpen, setLoginRegisterOpen] = React.useState(false);

    return(
        <div className="LoginForms" data-testid="LoginForms">
            <h1>Demo for Login/Register Forms</h1>
            <LoginRegister isOpen={isLoginRegisterOpen} setIsOpen={setLoginRegisterOpen}/>
        </div>
    );
};

LoginForms.propTypes = {};
LoginForms.defaultForms = {};

export default LoginForms;