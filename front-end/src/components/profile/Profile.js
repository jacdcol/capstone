import React, { useState, useEffect } from 'react';
import { Link, Route, useHistory } from 'react-router-dom';
import axios from 'axios';

const Profile = () => {
    const [user, setUser] = useState(0)
    const history = useHistory();

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
    {/*const signInWithSpotify = () =>
    {
        axios.post('http://localhost:8080/spotify-auth', user).then((response) => {
            console.log(response)
            localStorage.setItem('spotifyConnected', true);
        })
        .catch((error) => {
            console.log('Authentication Request Failed' + error)
        })
        signInWithSpotifyAsync()
    }*/}
    
    const signInWithAppleMusic = () =>
    {

    }

    if ((!localStorage.getItem('spotifyConnected')) && (!localStorage.getItem('appleMusicConnected')))
    {
        
    }
    else
    {
        return (
            <div className='Profile'>
                <div>
                    <h1>Profile</h1>
                </div>
                <div>
                    
                </div>
            </div>
        )
    }
}
export default Profile;