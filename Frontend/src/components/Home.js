import React from 'react';
import { useNavigate } from 'react-router-dom';
import Tile from './Tile';
import './Home.css';
import game1Image from '../assets/images/ship.jpg'
import game2Image from '../assets/images/TicTacToe.jpg';

function Home() {
    const navigate = useNavigate();
    const handlePlayGame1 = () => {
        navigate('/ship-game');
    };

    const handlePlayGame2 = () => {
        navigate('/game-setup');
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
