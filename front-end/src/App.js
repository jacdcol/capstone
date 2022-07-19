import React from 'react';
import './App.css';
import firebase from 'firebase/app';
import 'firebase/firestore';
import 'firebase/auth';
import { useAuthState } from 'react-firebase-hooks/auth';
import { useCollectionData } from 'react-firebase-hooks/firestore';


firebase.initializeApp({
  apiKey: "AIzaSyAAU56vNwdYJxOJD1nswmv3sFneePYWh60",
  authDomain: "capstone-e9137.firebaseapp.com",
  projectId: "capstone-e9137",
  storageBucket: "capstone-e9137.appspot.com",
  messagingSenderId: "459664929347",
  appId: "1:459664929347:web:88eb86176dd62d06f45af6"
})

const auth = firebase.auth();
const firestore = firebase.firestore();

function App(props) {
  const [user] = useAuthState(auth);


  return (
    <div className="App">
      <header className="App-header">
        
      </header>
      <section>
        {user ? <Profile /> : <SignIn />}
      </section>
    </div>
  );
}

export default App;
