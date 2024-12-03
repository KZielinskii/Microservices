import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './GameRating.css'

function GameRating() {
    const { gameName, starValue } = useParams();
    const [reviews, setReviews] = useState([]);
    const [comment, setComment] = useState('');

    useEffect(() => {
       //todo wyświetlanie opinii
    });

    const handleSubmit = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await fetch('http://localhost:8082/review/addReview', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    gameName: gameName,
                    rating: starValue,
                    comment: comment,
                    username: localStorage.getItem('username') || 'Anonim',
                }),
            });

            if (response.ok) {
                console.log('Dodano ocenę gry!');
                setComment('');
            } else {
                console.error('Nie udało się dodać oceny.');
            }
        } catch (error) {
            console.error('Błąd podczas dodawania oceny:', error);
        }
    };

    return (
        <div className="game-rating-container">
            <h1>Oceny i opinie dla "{gameName}"</h1>
            <div>
                <h2>Dodaj swoją opinię:</h2>
                <h3>Ocena: {starValue}</h3>
                <label>
                    Komentarz:
                    <textarea
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                    />
                </label>
                <button onClick={handleSubmit}>Dodaj opinię</button>
            </div>
            <div className="reviews-container">
                <h3>Opinie innych użytkowników:</h3>
                {reviews.length > 0 ? (
                    reviews.map((review, index) => (
                        <div key={index} className="review">
                            <p><span className="username">{review.username}</span><span
                                className="rating">({review.rating}/5)</span>:</p>
                            <p>{review.comment}</p>
                        </div>
                    ))
                ) : (
                    <p>Brak opinii dla tej gry.</p>
                )}
            </div>
        </div>
    );
}

export default GameRating;
