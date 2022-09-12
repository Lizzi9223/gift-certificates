import React, { useState, useEffect } from "react";
import { useCookies } from "react-cookie";
import axios from "axios";

import './css/LoginForm.css';

function LoginForm(props){
    const[username, setUsername] = useState('');
    const[password, setPassword] = useState('');
    const[errorMessages, setErrorMessages] = useState({});
    const[usernameValid, setUsernameValid] = useState(false);
    const[passwordValid, setPasswordValid] = useState(false);
    const[formValid, setFormValid] = useState(false);
    const [cookies, setCookie, removeCookie] = useCookies(["token","login","page"]);

    useEffect(() => {
        validateForm();
    }, [usernameValid, passwordValid]);

    useEffect(() => {
        validateForm();
    }, [usernameValid, passwordValid]);


  const handleUserInput = (e) => {
    const name = e.target.name;
    const value = e.target.value;
    let usernameValid_ = usernameValid;
    let passwordValid_ = passwordValid;
    switch(name) {
        case 'username':
            setUsername(value);
            usernameValid_ = value.length >= 3 && value.length <= 30;
            break;
        case 'password':
            setPassword(value);
            passwordValid_ = value.length >= 4 && value.length <= 30;
            break;
        default:
          break;
    }
    
    setUsernameValid(usernameValid_);
    setPasswordValid(passwordValid_);
  }

  function validateForm() {
    setFormValid(usernameValid && passwordValid);
  }

  const handleSubmit = (event) => {
    event.preventDefault();

    const requestOptions = {
      login: username,
      password: password
    }

    axios.post('auth', requestOptions)
      .then(response => {
        setCookie("token", response.data.token, { path: '/' });
        setCookie("login", requestOptions.login, { path: '/' });
        setCookie("page", 0, { path: '/' });
        props.navigate('/certificates');        
      })
      .catch((error) => {
        setErrorMessages({errorMessages : { name: "credentials", message: error.response.data.errorMessage }});
      })
  }

  const renderErrorMessage = (name) => {
    return(
      name === errorMessages.name && (
        <div className="error">{errorMessages.message}</div>)
    );
  }
  
  return(      
    <div className="login-form">
      <div className="title">Login</div>
        <div className="form">
            <form onSubmit={handleSubmit}>
                <div className="input-container">
                <input type="text" name="username" value={username} onChange={handleUserInput}
                        required placeholder="Username" />
                </div>
                <div className="input-container">
                <input type="password" name="password" value={password} onChange={handleUserInput}
                        required placeholder="Password" />
                {renderErrorMessage("credentials")}
                </div>
                <div className="button-container">
                <input type="submit" value="Login" disabled={formValid ? false : true}/>
                </div>
            </form>
        </div>
    </div> 
  )  
}

export default LoginForm;
