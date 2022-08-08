import React, { useState } from 'react';
import axios from 'axios';
import { useHistory } from 'react-router-dom';

function SignUp()
{
    const history = useHistory();

    const [user, setUser] = useState({
        username: '',
        name: '',
        password:'',
        userSpotify:{},
        userAppleMusic:{},
    })

    const userChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        const tempUser = {...user};
        tempUser[name] = value;
        setUser(tempUser);
    }

    const userSignUp = () => {
        axios.post('http://192.168.0.184:8080/save', user).then((response) => {
            localStorage.setItem('loggedInUser', response.data.tempUser)
            history.push('/profile');
        }).catch((error) => {
            console.log('User does not exist');
        })
    }
    
    return (
        <div>
            <br></br>
            <form className='row g-3'>
                <h2>Sign Up</h2>
                <div className='col-md-6'>
                    <label htmlFor='inputUsername' className='form-label'>Username</label>
                    <input name='username' value={user.username} onChange={userChange} type='text' className='form-control' id='inputUsername' />
                </div>
                <div className='col-md-6'>
                    <label htmlFor='inputName' className='form-label'>Name</label>
                    <input name='name' value={user.name} onChange={userChange} type='text' className='form-control' id='inputName' />
                </div>
                <div className='col-md-6'>
                    <label htmlFor='inputPassword' className='form-label'>Password</label>
                    <input name='password' value={user.password} onChange={userChange} type='password' className='form-control' id='inputPassword' />
                </div>
                {/*<div className='col-md-6'>
                    <label htmlFor='inputFirstName' className='form-label'>Repeat Password</label>
                    <input name='firstName' value={user.firstName} onChange={userChange} type='text' className='form-control' id='inputFirstName' />
                </div>*/}
                <div className="d-grid gap-2 ">
                    <button onClick={userSignUp} className="bg-dark btn btn-outline-success" type="button">Sign up</button>
                </div>
            </form>
        </div>
    )
}
export default SignUp;