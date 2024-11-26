import React, { useState } from 'react';
import Cell from './Cell';
import './ShipGame.css';

function Board({ isHuman, board, onCellClick }) {

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
                                onClick={() => onCellClick(rowIndex, colIndex)}
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
