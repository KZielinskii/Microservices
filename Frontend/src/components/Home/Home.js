import React from 'react';
import { useNavigate } from 'react-router-dom';
import Tile from './Tile';
import './Home.css';
import game1Image from '../../assets/images/ship.jpg'
import game2Image from '../../assets/images/TicTacToe.jpg';

function Home() {
    const navigate = useNavigate();
    const handlePlayGame1 = () => {
        navigate('/shipsgame/setup');
    };


    const handlePlayGame2 = () => {
        navigate('/game-setup');
    };

    const handleRateGame = async (gameName, databaseGameName, rating, comment) => {
        try {
            const token = localStorage.getItem('token');
            const response = await fetch('http://localhost:8082/review/addReview', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    databaseGameName,
                    rating,
                    comment: comment,
                    username: localStorage.getItem('username') || "Anonim",
                }),
            });

            if (response.ok) {
                console.log('Dodano ocenę gry!');
            } else {
                console.error('Nie udało się dodać oceny.');
            }
        } catch (error) {
            console.error('Błąd podczas dodawania oceny:', error);
        }
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
        </div>
    );
}

export default Home;
