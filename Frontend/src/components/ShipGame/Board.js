import React, { useState } from 'react';
import Cell from './Cell';
import './ShipGame.css';

const BOARD_SIZE = 10;

function Board({ player }) {
    const [board, setBoard] = useState(createBoard());
    const [hits, setHits] = useState(Array(BOARD_SIZE).fill(false).map(() => Array(BOARD_SIZE).fill(false)));

    function createBoard() {
        const board = [];
        for (let i = 0; i < BOARD_SIZE; i++) {
            const row = [];
            for (let j = 0; j < BOARD_SIZE; j++) {
                row.push(false);
            }
            board.push(row);
        }
        board[1][1] = true; // Przykładowy statek
        return board;
    }

    const handleCellClick = (row, col) => {
        if (hits[row][col]) return; // Już trafiono w tę komórkę

        const newHits = hits.map(arr => arr.slice());
        newHits[row][col] = true;
        setHits(newHits);

        console.log(`Kliknięto komórkę: Wiersz: ${row}, Kolumna: ${col}`);
    };

    return (
        <div>
            <h2>{player}</h2>
            <div className="ship_board">
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
        </div>
    );
}

export default Board;
