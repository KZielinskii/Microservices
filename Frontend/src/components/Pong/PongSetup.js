import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Pong.css';

function PongSetup() {
    const navigate = useNavigate();

    const handleSelectDifficulty = (difficulty) => {
        navigate(`/pong/game?difficulty=${difficulty}`);
    };

    return (
        <div className="difficulty-container">
            <h1>Wybierz poziom trudności:</h1>
            <button className="default-button"  onClick={() => handleSelectDifficulty('easy')}>Łatwy</button>
            <button className="default-button"  onClick={() => handleSelectDifficulty('medium')}>Średni</button>
            <button className="default-button"  onClick={() => handleSelectDifficulty('hard')}>Trudny</button>
        </div>
    );
}

export default PongSetup;
