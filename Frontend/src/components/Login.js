import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';

function Login({ onLogin }) {
    const [data, setData] = useState([]);
    const [username, setUsername] = useState(''); // Stan dla pola username
    const [password, setPassword] = useState(''); // Stan dla pola password
    const [error, setError] = useState(''); // Stan dla komunikatu o błędzie

    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(''); // Resetowanie błędu przed nową próbą logowania
        /*
        try {
            const response = await fetch('http://localhost:8081/login', {
                method: 'POST',  // Ustawienie metody na POST
                headers: {
                    'Content-Type': 'application/json',  // Określenie, że wysyłamy dane w formacie JSON
                },
                body: JSON.stringify({
                    username: username,  // Przykładowe dane wysyłane w body requesta
                    password: password
                }),
            });

            // Sprawdzenie czy odpowiedź jest OK (status 200-299)
            if (!response.ok) {
                const errorData = await response.json(); // Odczytaj odpowiedź w przypadku błędu
                throw new Error(errorData.message || 'Logowanie nie powiodło się'); // Ustaw komunikat błędu
            }

            // Odczytanie odpowiedzi jako JSON
            const data = await response.json();

            // Przechowaj token lub inne dane z odpowiedzi w localStorage
            localStorage.setItem('token', data.token);  // Jeśli odpowiedź zawiera np. token

            // Wyświetlenie danych odpowiedzi w konsoli
            console.log('Response data:', data);

            // Dodatkowa logika np. zalogowanie użytkownika
            onLogin();
        } catch (error) {
            console.error('Error during POST request:', error);
            setError(error.message); // Ustaw komunikat błędu
            */
        // Sprawdzenie, czy login i hasło są poprawne

        //todo usuń to
        if (username === '123' && password === '123') {
            // Symulacja logowania: przechowaj token lub inne dane
            localStorage.setItem('token', 'fake_token'); // Możesz tu ustawić dowolny token
            console.log('Logowanie powiodło się');
            onLogin(); // Dodatkowa logika np. zalogowanie użytkownika
            navigate('/'); // Przekierowanie po zalogowaniu, możesz zmienić na odpowiednią trasę
        } else {
            // Ustawienie komunikatu błędu, jeśli login lub hasło są niepoprawne
            setError('Nieprawidłowy login lub hasło');
        }
        //todo koniec
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
                {error && <p className="error-message">{error}</p>} {/* Wyświetlenie komunikatu o błędzie */}
                <button onClick={handleNavigateToLogin} className="move-button">
                    Nie posiadasz konta? Zarejestruj się.
                </button>
            </div>
        </div>
    );
}

export default Login;
