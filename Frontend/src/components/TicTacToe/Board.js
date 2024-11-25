import React, { useState } from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import Cell from './Cell';
import './TicTacToe.css';

function Board() {
    const [cells, setCells] = useState(Array(9).fill(null));
    const [isPlayerTurn, setIsPlayerTurn] = useState(true);
    const [winner, setWinner] = useState(null);
    const location = useLocation();
    const navigate = useNavigate();
    const { sessionId, symbol } = location.state;

    const handleClick = async (index) => {
        if (!isPlayerTurn || cells[index] || winner)return;

        const x = Math.floor(index / 3);
        const y = index % 3;
        const token = localStorage.getItem('token');
        const response = await fetch(`http://localhost:8082/game/move`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'sessionId': sessionId.sessionId,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ x, y })
        });

        if (!response.ok) {
            const errorData = await response.json();
            alert(`Error: ${errorData}`);
            return;
        }

        const data = await response.json();

        setCells(
            data.board.flat().map(row => row.split('')).flat().map(cell => (cell === '-' ? null : cell))
        );

        setWinner(data.status.includes("wins") || data.status.includes("Draw") ? data.status : null);
        setIsPlayerTurn(!data.status.includes("wins" && !data.status.includes("Draw")) && data.status === "In progress");
    };

    const saveScore = async () => {
        const username = localStorage.getItem('username');
        const gameName = "Tic Tac Toe";
        const score = winner.includes('Player wins') ? 1 : 0;

        const scoreData = {
            name: gameName,
            scores: [
                {
                    username,
                    score,
                },
            ],
        };

        const token = localStorage.getItem('token');
        try {
            const response = await fetch('http://localhost:8082/dashboard/addScore', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'sessionId': sessionId.sessionId,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(scoreData),
            });

            if (!response.ok) {
                const errorData = await response.json();
                console.error('Błąd przy zapisie wyniku:', errorData.message);
                alert('Nie udało się zapisać wyniku: ' + errorData.message);
                return;
            }

            console.log('Wynik zapisany pomyślnie!');
        } catch (error) {
            console.error('Błąd sieci przy zapisie wyniku:', error);
            alert('Błąd sieci przy zapisie wyniku: ' + error.message);
        }
    };

    const restartGame = async () => {
        await saveScore();
        setCells(Array(9).fill(null));
        setIsPlayerTurn(true);
        setWinner(null);
        navigate('/game-setup');
    };

    return (
        <div className="tic-tac-toe">
            <h1>Kółko i Krzyżyk</h1>
            {winner ? (
                <div className="winner-message">
                    {winner}
                </div>
            ) : (
                <div className="next-player">Twój symbol: {symbol}</div>
            )}
            <div className="board">
                {cells.map((cell, index) => (
                    <Cell key={index} value={cell} onClick={() => handleClick(index)} />
                ))}
            </div>
            {winner ? (
            <div>
                <button className="reset-button" onClick={restartGame}>
                    Zagraj ponownie
                </button>
            </div>):(<div></div>)}
        </div>
    );
}

export default Board;
