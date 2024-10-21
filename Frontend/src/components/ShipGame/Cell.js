import React from 'react';
import './ShipGame.css';

function Cell({ onClick, isHit, isShip }) {
    const cellClass = isHit ? 'cell hit' : isShip ? 'cell ship' : 'cell';

    return (
        <div className={cellClass} onClick={onClick}>
            {isHit && 'X'}
        </div>
    );
}

export default Cell;
