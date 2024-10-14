import React from 'react';
import './Navbar.css';

function Navbar({ onLogout }) {
    return (
        <nav className="navbar">
            <div className="profile">
                <img src="https://via.placeholder.com/40" alt="Profile" className="profile-pic" />
            </div>
            <button className="logout-button" onClick={onLogout}>Log Out</button>
        </nav>
    );
}

export default Navbar;
