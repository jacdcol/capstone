import React from "react";
import Profile from "../profile/Profile";
import Header from '../header/Header';
import Home from '../home/Home';
import SignUp from "../signup/SignUp";
import SignIn from "../SignIn/SignIn";

import { Route, withRouter } from 'react-router-dom';

const Layout = () =>
{
    const toggleRoutes = () =>
    {
        if(localStorage.getItem('loggedInUser'))
        {
            return (
                <div>
                    <Route exact path='/' component={Home}/>
                    <Route path='/profile' component={Profile}/>
                </div>
            )
        }
        else
        {
            return (
                <div>
                    <Route exact path='/' component={Home}/>
                    <Route path='/profile' component={Profile}/>
                    <Route path='/sign-up' component={SignUp}/>
                    <Route path='/sign-in' component={SignIn}/>
                </div>
            )
        }
    }
    return (
        <div>
            <Header />
            {toggleRoutes()}
        </div>
    )
}
export default withRouter(Layout);