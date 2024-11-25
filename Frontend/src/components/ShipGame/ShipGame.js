import React from 'react';
import Board from './Board';
import './ShipGame.css';

function ShipGame() {
    return (
        <div className="ship-game">
            <h1>Gra w Statki</h1>
            <div className="boards-container">
                <Board isHuman={true}/>
                <Board isHuman={false}/>
            </div>
        </div>
    );
}

export default ShipGame;
