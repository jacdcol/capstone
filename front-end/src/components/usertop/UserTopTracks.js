import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Card from 'react-bootstrap/Card';
import CardGroup from 'react-bootstrap/CardGroup';

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
        <div>
            <CardGroup>
                <Card><Card.Body><Card.Text><b>Track</b></Card.Text></Card.Body></Card>
                <Card><Card.Body><Card.Text><b>Artist(s)</b></Card.Text></Card.Body></Card>
                <Card><Card.Body><Card.Text><b>Album</b></Card.Text></Card.Body></Card>
            </CardGroup>
            {props ? (
                props.map(track => {
                    return (
                        <CardGroup key={ track.id }>
                                <Card>
                                    <Card.Body><Card.Text>{ track.name }</Card.Text></Card.Body>
                                </Card>
                                <Card>
                                    <Card.Body><Card.Text>{ mapArtists(track.artists) }</Card.Text></Card.Body>
                                </Card>
                                <Card>
                                    <Card.Body><Card.Text>{ track.album.name }</Card.Text></Card.Body>
                                </Card>
                        </CardGroup>
                    )
                })
            ):
            (
                <Card><Card.Body><Card.Text>loading top tracks...</Card.Text></Card.Body></Card>
            )}
        </div>
    )
}
export default UserTopTracks;