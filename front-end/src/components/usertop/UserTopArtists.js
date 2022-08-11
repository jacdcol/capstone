import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Card from 'react-bootstrap/Card';
import CardGroup from 'react-bootstrap/CardGroup';

const UserTopArtists = (props) =>
{
    {/*put cards on this page! with pictures and genres*/}
    return (
        <div>
            {props ? (
                props.map(artist => {
                    return (
                        <CardGroup key={ artist.id }>
                            <Card className='artist-card'>
                                <Card.Img className='primary-img' src={ artist.images[0].url }/>
                            </Card>
                            <Card>
                                    <Card.Body>
                                        <Card.Text>{ artist.name }</Card.Text>
                                    </Card.Body>
                                </Card>
                                <Card>
                                    <Card.Body>
                                        <Card.Text>{ artist.genres[0] }</Card.Text>
                                        <Card.Text>{ artist.genres[1] }</Card.Text>
                                        <Card.Text>{ artist.genres[2] }</Card.Text>
                                        <Card.Text>{ artist.genres[3] }</Card.Text>
                                    </Card.Body>
                                </Card>
                        </CardGroup>
                    )
                })
            ):
            (
                <Card><Card.Body><Card.Text>loading top artists...</Card.Text></Card.Body></Card>
            )}
        </div>
    )
}
export default UserTopArtists;
{/*<Table borderless>
            <thead>
                <tr>
                    <th>Name</th>
                </tr>
            </thead>
            <tbody>
                
            </tbody>
                </Table>*/}