import React, {useEffect, useState} from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Home from './components/Home/Home';
import Login from './components/Login/Login';
import Register from './components/Register/Register';
import Navbar from './components/Navbar/Navbar';
import ShipGame from './components/ShipGame/ShipGame';
import TicTacToe from './components/TicTacToe/Board'
import GameSetup from "./components/TicTacToe/GameSetup";
import Scoreboard from "./components/TicTacToe/Scoreboard";
import GameRating from './components/Rating/GameRating';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const handleLogin = () => {
    setIsAuthenticated(true);
    localStorage.setItem('isAuthenticated', 'true');
  };

  const handleLogout = () => {
    setIsAuthenticated(false);
    localStorage.removeItem('isAuthenticated');
    localStorage.removeItem('token');
  };

  useEffect(() => {
    const auth = localStorage.getItem('isAuthenticated');
    if(auth === 'true') {
      setIsAuthenticated(true);
    }
  }, []);

  return (
      <Router>
        <div>
          {isAuthenticated && <Navbar onLogout={handleLogout} />}
          <Routes>
            <Route
                path="/login"
                element={isAuthenticated ? <Navigate to="/home" /> : <Login onLogin={handleLogin} />}
            />
            <Route
                path="/register"
                element={isAuthenticated ? <Navigate to="/home" /> : <Register onRegister={handleLogin} />}
            />
            <Route
                path="/home"
                element={isAuthenticated ? <Home /> : <Navigate to="/login" />}
            />
            <Route
                path="/shipsgame"
                element={isAuthenticated ? <ShipGame /> : <Navigate to="/login" />}
            />
            <Route
                path="/tic-tac-toe"
                element={isAuthenticated ? <TicTacToe /> : <Navigate to="/login" />}
            />
            <Route
                path="/game-setup"
                element={isAuthenticated ? <GameSetup /> : <Navigate to="/login" />}
            />
            <Route
                path="/game-rating/:gameName/:starValue"
                element={isAuthenticated ? <GameRating /> : <Navigate to="/login" />}
            />
            <Route
                path="/scoreboard"
                element={isAuthenticated ? <Scoreboard /> : <Navigate to="/login" />}
            />
            <Route path="/" element={<Navigate to={isAuthenticated ? '/home' : '/login'} />} />
          </Routes>
        </div>
      </Router>
  );
}

export default App;
