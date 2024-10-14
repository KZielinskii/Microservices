import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Home from './components/Home';
import Login from './components/Login';
import Navbar from './components/Navbar';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const handleLogin = () => {
    setIsAuthenticated(true);
  };

  const handleLogout = () => {
    setIsAuthenticated(false);
  };

  return (
      <Router>
        <div>
          {}
          {isAuthenticated && <Navbar onLogout={handleLogout} />}
          <Routes>
            {}
            <Route path="/login" element={isAuthenticated ? <Navigate to="/home" /> : <Login onLogin={handleLogin} />} />
            {}
            <Route path="/home" element={isAuthenticated ? <Home /> : <Navigate to="/login" />} />
            {}
            <Route path="/" element={<Navigate to={isAuthenticated ? "/home" : "/login"} />} />
          </Routes>
        </div>
      </Router>
  );
}

export default App;
