import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Table from 'react-bootstrap/Table';

const UserTopArtists = () =>
{
    const [artists, setArtists] = useState()
    useEffect(() => {
        artistReq()
    }, []
    );
    async function artistReq()
    {
        const params = {
            username: localStorage.getItem('loggedInUser')
        }
        try
        {
            await axios.get('http://localhost:8080/spotify-api/get-top-artists', { params })
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
    {/*put cards on this page! with pictures and genres*/}
    return (
        <Table borderless>
            <thead>
                <tr>
                    <th>Name</th>
                </tr>
            </thead>
            <tbody>
                {artists ? (
                    artists.map(artist => {
                        return (
                            <tr key={ artist.id }>
                                <td>{ artist.name }</td>
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
export default UserTopArtists;