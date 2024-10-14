import React from 'react';
import Tile from './Tile';
import './Home.css';
import game1Image from '../assets/images/ship.jpg'
import game2Image from '../assets/images/TicTacToe.jpg';

function Home() {
    const handlePlayGame1 = () => {
        alert('Zagraj w grę 1');
    };

    const handlePlayGame2 = () => {
        alert('Zagraj w grę 2');
    };

    return (
        <div className="home-container">
            <Tile
                title="Statki"
                imageUrl={game1Image}
                onPlay={handlePlayGame1}
            />
            <Tile
                title="Kółko i Krzyżyk"
                imageUrl={game2Image}
                onPlay={handlePlayGame2}
            />
        </div>
    );
}

export default Home;
