import React, { useState, useEffect } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import './Pong.css';

function PongGame() {
    const [searchParams] = useSearchParams();
    const difficulty = searchParams.get('difficulty') || 'medium';

    const navigate = useNavigate();
    const [ballSpeed, setBallSpeed] = useState(3);
    const [playerScore, setPlayerScore] = useState(0);
    const [botScore, setBotScore] = useState(0);

    const [ball, setBall] = useState({ x: 50, y: 50, dx: 5, dy: 5 });
    const [playerY, setPlayerY] = useState(40);
    const [botY, setBotY] = useState(40);

    const ballSpeedMap = { easy: 1, medium: 2, hard: 3 };

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

            if (x <= 2 && y >= playerY && y <= playerY + 20) dx = -dx;

            if (x >= 97 && y >= botY && y <= botY + 20) dx = -dx;

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
                random = Math.random() * 6 - 3;
                if (Math.abs(botMovement + random) > 2) {
                    newY = prev + Math.sign(botMovement + random) * 0.5;
                }
            }
            else if (difficulty === 'medium') {
                random = Math.random() * 2 - 1;
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
        return { x: 50, y: 50, dx: 5 * randomDirection, dy: ball.dy };
    };

    const handleMouseMove = (e) => {
        const pongContainer = document.querySelector('.pong-container');
        const pongContainerHeight = pongContainer.offsetHeight;
        const mousePositionInContainer = e.clientY - pongContainer.getBoundingClientRect().top;

        let newY = (mousePositionInContainer / pongContainerHeight) * 100;

        setPlayerY(newY > 80 ? 80 : newY < 0 ? 0 : newY);
    };



    useEffect(() => {
        if (playerScore >= 10 || botScore >= 10) {
            alert(playerScore >= 10 ? 'Wygrałeś!' : 'Przegrałeś!');
            navigate('/');
        }
    }, [playerScore, botScore, navigate]);

    return (
        <div className="difficulty-container">
        <div>
            <div className="scoreboard">
                <span>Gracz: {playerScore}</span>
                <span>Bot: {botScore}</span>
            </div>
            <div className="pong-container" onMouseMove={handleMouseMove}>
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
