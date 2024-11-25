import React, { useState } from 'react';
import Cell from './Cell';
import './ShipGame.css';

const BOARD_SIZE = 10;

function Board({ isHuman }) {
    const [board, setBoard] = useState(createBoard());

    function createBoard() {
        if(isHuman) {
            const humanBoardData = localStorage.getItem('human_board');
            if (humanBoardData) {
                const humanBoard = JSON.parse(humanBoardData);
                console.log(humanBoard);
            } else {
                console.log('Brak danych w localStorage pod kluczem "human_board"');
            }
        }
        const board = Array.from({ length: BOARD_SIZE }, () => Array(BOARD_SIZE).fill(0));
        board[1][1] = 1;
        board[4][4] = 1;// Przykładowy statek
        return board;
    }

    const handleCellClick = (row, col) => {
        if (isHuman) return;
        const newBoard = board.map(arr => arr.slice());

        if (board[row][col] === 1) {
            newBoard[row][col] = 2;
            console.log(`Trafiono w statek! Wiersz: ${row}, Kolumna: ${col}`);
        }
        if (board[row][col] === 0) {
            newBoard[row][col] = -1;
            console.log(`Nietrafiono! Wiersz: ${row}, Kolumna: ${col}`);
        }

        setBoard(newBoard);
    };

    return (
        <div>
            {isHuman ?
            <h2>Twoja plansza</h2>:
            <h2>AI plansza</h2>
        }

            <div className="ship_board">
                {board.map((row, rowIndex) => (
                    <div key={rowIndex} className="row">
                        {row.map((cell, colIndex) => (
                            <Cell
                                key={colIndex}
                                onClick={() => handleCellClick(rowIndex, colIndex)}
                                cellValue={cell}
                            />
                        ))}
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Board;
