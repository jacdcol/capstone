import React, { useState, useEffect } from 'react'
import axios from 'axios';

function Profile() {
    const [user, setUser] = useState(0)
    useEffect(() => {
        const params = {
            email: localStorage.getItem('loggedInUser')
        }
        axios.get('http://localhost:8080/findByEmail', { params }).then((response) => {
            setUser(response.data);
        }).catch(error => {
            console.log('error' + error);
        });
    }, [])
    const signInWithSpotify = () =>
    {
        //axios.get('https://accounts.spotify.com/authorize' + '/' + client_id + '/' + scopes + '/' + redirect_uri);
        axios.post('http://localhost:8080/spotify-auth', user).then((response) => {

        })
    }
    const signInWithAppleMusic = () =>
    {

    }
    return (
    <div className='Profile'>
        <button onClick={signInWithSpotify}>Sign in with Spotify</button>
        <button onClick={signInWithAppleMusic}>Sign in with Apple Music</button>
    </div>
    );
}
export default Profile;