import React from 'react';
import { useNavigate } from 'react-router-dom';
import Tile from './Tile';
import './Home.css';
import game1Image from '../../assets/images/ship.jpg'
import game2Image from '../../assets/images/TicTacToe.jpg';
import game3Image from '../../assets/images/Pong.jpg';

function Home() {
    const navigate = useNavigate();
    const handlePlayGame1 = () => {
        navigate('/shipsgame/setup');
    };

    const handlePlayGame2 = () => {
        navigate('/game-setup');
    };

    const handlePlayGame3 = () => {
        navigate('/pong/difficulty');
    };

    return (
        <div className="home-container">
            <Tile
                title="Statki"
                imageUrl={game1Image}
                onPlay={handlePlayGame1}
                gameName="Ships Game"
            />
            <Tile
                title="Kółko i Krzyżyk"
                imageUrl={game2Image}
                onPlay={handlePlayGame2}
                gameName="Tic Tac Toe"
            />
            <Tile
                title="Pong"
                imageUrl={game3Image}
                onPlay={handlePlayGame3}
                gameName="Pong"
            />
        </div>
    );
}

export default Home;
