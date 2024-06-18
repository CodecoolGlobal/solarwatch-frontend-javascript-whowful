import React, { useState } from 'react';
import'./RegistrationPage.css';

const registerUser = async (body) => {
  try {
    const res = await fetch('/api/user/register', {
      method: "POST",
      headers: {
        "Content-type": "application/json"
      },
      body: JSON.stringify(body)
    })
    console.log(res.json())
  } catch (error) {
    console.error("Failed to register user: ", error)
  }
}

const RegistrationPage = () => {
  const [registrationData, setRegistrationData] = useState({
    username: '',
    password: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    console.log(e.target.value)
    setRegistrationData({
      ...registrationData,
      [name]: value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    registerUser(registrationData)
    console.log('Form submitted:', registrationData);
  };

  return (
    <div className="form-container">
      <h2>Registration Form</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            name="username"
            value={registrationData.username}
            onChange={handleChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            id="password"
            name="password"
            value={registrationData.password}
            onChange={handleChange}
          />
        </div>
        <button type="submit">Register</button>
      </form>
    </div>
  );
};

export default RegistrationPage;
