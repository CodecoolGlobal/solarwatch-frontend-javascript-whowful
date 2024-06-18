import React, { useState } from 'react';
import './RegistrationPage.css';
import { useNavigate } from 'react-router-dom';

const logInUser = async (body) => {
  try {
    const res = await fetch('/api/user/signin', {
      method: "POST",
      headers: {
        "Content-type": "application/json"
      },
      body: JSON.stringify(body)

    })
    return await res.json()
  } catch (error) {
    console.error("Failed to log in: ", error)
  }
}

const LogInPage = () => {
  const [logInData, setLogInData] = useState({
    username: '',
    password: ''
  });
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    console.log(e.target.value)
    setLogInData({
      ...logInData,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const userData = await logInUser(logInData)
    localStorage.setItem("token", userData.jwt)
    navigate('/solarwatch')
    console.log('User logged in:', logInData);
  };

  return (
    <div className="form-container">
      <h2>Log In</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            name="username"
            value={logInData.username}
            onChange={handleChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            name="password"
            value={logInData.password}
            onChange={handleChange}
          />
        </div>
        <button type="submit">Log In</button>
      </form>
    </div>
  );
};

export default LogInPage;
