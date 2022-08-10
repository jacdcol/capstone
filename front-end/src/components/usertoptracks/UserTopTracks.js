import React from 'react';
import Table from 'react-bootstrap/Table';

const UserTopTracks = (props) =>
{
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
                    <th>Track Title</th>
                    <th>Artist Name</th>
                    <th>Album Name</th>
                </tr>
            </thead>
            <tbody>
                <div>
                    {props ? (
                        props.map(track => {
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
                        <h1>loading</h1>
                    )}
                </div>
            </tbody>
        </Table>
    )
}
export default UserTopTracks;