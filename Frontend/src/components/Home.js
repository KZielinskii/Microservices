import React from 'react';
import { useNavigate } from 'react-router-dom';
import Tile from './Tile';
import './Home.css';
import game1Image from '../assets/images/ship.jpg'
import game2Image from '../assets/images/TicTacToe.jpg';

function Home() {
    const navigate = useNavigate();
    const handlePlayGame1 = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await fetch('http://localhost:8082/shipsgame/start', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                const errorData = await response.json();
                console.error('Błąd przy starcie gry:', errorData.message);
                return;
            }

            const data = await response.json();
            localStorage.setItem('sessionId', data.sessionId);
            localStorage.setItem('human_board', data.human_board);

            console.log('Gra rozpoczęta z ID sesji:', data.sessionId);
        } catch (error) {
            console.error('Błąd przy starcie gry:', error);
        }
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
