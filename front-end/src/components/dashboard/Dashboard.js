import React, { useState, useEffect} from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';
import UserTopTracks from '../usertoptracks/UserTopTracks';

const Dashboard = () =>
{
    const [user, setUser] = useState(0)
    const [tracks, setTracks] = useState()
    const history = useHistory();

    useEffect(() => {
        const params = {
            username: localStorage.getItem('loggedInUser')
        }
        axios.get('http://localhost:8080/findByUsername', { params })
        .then((response) => {
            setUser(response.data);
            console.log("user found");
        }).catch(error => {
            console.log('error' + error);
        });
        axios.get('http://localhost:8080/spotify-api/get-top-tracks', { params })
        .then((response) => {
            console.log(response.data)
            setTracks(response.data)
            localStorage.setItem("user-top-tracks", tracks)
        })
    }, []
    );

    return(
        <div>
            <div>
                <h1>Dashboard</h1>
            </div>
            <div>
                { UserTopTracks(tracks) }
            </div>
        </div>
    )
}
export default Dashboard;