import { Route, Routes } from 'react-router-dom';
import React from 'react';
import MainPage from './Pages/MainPage'
import LogInPage from './Pages/LogInPage'
import RegistrationPage from './Pages/RegistrationPage'
import Layout from './Components/Layout'
import ProtectedRoute from './Components/ProtectedRoute'
import SolarWatchPage from './Pages/SolarWatchPage'


function App() {
  
  return (
      <Layout>
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/login" element={<LogInPage />} />
          <Route path="/register" element={<RegistrationPage />} />
          <Route path='/solarwatch' element={<SolarWatchPage/>}/>
        </Routes>
      </Layout>
  );
}

export default App;
