import React from 'react';

function SignIn() {
    const signInWithSpotify = () => {
        const provider = new firebase.auth.
    }
    return (
    <div className='SignIn'>
        <button onClick={signInWithSpotify}>Sign in with Spotify</button>
        <button onClick={signInWithAppleMusic}>Sign in with Apple Music</button>
    </div>
    );
}
export default SignIn;