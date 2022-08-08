import React, { useState, useEffect} from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';

const Dashboard = () =>
{
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
    {/*const [accessToken, setAccessToken] = useState('')
    useEffect(() => {
        const hash = window.location.hash;
        let token = window.localStorage.getItem('token')
        if (!token && hash)
        {
            token = hash.substring(1).split('&').find(elem => elem.startsWith('access_token')).split('=')[1]
            window.location.hash = ''
            window.localStorage.setItem('token', token)
            console.log(token)
        }
        setAccessToken(token);
        await axios.get('http://localhost:8080/spotify-api/callback', )
        .then(response => {
            setAccessToken(response.data);
            console.log(response.data);
        })
    })*/}

    return(
        <div>
            <h1>Dashboard</h1>
        </div>
    )
}
export default Dashboard;