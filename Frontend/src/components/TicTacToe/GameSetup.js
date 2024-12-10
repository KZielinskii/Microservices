import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './GameSetup.css';

function GameSetup() {
    const [difficulty, setDifficulty] = useState('EASY');
    const [symbol, setSymbol] = useState('X');
    const [first, setFirst] = useState('HUMAN');
    const navigate = useNavigate();
    const sessionId = localStorage.getItem('sessionId');


    const startGame = async () => {
        const token = localStorage.getItem('token');
        try {

            const response = await fetch(`http://localhost:8082/game/start?difficulty=${difficulty}&symbol=${symbol}&firstMove=${first}`, {
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

            localStorage.removeItem('cells');
            localStorage.removeItem('isPlayerTurn');
            localStorage.removeItem('winner');

            const resp = await response.json();
            localStorage.setItem('sessionId', resp.sessionId);
            localStorage.setItem('symbol', symbol);
            const updatedCells = resp.board.flat().map(row => row.split('')).flat().map(cell => (cell === '-' ? null : cell));
            localStorage.setItem('cells', JSON.stringify(updatedCells))

            navigate('/tic-tac-toe', { state: { sessionId, symbol, first } });
        } catch (error) {
            console.error('Błąd podczas uruchamiania gry:', error);
            alert('Błąd podczas uruchamiania gry: ' + error.message);
        }
    };

    const goToScores = () => {
        navigate(`/scoreboard/Tic%20Tac%20Toe`);
    };

    const resumeGame = () => {
        navigate('/tic-tac-toe');
    };

    return (
        <div className="game-setup-container">
            <div className="game-setup-frame">
                <h1 className="game-setup-title">Ustawienia gry:</h1>
                <div className="game-setup-option">
                    <label className="option-label">Wybierz trudność:</label>
                    <select className="option-select" value={difficulty}
                            onChange={(e) => setDifficulty(e.target.value)}>
                        <option value="EASY">Łatwy</option>
                        <option value="MEDIUM">Średni</option>
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
                <div className="game-setup-option">
                    <label className="option-label">Kto zaczyna:</label>
                    <select className="option-select" value={first} onChange={(e) => setFirst(e.target.value)}>
                        <option value="HUMAN">Ty</option>
                        <option value="AI">AI</option>
                    </select>
                </div>
                <br></br>
                <button className="start-game-button" onClick={startGame}>Zagraj w grę</button>
                {sessionId && (
                    <button className="start-game-button" onClick={resumeGame}>Wznów grę</button>
                )}
                <button className="view-back-button" onClick={goToScores}>Zobacz wyniki</button>
            </div>
        </div>
    );
}

export default GameSetup;
