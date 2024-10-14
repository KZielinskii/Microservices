import React from 'react';
import './Login.css';

function Login({ onLogin }) {
    const handleSubmit = (e) => {
        e.preventDefault();
        onLogin(); // Ustawienie użytkownika jako zalogowanego
    };

    return (
        <div className="login-container">
            <div className="login-box">
                <h2>Login</h2>
                <form onSubmit={handleSubmit}>
                    <div className="input-box">
                        <input type="text" placeholder="Username" required />
                    </div>
                    <div className="input-box">
                        <input type="password" placeholder="Password" required />
                    </div>
                    <button type="submit" className="login-button">Log In</button>
                </form>
            </div>
        </div>
    );
}

export default Login;
