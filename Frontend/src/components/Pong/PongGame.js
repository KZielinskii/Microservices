import React, { useState, useEffect } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import './Pong.css';

function PongGame() {
    const [searchParams] = useSearchParams();
    const difficulty = searchParams.get('difficulty') || 'medium';

    const navigate = useNavigate();
    const [ballSpeed, setBallSpeed] = useState(0);
    const [playerScore, setPlayerScore] = useState(0);
    const [botScore, setBotScore] = useState(0);

    const [ball, setBall] = useState({ x: 50, y: 50, dx: 1, dy: 1 });
    const [playerY, setPlayerY] = useState(40);
    const [botY, setBotY] = useState(40);

    const ballSpeedMap = { easy: 5, medium: 10, hard: 15 };

    useEffect(() => {
        setBallSpeed(ballSpeedMap[difficulty]);

        const interval = setInterval(() => {
            moveBall();
            moveBot();
        }, 5);

        return () => clearInterval(interval);
    }, [ball, playerY]);

    const moveBall = () => {
        setBall((prev) => {
            let { x, y, dx, dy } = prev;

            if (y <= 0 || y >= 99) dy = -dy;

            if (x <= 3 && y >= playerY-2 && y <= playerY + 22) {
                x = 3;
                dx = -dx;
            }

            if (x >= 95 && y >= botY-2 && y <= botY + 22){
                x = 95;
                dx = -dx;
            }

            if (x <= 0) {
                setBotScore((score) => score + 1);
                return resetBall();
            }

            if (x >= 100) {
                setPlayerScore((score) => score + 1);
                return resetBall();
            }

            return { x: x + dx * (ballSpeed / 20), y: y + dy * (ballSpeed / 20), dx, dy };
        });
    };

    const moveBot = () => {
        setBotY((prev) => {
            const botMovement = ball.y - prev;
            let random = 0;
            let newY = prev;

            if (difficulty === 'easy') {
                random = Math.random() * 5;
                if (Math.abs(botMovement + random) > 2) {
                    newY = prev + Math.sign(botMovement + random) * 0.5;
                }
            }
            else if (difficulty === 'medium') {
                random = Math.random() * 2;
                if (Math.abs(botMovement + random) > 1) {
                    newY = prev + Math.sign(botMovement + random);
                }
            }
            else if (difficulty === 'hard') {
                newY = prev + Math.sign(botMovement) * 2;
            }

            if (newY < 0) newY = 0;
            if (newY > 80) newY = 80;

            return newY;
        });
    };

    const resetBall = () => {
        const randomDirection = Math.random() < 0.5 ? -1 : 1;
        return { x: 50-randomDirection*35, y: 45, dx: randomDirection, dy: ball.dy };
    };

    const handleKeyPress = (e) => {
        if (e.key === 'w' || e.key === 'W') {
            const temp = playerY - 5;
            if (playerY > 0)
                setPlayerY(temp);
        } else if (e.key === 's' || e.key === 'S') {
            const temp = playerY + 5;
            if (playerY < 80)
                setPlayerY(temp);
        }
    };

    useEffect(() => {
        if (playerScore >= 10 || botScore >= 10) {
            alert(playerScore >= 10 ? 'Wygrałeś!' : 'Przegrałeś!');
            saveScore();
            navigate('/');
        }
    }, [playerScore, botScore, navigate]);

    const saveScore = async () => {
        const username = localStorage.getItem('username');
        const gameName = "Pong";

        const scoreData = {
            name: gameName,
            scores: [
                {
                    username
                },
            ],
        };

        const token = localStorage.getItem('token');
        try {
            const response = await fetch('http://localhost:8082/dashboard/addScore', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(scoreData),
            });

            if (!response.ok) {
                const errorData = await response.json();
                console.error('Błąd przy zapisie wyniku:', errorData.message);
                alert('Nie udało się zapisać wyniku: ' + errorData.message);
                return;
            }

            console.log('Wynik zapisany pomyślnie!');
        } catch (error) {
            console.error('Błąd sieci przy zapisie wyniku:', error);
            alert('Błąd sieci przy zapisie wyniku: ' + error.message);
        }
    };

    return (
        <div className="difficulty-container">
        <div>
            <div className="scoreboard">
                <span>Gracz: {playerScore}</span>
                <span>Bot: {botScore}</span>
            </div>
            <div className="pong-container" tabIndex={0} onKeyDown={handleKeyPress}>
                <div className="pong-game">
                    <div className="paddle player" style={{top: `${playerY}%`}}></div>
                    <div className="ball" style={{top: `${ball.y}%`, left: `${ball.x}%`}}></div>
                    <div className="paddle bot" style={{top: `${botY}%`}}></div>
                </div>
            </div>
        </div>
        </div>
    );
}

export default PongGame;
