import React from 'react';
import axios from 'axios';
import { Link, useHistory } from 'react-router-dom';

function Header()
{

    const toggleDisplay = () => {
        if (localStorage.getItem('loggedInUser'))
        {
            return (
                <div className='collapse navbar-collapse' id='navbarCollapse'>

                </div>
            )
        }
        else
        {
            return (
                <div className='collapse navbar-collapse' id='navbarCollapse'>

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