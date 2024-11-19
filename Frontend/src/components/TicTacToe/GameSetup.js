import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './GameSetup.css';

function GameSetup() {
    const [difficulty, setDifficulty] = useState('EASY');
    const [symbol, setSymbol] = useState('X');
    const navigate = useNavigate();

    const startGame = async () => {
        const token = localStorage.getItem('token');
        try {
            const response = await fetch(`http://localhost:8082/game/start?difficulty=${difficulty}&symbol=${symbol}`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Nie udało się rozpocząć gry');
            }

            const sessionId = await response.json();

            navigate('/tic-tac-toe', { state: { sessionId, symbol } });
        } catch (error) {
            console.error('Błąd podczas uruchamiania gry:', error);
            alert('Błąd podczas uruchamiania gry: ' + error.message);
        }
    };

    const goToScores = () => {
        navigate('/scoreboard');
    };

    return (
        <div className="game-setup-container">
            <div className="game-setup-frame">
                <h1 className="game-setup-title">Ustawienia gry:</h1>
                <div className="game-setup-option">
                    <label className="option-label">Wybierz trudność:</label>
                    <select className="option-select" value={difficulty} onChange={(e) => setDifficulty(e.target.value)}>
                        <option value="EASY">Łatwy</option>
                        <option value="HARD">Trudny</option>
                    </select>
                </div>

                <div className="game-setup-option">
                    <label className="option-label">Wybierz znak:</label>
                    <select className="option-select" value={symbol} onChange={(e) => setSymbol(e.target.value)}>
                        <option value="X">X</option>
                        <option value="O">O</option>
                    </select>
                </div>
                <br></br>
                <button className="start-game-button" onClick={startGame}>Zagraj w grę</button>
                <button className="view-back-button" onClick={goToScores}>Zobacz wyniki</button>
            </div>
        </div>
    );
}

export default GameSetup;
