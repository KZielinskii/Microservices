import React from 'react';
import Board from './Board';
import './ShipGame.css';

function ShipGame() {
    return (
        <div className="ship-game">
            <h1>Gra w Statki</h1>
            <Board />
        </div>
    );
}

export default ShipGame;
