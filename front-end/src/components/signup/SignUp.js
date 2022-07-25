import React from 'react';
import axios from 'axios';

function SignUp()
{
    const [user, setUser] = useState({
        email:'',
        password:'',
        userSpotify:'',
        userAppleMusic:'',
    })

    const userChange = (event) => {
        const name = event.target.name;
        const value = event.target.value;
        const tempUser = {...user};
        tempUser[name] = value;
        setUser(tempUser);
    }

    const userSignUp = () => {
        axios.post('http://localhost:8080/save', user).then((response) => {
            localStorage.setItem('loggedInUser', response.data.tempUser)
            history.push('/profile');
        }).catch((error) => {
            console.log('User does not exist');
        })
    }
    return (
        <div>
            {userSignUp}
        </div>
    )
}
export default SignUp;