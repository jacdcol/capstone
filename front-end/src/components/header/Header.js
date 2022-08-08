import React from 'react';
import { Link, useHistory } from 'react-router-dom';

function Header()
{
    const history = useHistory();
    const signOutHandler = () => {
        console.log('sign out clicked');
        localStorage.clear();
        history.push('/');
    }
    const toggleDisplay = () => {
        if (localStorage.getItem('loggedInUser'))
        {
            return (
                <form className="d-flex">
                    <button className="btn btn-outline-success" onClick={signOutHandler} type="button">Sign Out</button>
                </form>
            )
        }
        else
        {
            return (
                <div>
                    <Link className='navbar-brand' to='/sign-in'>Sign In</Link>
                </div>
            )
        }
    }
    return(
        <header>
            <div className='mb-5'>
                <nav className='navbar navbar-expand-md navbar-dark fixed-top bg-dark'>
                    <div className='container-fluid'>
                        <Link className='navbar-brand' to='/'>Capstone</Link>
                        <button className='navbar-toggler' type='button' data-bs-toggle='collapse' data-bs-target='#navbarCollapse' aria-controls='navbarCollapse' aria-expanded='false' aria-label='Toggle navigation'>
                            <span className='navbar-toggler-icon'></span>
                        </button>
                        {toggleDisplay()}
                    </div>
                </nav>
            </div>
        </header>
    );
}
export default Header;