import React, { useState } from 'react';
import Cell from './Cell';
import './TicTacToe.css';

function Board() {
    const [cells, setCells] = useState(Array(9).fill(null));
    const [isXNext, setIsXNext] = useState(true);
    const [winner, setWinner] = useState(null);

    const handleClick = (index) => {
        if (cells[index] || winner) return;

        const newCells = cells.slice();
        newCells[index] = isXNext ? 'X' : 'O';
        setCells(newCells);
        setIsXNext(!isXNext);
        checkWinner(newCells);
    };

    const checkWinner = (newCells) => {
        if (newCells.every(cell => cell)) {
            setWinner('Remis');
        }
    };

    const resetGame = () => {
        setCells(Array(9).fill(null));
        setIsXNext(true);
        setWinner(null);
    };

    return (
        <div className="tic-tac-toe">
            <h1>Kółko i Krzyżyk</h1>
            {winner ? (
                <div className="winner-message">
                    {winner === 'Remis' ? "Remis!" : `Zwycięzca: ${winner}`}
                </div>
            ) : (
                <div className="next-player">Następny: {isXNext ? 'X' : 'O'}</div>
            )}
            <div className="board">
                {cells.map((cell, index) => (
                    <Cell key={index} value={cell} onClick={() => handleClick(index)} />
                ))}
            </div>
            <button className="reset-button" onClick={resetGame}>
                Zresetuj grę
            </button>
        </div>
    );
}

export default Board;
