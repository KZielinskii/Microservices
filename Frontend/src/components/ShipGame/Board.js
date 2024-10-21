import React, { useState } from 'react';
import Cell from './Cell';
import './ShipGame.css';

const BOARD_SIZE = 10;

function Board() {
    const [board, setBoard] = useState(createBoard());
    const [hits, setHits] = useState(Array(BOARD_SIZE).fill(Array(BOARD_SIZE).fill(false)));

    function createBoard() {
        const newBoard = Array(BOARD_SIZE).fill(null).map(() => Array(BOARD_SIZE).fill(false));

        // Todo logika umeszczania statków

        newBoard[1][1] = true; // Przykładowy statek

        return newBoard;
    }

    const handleCellClick = (row, col) => {
        if (hits[row][col]) return; // Już trafiono w tę komórkę

        const newHits = hits.map(arr => arr.slice());
        newHits[row][col] = true;
        setHits(newHits);

        if (board[row][col]) {
            alert('Trafiony!');
        } else {
            alert('Pudło!');
        }
    };

    return (
        <div className="board">
            {board.map((row, rowIndex) => (
                <div key={rowIndex} className="row">
                    {row.map((cell, colIndex) => (
                        <Cell
                            key={colIndex}
                            onClick={() => handleCellClick(rowIndex, colIndex)}
                            isHit={hits[rowIndex][colIndex]}
                            isShip={cell}
                        />
                    ))}
                </div>
            ))}
        </div>
    );
}

export default Board;
