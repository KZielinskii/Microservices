import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Register.css';

function Register() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (password !== confirmPassword) {
            setErrorMessage('Passwords do not match!');
            return;
        }

        try {
            const response = await fetch('http://localhost:8082/security/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    username: username,
                    password: password,
                }),
            });

            if (!response.ok) {
                throw new Error('Failed to register');
            }

            navigate('/login');
        } catch (error) {
            console.error('Error during registration:', error);
            if (error.response) {
                console.error('Response error data:', await error.response.json());
            }
            setErrorMessage('Registration failed. Please try again.');
        }
    };

    const handleNavigateToLogin = () => {
        navigate('/login');
    };

    return (
        <div className="register-container">
            <div className="register-box">
                <h2>Register</h2>
                <form onSubmit={handleSubmit}>
                    <div className="input-box">
                        <input
                            type="text"
                            placeholder="Username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div className="input-box">
                        <input
                            type="password"
                            placeholder="Password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <div className="input-box">
                        <input
                            type="password"
                            placeholder="Confirm Password"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            required
                        />
                    </div>
                    {errorMessage && <p className="error-message">{errorMessage}</p>}
                    <button type="submit" className="register-button">Register</button>
                </form>
                <button onClick={handleNavigateToLogin} className="move-button">
                    Posiadasz już konto?
                </button>
            </div>
        </div>
    );
}

export default Register;
