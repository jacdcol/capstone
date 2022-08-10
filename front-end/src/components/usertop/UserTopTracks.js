import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Table from 'react-bootstrap/Table';

const UserTopTracks = () =>
{
    const [tracks, setTracks] = useState()
    useEffect(() => {
        trackReq()
    }, []
    );
    async function trackReq()
    {
        const params = {
            username: localStorage.getItem('loggedInUser')
        }
        try
        {
            await axios.get('http://localhost:8080/spotify-api/get-top-tracks', { params })
            .then((response) => {
                console.log(response.data)
                setTracks(response.data)
            })
        }
        catch(err)
        {
            console.log(err)
        }
    }
    const mapArtists = (props) => {
        return (
            props.map(artist => <span>{ artist.name }</span>)
            .reduce((accu, elem) => {
                return accu === null ? [elem] : [...accu, ', ', elem]
            }, null)
        )
    }
    return (
        <Table borderless>
            <thead>
                <tr>
                    <th>Track</th>
                    <th>Artist(s)</th>
                    <th>Album</th>
                </tr>
            </thead>
            <tbody>
                {tracks ? (
                    tracks.map(track => {
                        return (
                            <tr key={ track.id }>
                                <td>{ track.name }</td>
                                <td>{ mapArtists(track.artists) }</td>
                                <td>{ track.album.name }</td>
                            </tr>
                        )
                    })
                ):
                (
                    <tr><td>'loading'</td></tr>
                )}
            </tbody>
        </Table>
    )
}
export default UserTopTracks;