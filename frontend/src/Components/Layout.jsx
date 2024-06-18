import './Layout.css'
import React from 'react'



const Layout = ({ children }) => {
  const token = localStorage.getItem("token")

  return (
    token === undefined || token === null ? (
      <div>
        <nav className="navbar navbar-expand-lg" style={{ "backgroundColor": "#464242" }}>
          <div className="container-fluid">
            <div className="collapse navbar-collapse" id="navbarNav">
              <ul className="navbar-nav mr-auto">
                <li className="main">
                  <a className="nav-link" href='/'>Main</a>
                </li>
              </ul>
              <ul className="navbar-nav ms-auto">
                <li className="main">
                  <a className="nav-link" href='/'>Log Out</a>
                </li>
              </ul>
            </div>
          </div>
        </nav>
        <div>
          {children}
        </div>
      </div>
    ) : (
      <div>
        <nav className="navbar navbar-expand-lg" style={{ "backgroundColor": "#464242" }}>
          <div className="container-fluid">
            <div className="collapse navbar-collapse" id="navbarNav">
              <ul className="navbar-nav mr-auto">
                <li className="main">
                  <a className="nav-link" href='/'>Main</a>
                </li>
              </ul>
              <ul className="navbar-nav ms-auto">
                <li className="nav-item">
                  <a className="nav-link" href='/login'>Log In</a>
                </li>
                <li className="nav-item">
                  <a className="nav-link" href='/register'>Register</a>
                </li>
              </ul>
            </div>
          </div>
        </nav>
        <div>
          {children}
        </div>
      </div>

    ))
}

export default Layout