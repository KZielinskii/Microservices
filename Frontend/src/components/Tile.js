import React, { useState } from 'react';
import './Tile.css';

function Tile({ title, imageUrl, onPlay, onRate }) {
    const [rating, setRating] = useState(0);
    const [hover, setHover] = useState(0);

    const handleRate = () => {
        const userComment = prompt(`Dodaj komentarz do gry ${title}:`);
        if (userComment) {
            onRate(rating, userComment);
        } else {
            alert("Komentarz jest wymagany.");
        }
    };

    return (
        <div className="tile">
            <img src={imageUrl} alt={title} className="tile-image" />
            <h3 className="tile-title">{title}</h3>
            <div className="rating-container">
                {[...Array(5)].map((_, index) => {
                    const starValue = index + 1;
                    return (
                        <span
                            key={index}
                            className={`star ${starValue <= (hover || rating) ? 'filled' : ''}`}
                            onClick={() => setRating(starValue)}
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
            <button className="rate-button" onClick={handleRate}>Oceń grę</button>
        </div>
    );
}

export default Tile;
