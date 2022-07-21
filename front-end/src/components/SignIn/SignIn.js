import React from 'react';
//import firebase from 'firebase/app';
//import 'firebase/firestore';
//import 'firebase/auth';
//import { useAuthState } from 'react-firebase-hooks/auth';
//import { useCollectionData } from 'react-firebase-hooks/firestore';
import axios from 'axios';


/*firebase.initializeApp({
  apiKey: "AIzaSyAAU56vNwdYJxOJD1nswmv3sFneePYWh60",
  authDomain: "capstone-e9137.firebaseapp.com",
  projectId: "capstone-e9137",
  storageBucket: "capstone-e9137.appspot.com",
  messagingSenderId: "459664929347",
  appId: "1:459664929347:web:88eb86176dd62d06f45af6"
})

const auth = firebase.auth();
const firestore = firebase.firestore();*/

const client_id = '198355d84c154d1da590aa0a7f716d5b';
const response_type = 'code';
const redirect_uri = {SignIn};
const code_challenge_method = 'S256';
const code_challenge = '';
var scopes = 'user-read-private user-read-email';

function SignIn() {
    const signInWithSpotify = () =>
    {
        axios.get('https://accounts.spotify.com/authorize' + '/' + client_id + '/' + scopes + '/' + redirect_uri);
    }
    const signInWithAppleMusic = () =>
    {

    }
    return (
    <div className='SignIn'>
        <button onClick={signInWithSpotify}>Sign in with Spotify</button>
        <button onClick={signInWithAppleMusic}>Sign in with Apple Music</button>
    </div>
    );
}
export default SignIn;