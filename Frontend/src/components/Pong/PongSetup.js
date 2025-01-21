import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Pong.css';

function PongSetup() {
    const navigate = useNavigate();

    const handleSelectDifficulty = (difficulty) => {
        navigate(`/pong/game?difficulty=${difficulty}`);
    };

    const goToScores = () => {
        navigate(`/scoreboard/PONG`);
    };

    return (
        <div className="difficulty-container">
            <h1>Wybierz poziom trudności:</h1>
            <button className="default-button"  onClick={() => handleSelectDifficulty('easy')}>Łatwy</button>
            <button className="default-button"  onClick={() => handleSelectDifficulty('medium')}>Średni</button>
            <button className="default-button"  onClick={() => handleSelectDifficulty('hard')}>Trudny</button>
            <button className="view-back-button" onClick={goToScores}>Zobacz wyniki</button>
        </div>
    );
}

export default PongSetup;
