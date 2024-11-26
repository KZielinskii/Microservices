import React, {useState} from 'react';
import Board from './Board';
import './ShipGame.css';

const BOARD_SIZE = 10;

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

    const handleCellClick = async (row, col) => {
        //if (isHuman) return;

        const sessionId = localStorage.getItem('sessionId');
        if (!sessionId) {
            console.error('Brak ID sesji w localStorage.');
            return;
        }

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
                alert(data.message);
            }
        } catch (error) {
            console.error('Błąd podczas ruchu:', error);
        }
    };

    return (
        <div className="ship-game">
            <h1>Gra w Statki</h1>
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
