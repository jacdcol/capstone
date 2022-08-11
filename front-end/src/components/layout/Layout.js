import React from "react";
import Profile from "../profile/Profile";
import Header from '../header/Header';
import Dashboard from '../dashboard/Dashboard';
import SignUp from "../signup/SignUp";
import SignIn from "../SignIn/SignIn";
import AuthLanding from "../authlanding/AuthLanding";
import { Route, withRouter } from 'react-router-dom';

const Layout = () =>
{
    {/*window.addEventListener("beforeunload", (ev) => {
        localStorage.clear()
      })*/}
    const toggleRoutes = () =>
    {
        if(localStorage.getItem('loggedInUser'))
        {
            return (
                <div>
                    <Route exact path='/' component={AuthLanding}/>
                    <Route path='/dashboard' component={Dashboard}/>
                </div>
            )
        }
        else
        {
            return (
                <div>
                    <Route exact path='/' component={SignUp}/>
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