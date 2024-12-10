import React, {useState} from 'react';
import Board from './Board';
import './ShipGame.css';

function ShipGame() {

    const [aiBoard, setAiBoard] = useState(Array(10).fill().map(() => Array(10).fill(0)));
    const [humanBoard, setHumanBoard] = useState(() => {
        const humanBoardData = localStorage.getItem('human_board');
        if (humanBoardData) {
            const humanBoard = JSON.parse(humanBoardData);
            console.log(humanBoard);
            return humanBoard
        } else {
            console.log('Brak danych "human_board"');
        }
    });

    const [winner, setWinner] = useState(null);
    const [isGameOver, setIsGameOver] = useState(false);
    const {sessionId} = {
        sessionId: localStorage.getItem('sessionId_ship'),
    }


    const handleCellClick = async (row, col) => {
        if (isGameOver) return;

        try {
            const token = localStorage.getItem('token');
            const response = await fetch('http://localhost:8082/shipsgame/move', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                    'sessionId': sessionId,
                },
                body: JSON.stringify({x: row, y: col}),
            });

            if (!response.ok) {
                const errorData = await response.json();
                console.error('Błąd podczas ruchu:', errorData.message);
                return;
            }

            const data = await response.json();
            console.log('Ruch wykonany:', data);

            setAiBoard(data.board_ai);
            setHumanBoard(data.board_human);
            localStorage.setItem('human_board', JSON.stringify(data.board_human));

            if (data.message === "Human won" || data.message === "AI won") {
                setWinner(data.message);
                setIsGameOver(true);
                await saveScore(data.score);
            }
        } catch (error) {
            console.error('Błąd podczas ruchu:', error);
        }
    };

    const saveScore = async (score) => {
        const username = localStorage.getItem('username');
        const gameName = "Ships game";

        const scoreData = {
            name: gameName,
            scores: [
                {
                    username,
                    score,
                },
            ],
        };

        const token = localStorage.getItem('token');
        try {
            const response = await fetch('http://localhost:8082/dashboard/addScore', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'sessionId': sessionId,
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
        <div className="ship-game">
            <br/><br/><br/>
            <h1>Gra w Statki</h1>
            {winner && <h2 className="winner-message">{winner}</h2>}
            <div className="boards-container">
                <Board
                    isHuman={true}
                    board={humanBoard}
                    onCellClick={() => {}}
                />
                <Board
                    isHuman={false}
                    board={aiBoard}
                    onCellClick={handleCellClick}
                />
            </div>
        </div>
    );
}

export default ShipGame;
