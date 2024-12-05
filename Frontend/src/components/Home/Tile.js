import React, { useState } from 'react';
import './Tile.css';
import {useNavigate} from "react-router-dom";

function Tile({ title, imageUrl, onPlay, gameName }) {
    const [hover, setHover] = useState(0);
    const navigate = useNavigate();

    const handleRate = (starValue) => {
        setHover(starValue);
        navigate(`/game-rating/${gameName}/${starValue}`);
    };

    const handleRateButton = () => {
        navigate(`/game-rating/${gameName}/${null}`);
    };

    return (
        <div className="tile">
            <img src={imageUrl} alt={title} className="tile-image"/>
            <h3 className="tile-title">{title}</h3>
            <div className="rating-container">
                {[...Array(5)].map((_, index) => {
                    const starValue = index + 1;
                    return (
                        <span
                            key={index}
                            className={`star ${starValue <= (hover) ? 'filled' : ''}`}
                            onClick={() => handleRate(starValue)}
                            onMouseEnter={() => setHover(starValue)}
                            onMouseLeave={() => setHover(0)}
                        >
                            ★
                        </span>
                    );
                })}
            </div>
            <button className="play-button" onClick={onPlay}>Zagraj w grę</button>
            <br/>
            <button className="rate-button" onClick={handleRateButton}>Zobacz opinie</button>
        </div>
    );
}

export default Tile;
