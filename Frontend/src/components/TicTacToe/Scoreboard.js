import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Scoreboard.css';

function Scoreboard() {
    const [scores, setScores] = useState([]);
    const navigate = useNavigate();

    const goBack = () => {
        navigate('/game-setup');
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
                {scores.map((score, index) => (
                    <tr key={index}>
                        <td>{index + 1}</td>
                        <td>{score.player}</td>
                        <td>{score.score}</td>
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
