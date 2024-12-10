import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import './Scoreboard.css';

function Scoreboard() {
    const [scores, setScores] = useState([]);
    const navigate = useNavigate();
    const { gameName } = useParams();

    useEffect(() => {
        const fetchTopScores = async () => {
            try {
                const token = localStorage.getItem('token');
                const response = await fetch(`http://localhost:8082/dashboard/top-scores?gameName=${gameName}`, {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`,
                        'Content-Type': 'application/json',
                    },
                });

                if (!response.ok) {
                    throw new Error('Błąd podczas pobierania wyników');
                }

                const data = await response.json();

                setScores(data);
            } catch (error) {
                console.error('Błąd:', error.message);
            }
        };

        fetchTopScores();
    }, []);

    const goBack = () => {
        navigate('/home');
    };

    return (
        <div className="scoreboard-container">
            <h1>Najlepsze wyniki</h1>
            <table className="scoreboard-table">
                <thead>
                <tr>
                    <th>Miejsce</th>
                    <th>Gracz</th>
                    <th>Wynik</th>
                </tr>
                </thead>
                <tbody>
                {scores.slice(0, 10).map((score, index) => (
                    <tr key={index}>
                        <td>{index + 1}</td>
                        <td>{score.username || 'Anonim'}</td>
                        <td>{score.score || 0}</td>
                    </tr>
                ))}
                </tbody>
            </table>
            <button className="view-back-button" onClick={goBack}>
                Wróć
            </button>
        </div>
    );
}

export default Scoreboard;
