import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import Cell from './Cell';
import './TicTacToe.css';

function Board() {
    const [cells, setCells] = useState(Array(9).fill(null));
    const [isPlayerTurn, setIsPlayerTurn] = useState(true);
    const [winner, setWinner] = useState(null);
    const location = useLocation();
    const { sessionId, symbol } = location.state;

    useEffect(() => {
        if (sessionId) {
            fetchGameState();
        }
    }, [sessionId]);

    const fetchGameState = async () => {
        const response = await fetch(`/game/state`, {
            headers: { sessionId },
        });
        const data = await response.json();
        setCells(data.board.flat());
        setWinner(data.status.includes("wins") ? data.status : null);
        setIsPlayerTurn(data.status === "In progress" && data.board.flat().filter(c => c !== '-').length % 2 === 0);
    };

    const handleClick = async (index) => {
        if (!isPlayerTurn || cells[index] || winner) return;

        const x = Math.floor(index / 3);
        const y = index % 3;

        const response = await fetch(`/game/move`, {
            method: 'POST',
            headers: {
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
        setCells(data.board.flat());
        setWinner(data.status.includes("wins") ? data.status : null);
        setIsPlayerTurn(!data.status.includes("wins") && data.status === "In progress");
    };


    return (
        <div className="tic-tac-toe">
            <h1>Kółko i Krzyżyk</h1>
            {winner ? (
                <div className="winner-message">{winner}</div>
            ) : (
                <div className="next-player">Your symbol: {symbol}</div>
            )}
            <div className="board">
                {cells.map((cell, index) => (
                    <Cell key={index} value={cell} onClick={() => handleClick(index)} />
                ))}
            </div>
        </div>
    );
}

export default Board;
