import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './ShipGame.css';
import Cell from './Cell';

const ShipSetup = ({ onSetupComplete }) => {
    const [board, setBoard] = useState(Array(10).fill().map(() => Array(10).fill(0)));
    const [selectedShip, setSelectedShip] = useState(null);
    const navigate = useNavigate();

    const shipsToPlace = [
        { size: 5, count: 1 },
        { size: 4, count: 1 },
        { size: 3, count: 2 },
        { size: 2, count: 1 },
        { size: 1, count: 2 },
    ];

    const [remainingShips, setRemainingShips] = useState(shipsToPlace.flatMap(ship => Array(ship.count).fill(ship.size)));

    const placeShip = (row, col, size, isHorizontal) => {
        if (!canPlaceShip(row, col, size, isHorizontal)) return false;

        const newBoard = board.map(row => [...row]);

        for (let i = 0; i < size; i++) {
            const x = row + (isHorizontal ? 0 : i);
            const y = col + (isHorizontal ? i : 0);
            newBoard[x][y] = 1;

            for (let dx = -1; dx <= 1; dx++) {
                for (let dy = -1; dy <= 1; dy++) {
                    const nx = x + dx;
                    const ny = y + dy;
                    if (nx >= 0 && nx < 10 && ny >= 0 && ny < 10 && newBoard[nx][ny] !== 1) {
                        newBoard[nx][ny] = -1;
                    }
                }
            }
        }

        setBoard(newBoard);
        setRemainingShips(remainingShips.slice(1));
        return true;
    };

    const canPlaceShip = (row, col, size, isHorizontal) => {
        for (let i = 0; i < size; i++) {
            const x = row + (isHorizontal ? 0 : i);
            const y = col + (isHorizontal ? i : 0);

            if (x < 0 || x >= 10 || y < 0 || y >= 10 || board[x][y] !== 0) {
                return false;
            }
        }
        return true;
    };

    const handleCellClick = (row, col) => {
        if (selectedShip !== null) {
            const success = placeShip(row, col, selectedShip.size, selectedShip.isHorizontal);
            if (success) setSelectedShip(null);
        }
    };

    const toggleShipOrientation = () => {
        if (selectedShip) {
            setSelectedShip({ ...selectedShip, isHorizontal: !selectedShip.isHorizontal });
        }
    };

    const resetBoard = () => {
        setBoard(Array(10).fill().map(() => Array(10).fill(0)));
        setRemainingShips(shipsToPlace.flatMap(ship => Array(ship.count).fill(ship.size)));
    };

    const handleConfirmSetup = () => {
        const shipCount = remainingShips.length;
        if (shipCount === 0) {
            localStorage.setItem('human_board', JSON.stringify(board));

            navigate('/shipsgame');
        } else {
            alert(`Pozostało do ustawienia: ${shipCount} statków.`);
        }
    };

    return (
        <div className="ship-setup">
            <h1>Ustaw swoje statki</h1>
            <div className="setup-container">
                <div className="ship-button-container">
                    <h3>Dostępne statki:</h3>
                    <ul>
                        {remainingShips.map((size, index) => (
                            <li key={index}>
                                <button className="ship-button" onClick={() => setSelectedShip({size, isHorizontal: true})}>
                                    Statek: {size}
                                </button>
                            </li>
                        ))}
                    </ul>
                </div>
                <div className="ship_board_choose">
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
                <div className="ship-button-container">
                    <button className="default-button" onClick={resetBoard}>Resetuj planszę</button>
                    <button className="default-button" onClick={handleConfirmSetup}>Potwierdź ustawienie</button>
                    {selectedShip && (
                        <button className="default-button" onClick={toggleShipOrientation}>
                            Obrot: {selectedShip.isHorizontal ? 'Pionowy' : 'Poziomy'}
                        </button>
                    )}
                </div>
            </div>
        </div>
    );
};

export default ShipSetup;
