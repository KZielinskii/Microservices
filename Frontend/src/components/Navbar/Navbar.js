import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Navbar.css';

function Navbar({ onLogout }) {
    const navigate = useNavigate();

    const handleHomeClick = () => {
        navigate('/home');
    };

    return (
        <nav className="navbar">
            <button className="home-button" onClick={handleHomeClick}>Strona główna</button>
            <button className="logout-button" onClick={onLogout}>Wyloguj się</button>
        </nav>
    );
}

export default Navbar;
