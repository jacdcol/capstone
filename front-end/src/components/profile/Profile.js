import React, { useState, useEffect } from 'react';
import { Link, Route } from 'react-router-dom';
import axios from 'axios';

const Profile = () => {
    const [user, setUser] = useState(0)

    useEffect(() => {
        const params = {
            username: localStorage.getItem('loggedInUser')
        }
        axios.get('http://localhost:8080/findByUsername', { params }).then((response) => {
            setUser(response.data);
            console.log("user found");
        }).catch(error => {
            console.log('error' + error);
        });
    }, []
    );
    const signInWithSpotify = () =>
    {
        //axios.get('https://accounts.spotify.com/authorize' + '/' + client_id + '/' + scopes + '/' + redirect_uri);
        axios.post('http://localhost:8080/spotify-auth', user).then((response) => {
            localStorage.setItem('spotifyConnected', true);
        }).catch((error) => {
            console.log('Authentication Request Failed' + error)
        })
    }
    const signInWithAppleMusic = () =>
    {

    }

    return (
        <div className='Profile'>
            <div>
                <h1>Profile</h1>
            </div>
            <div>
                <button onClick={signInWithSpotify}>Sign in with Spotify</button>
                <button onClick={signInWithAppleMusic}>Sign in with Apple Music</button>
            </div>
        </div>
    )

    {/*if ((!localStorage.getItem('spotifyConnected')) && (!localStorage.getItem('appleMusicConnected')))
    {
        return (
            <div className='Profile'>
                <button onClick={signInWithSpotify}>Sign in with Spotify</button>
                <button onClick={signInWithAppleMusic}>Sign in with Apple Music</button>
            </div>
        );
    }
    else
    {

    }*/}
}
export default Profile;