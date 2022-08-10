import React, { useState, useEffect} from 'react';
import axios from 'axios';
import Tab from 'react-bootstrap/Tab';
import Tabs from 'react-bootstrap/Tabs';
import UserTopTracks from '../usertop/UserTopTracks';
import UserTopArtists from '../usertop/UserTopArtists';

const Dashboard = () =>
{
    const [user, setUser] = useState('')
    const [key, setKey] = useState('tracks')

    useEffect(() => {
        const params = {
            username: localStorage.getItem('loggedInUser')
        }
        setUser( params['username'] )
        {/*axios.get('http://localhost:8080/findByUsername', { params })
        .then((response) => {
            setUser(response.data)
            localStorage.setItem('spotifyConnected', true)
            console.log(user.username)
            console.log(response.data.username)
        }).catch(error => {
            console.log('error' + error)
        });
        console.log(localStorage.getItem('loggedInUser'))
    console.log(params)*/}
        
    }, []
    );
    console.log(user)
    return(
        <Tabs
            id='dashboard-tabs'
            activeKey={key}
            onSelect={(k) => setKey(k)}
            className='mb-3'
        >
            <Tab eventKey='tracks' title='Tracks'>
                { UserTopTracks() }
            </Tab>
            <Tab eventKey='artists' title='Artists'>
                { UserTopArtists() }
            </Tab>
        </Tabs>
    )
}
export default Dashboard;