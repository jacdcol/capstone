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
    const [artists, setArtists] = useState()
    const [tracks, setTracks] = useState()

    useEffect(() => {
        const params = {
            username: localStorage.getItem('loggedInUser')
        }
        if (key === 'artists')
        {
            artistReq({ params })
        }
        else
        {
            trackReq({ params })
        }
    }, [key]
    );
    async function artistReq(props)
    {
        console.log(props)
        try
        {
            await axios.get('http://localhost:8080/spotify-api/get-top-artists', props)
            .then((response) => {
                console.log(response.data)
                setArtists(response.data)
            })
        }
        catch(err)
        {
            console.log(err)
        }
    }
    async function trackReq(props)
    {
        console.log(props)
        try
        {
            await axios.get('http://localhost:8080/spotify-api/get-top-tracks', props)
            .then((response) => {
                console.log(response.data)
                setTracks(response.data)
            })
        }
        catch(err)
        {
            console.log(err)
        }
        console.log(tracks)
    }
    return(
        <div>
            <Tabs
            id='dashboard-tabs'
            activeKey={key}
            onSelect={(k) => setKey(k)}
            className='mb-3'
        >
            <Tab eventKey='tracks' onClick={ trackReq } title='Tracks'>
                { UserTopTracks(tracks) }
            </Tab>
            <Tab eventKey='artists' onClick={ artistReq } title='Artists'>
                { UserTopArtists(artists) }
            </Tab>
        </Tabs>
        </div>
        
    )
}
export default Dashboard;