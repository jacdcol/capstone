import React, { useState } from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import ToggleButton from 'react-bootstrap/ToggleButton'

function SignIn()
{
  const [signInUser, setSignInUser] = useState({ username:'', password:''})

  const history = useHistory();

  const changeHandler = (event) => {
    console.log('change handler');
    const name = event.target.name;
    const value = event.target.value;
    const tempSignIn = {...signInUser};
    tempSignIn[name] = value;
    setSignInUser(tempSignIn);
  }

  const handleUserSignIn = () => {
    console.log('post');
    axios.post('http://localhost:8080/login', signInUser).then(response => {
      localStorage.setItem('loggedInUser', response.data.username);
      history.push('/');
    }).catch((error) => {
      console.log('invalid' + error);
    })
  }
  return (
    <div>
      <div className='register'>
        <Form className='register-form'>
          <h3>Sign In</h3>
          <Form.Group className='mb-3' controlId='formBasicUsername'>
            <Form.Label>Username</Form.Label>
            <Form.Control size='lg' value={signInUser.username} onChange={changeHandler} name='username' type='username' placeholder='Enter Username' aria-describedby='Username' />
          </Form.Group>
          <Form.Group className='mb-3' controlId='formBasicPassword'>
            <Form.Label>Password</Form.Label>
            <Form.Control size='lg' value={signInUser.password} onChange={changeHandler} name='password' type='password' placeholder='Enter Password' aria-describedby='Password' />
          </Form.Group>
          <button className="btn btn-outline-success" size='large' onClick={handleUserSignIn} type="button">Sign in and Authorize Spotify</button>
        </Form>
      </div>
    </div>
  )
}
export default SignIn;