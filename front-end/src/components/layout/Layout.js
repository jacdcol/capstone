import React from "react";
import Profile from "../Profile/Profile";
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
                    <Route path='/sign-up' component={SignUp}/>
                    <Route path='/spotify' component={Spotify}/>
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