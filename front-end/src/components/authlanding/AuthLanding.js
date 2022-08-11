import React, { useEffect, useState } from 'react';
import axios from 'axios';

const AuthLanding = () =>
{
    const signInWithSpotify = () =>
    {
        const params = {
            username: localStorage.getItem('loggedInUser')
        }
        console.log({ params })
        axios.get('http://localhost:8080/spotify-api/auth-code-uri', { params })
        .then(response => {
            window.location.replace(response.data);
        })
    }
    return (
        <div className='auth-landing'>
            <button onClick={signInWithSpotify}>Authorize Spotify Account</button>
        </div>
    )
}
export default AuthLanding;