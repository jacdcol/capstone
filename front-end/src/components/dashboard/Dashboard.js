import React, { useState, useEffect} from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';
import UserTopFiftyTracks from '../usertoptracks/UserTopFiftyTracks';

const Dashboard = () =>
{
    const [user, setUser] = useState(0)
    const [userTopTracks, setUserTopTracks] = useState()
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
            setUserTopTracks(response.data)
            localStorage.setItem("user-top-tracks", userTopTracks)
        })
    }, []
    );

    return(
        <div>
            <div>
                <h1>Dashboard</h1>
            </div>
            <div>
                {userTopTracks ? (
                    userTopTracks.map(track => {
                        return (
                            <div key={ track.id }>
                                { track.name }
                                { track.artists.name }
                            </div>
                        )
                    })
                ):
                (
                    <h1>loading</h1>
                )}
            </div>
        </div>
    )
}
export default Dashboard;