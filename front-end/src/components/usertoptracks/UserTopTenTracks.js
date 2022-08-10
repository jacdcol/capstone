import React from 'react';
import Table from 'react-bootstrap/Table';

const UserTopTenTracks = (props) =>
{
    return (
        <Table borderless size="lg">
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
                                    <td>{ track.artists[0].name }</td>
                                    <td>{ track.album.name }</td>
                                </tr>
                            )
                        })
                    ):
                    (
                        <h1>loading</h1>
                    )}
                </div>
                    {/*tracks.map(item => {
                        return (
                            <tr key={ item.name } >
                                <td>{ item.name }</td>
                                <td>{ item.artists[0].name }</td>
                                <td>{ item.album.name }</td>
                            </tr>
                        )
                    })*/}
                </tbody>
            </Table>
    )
}
export default UserTopTenTracks;