import React from 'react';
import './TicTacToe.css';

function Cell({ value, onClick }) {
    return (
        <div className={`cell ${value ? 'filled' : ''}`} onClick={onClick}>
            {value}
        </div>
    );
}

export default Cell;
