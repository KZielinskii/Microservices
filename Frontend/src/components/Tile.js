import React from 'react';
import './Tile.css';

function Tile({ title, imageUrl, onPlay }) {
    return (
        <div className="tile">
            <img src={imageUrl} alt={title} className="tile-image" />
            <h3 className="tile-title">{title}</h3>
            <button className="play-button" onClick={onPlay}>Zagraj w grę</button>
        </div>
    );
}

export default Tile;
