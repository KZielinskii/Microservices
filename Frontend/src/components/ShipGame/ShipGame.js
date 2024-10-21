import React from 'react';
import Board from './Board';
import './ShipGame.css';

function ShipGame() {
    return (
        <div className="ship-game">
            <h1>Gra w Statki</h1>
            <div className="boards-container">
                <Board player="Gracz 1" />
                <Board player="Gracz 2" />
            </div>
        </div>
    );
}

export default ShipGame;
