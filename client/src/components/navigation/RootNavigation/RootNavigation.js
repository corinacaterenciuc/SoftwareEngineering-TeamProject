import React from 'react';
import './RootNavigation.css';
import AuthForm from "../../auth/AuthForm/AuthForm";
import Dashboard from "../../generic/Dashboard/Dashboard";
import {Redirect, Route, Switch} from "react-router-dom";
import Logout from "../../auth/Logout/Logout";
import NotFound from "../NotFound/NotFound";
import {useDispatch} from "react-redux";

const RootNavigation = () => {
    const dispatch = useDispatch();

    return (<div className="RootNavigation">
        <Switch>
            <Route path="/login" component={AuthForm} componentProps={{isRegister: false}}/>

            <Route path="/register" component={AuthForm} componentProps={{isRegister: true}}/>

            <Route path={'/dashboard'} component={Dashboard}/>

            <Route path={'/logout'} component={Logout}/>

            <Route exact path={""} render={() => <Redirect to={'/dashboard'}/>}/>

            <Route path={'*'} render={() => <NotFound/>}/>
        </Switch>
    </div>)
};

RootNavigation.propTypes = {};

RootNavigation.defaultProps = {};

export default RootNavigation;
