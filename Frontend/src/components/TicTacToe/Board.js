import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import Cell from './Cell';
import './TicTacToe.css';

function Board() {
    const [cells, setCells] = useState(() => {
        const savedCells = localStorage.getItem('cells');
        return savedCells ? JSON.parse(savedCells) : Array(9).fill(null);
    });
    const [isPlayerTurn, setIsPlayerTurn] = useState(() => {
        const savedTurn = localStorage.getItem('isPlayerTurn');
        return savedTurn ? JSON.parse(savedTurn) : true;
    });
    const [winner, setWinner] = useState(() => {
        const savedWinner = localStorage.getItem('winner');
        return savedWinner ? JSON.parse(savedWinner) : null;
    });
    const navigate = useNavigate();
    const { sessionId, symbol } = {
        sessionId: localStorage.getItem('sessionId'),
        symbol: localStorage.getItem('symbol'),
    };
    const [score, setScore] = useState(() => {
        const savedScore = localStorage.getItem('score');
        return savedScore ? JSON.parse(savedScore) : 0;
    });

    const handleClick = async (index) => {
        if (!isPlayerTurn || cells[index] || winner)return;

        const x = Math.floor(index / 3);
        const y = index % 3;
        const token = localStorage.getItem('token');
        const response = await fetch(`http://localhost:8082/game/move`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`,
                'sessionId': sessionId,
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

        const updatedCells = data.board.flat().map(row => row.split('')).flat().map(cell => (cell === '-' ? null : cell));
        const updatedWinner = (data.status.includes("wins")||data.status.includes("Draw")) ? data.status : null;
        const updatedIsPlayerTurn = (!data.status.includes("wins")&&!data.status.includes("Draw")) && data.status === "In progress";

        setScore(data.score);
        setCells(updatedCells);
        setWinner(updatedWinner);
        setIsPlayerTurn(updatedIsPlayerTurn);
    };

    useEffect(() => {
        localStorage.setItem('cells', JSON.stringify(cells));
        localStorage.setItem('isPlayerTurn', JSON.stringify(isPlayerTurn));
        localStorage.setItem('winner', JSON.stringify(winner));
        localStorage.setItem('score', JSON.stringify(score));
    }, [cells, isPlayerTurn, winner, score]);

    const saveScore = async () => {
        const username = localStorage.getItem('username');
        const gameName = "Tic Tac Toe";

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
        localStorage.removeItem('score');
        localStorage.removeItem('cells');
        localStorage.removeItem('isPlayerTurn');
        localStorage.removeItem('winner');
        localStorage.removeItem('sessionId');
        localStorage.removeItem('symbol');
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
                    Zapisz wynik i zagraj ponownie
                </button>
            </div>):(<div></div>)}
        </div>
    );
}

export default Board;
