import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';

function Login({ onLogin }) {
    const [data, setData] = useState([]);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        try {
            const response = await fetch('http://localhost:8082/security/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    username: username,
                    password: password
                }),
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Logowanie nie powiodło się');
            }

            const data = await response.json();

            localStorage.setItem('token', data.token);
            localStorage.setItem('username', username);

            console.log('Response data:', data);

            onLogin();
        } catch (error) {
            console.error('Error during POST request:', error);
            setError(error.message);
        }

    };

    const handleNavigateToLogin = () => {
        navigate('/register');
    };

    return (
        <div className="login-container">
            <div className="login-box">
                <h2>Login</h2>
                <form onSubmit={handleSubmit}>
                    <div className="input-box">
                        <input
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            type="text"
                            placeholder="Username"
                            required
                        />
                    </div>
                    <div className="input-box">
                        <input
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            type="password"
                            placeholder="Password"
                            required
                        />
                    </div>
                    <button type="submit" className="login-button">Log In</button>
                </form>
                {error && <p className="error-message">{error}</p>}
                <button onClick={handleNavigateToLogin} className="move-button">
                    Nie posiadasz konta? Zarejestruj się.
                </button>
            </div>
        </div>
    );
}

export default Login;
