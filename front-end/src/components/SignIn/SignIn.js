import React, { useState } from 'react';
//import firebase from 'firebase/app';
//import 'firebase/firestore';
//import 'firebase/auth';
//import { useAuthState } from 'react-firebase-hooks/auth';
//import { useCollectionData } from 'react-firebase-hooks/firestore';
import axios from 'axios';
import { useHistory } from 'react-router-dom';


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

/*const client_id = '198355d84c154d1da590aa0a7f716d5b';
const response_type = 'code';
const redirect_uri = {SignIn};
const code_challenge_method = 'S256';
const code_challenge = '';
var scopes = 'user-read-private user-read-email';*/

function SignIn()
{
  const [signInUser, setSignInUser] = useState({ username:'', password:''})

  const history = useHistory();

  const changeHandler = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    const tempSignIn = {...signInUser};
    tempSignIn[name] = value;
    setSignInUser(tempSignIn);
  }

  const handleUserSignIn = () => {
    axios.post('http://localhost:8080/login', signInUser).then(response => {
      localStorage.setItem('loggedInUser', response.data.username);
      history.push('/profile');
    }).catch((error) => {
      console.log('invalid' + error);
    })
  }
  return (
    <div>
      <div>
        <form>
          <input className='mb-3 form-control' value={signInUser.username} onChange={changeHandler} name='username' type="username" placeholder="Enter Username" aria-label='Username' />
          <input className='mb-3 form-control' value={signInUser.password} onChange={changeHandler} name='password' type="password" placeholder="Enter Password" aria-label='Password' />
          <button className="btn btn-outline-success" onClick={handleUserSignIn} type="button">Sign in</button>
        </form>
      </div>
      {/*<Form>
          <Form.Group className="mb-3" controlId="formBasicUsername">
            <Form.Label>Email Address or Username</Form.Label>
            <Form.Control value={signInUser.username} onChange={changeHandler} type="username" placeholder="Enter Email or Username" />
            {/*<Form.Text className="text-muted">
              We'll never share your email with anyone else.
            </Form.Text>
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicPassword">
            <Form.Label>Password</Form.Label>
            <Form.Control value={signInUser.password} onChange={changeHandler} type="password" placeholder="Password" />
          </Form.Group>
          <Form.Group className="mb-3" controlId="formBasicCheckbox">
            <Form.Check type="checkbox" label="Check me out" />
          </Form.Group>
          <Button variant="primary" onClick={handleUserSignIn} type="button">
            SignIn
          </Button>
        </Form>*/}
    </div>
  )
}
export default SignIn;