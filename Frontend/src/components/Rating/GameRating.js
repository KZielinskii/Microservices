import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './GameRating.css';

function GameRating() {
    const { gameName, starValue } = useParams();
    const [reviews, setReviews] = useState([]);
    const [comment, setComment] = useState('');
    const [currentPage, setCurrentPage] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [update, setUpdate] = useState(true);

    useEffect(() => {
        const fetchReviews = async () => {
            try {
                const token = localStorage.getItem('token');
                const response = await fetch(`http://localhost:8082/review/review-page?gameName=${gameName}&page=${currentPage}&size=10&sortBy=id`, {
                        headers: {
                            'Authorization': `Bearer ${token}`,
                            'Content-Type': 'application/json',
                        },
                });
                if (response.ok) {
                    const data = await response.json();
                    setReviews(data.content);
                    setTotalPages(data.totalPages);
                } else {
                    console.error('Nie udało się pobrać opinii.');
                }
            } catch (error) {
                console.error('Błąd podczas pobierania opinii:', error);
            }
        };

        fetchReviews();
    }, [gameName, currentPage, update]);

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
                setCurrentPage(0);
                setUpdate(!update);
                alert('Dziękujemy za udzielenie opinii!');

            } else {
                console.error('Nie udało się dodać oceny.');
            }
        } catch (error) {
            console.error('Błąd podczas dodawania oceny:', error);
        }
    };

    const handleNextPage = () => {
        if (currentPage < totalPages - 1) {
            setCurrentPage((prevPage) => prevPage + 1);
        }
    };

    const handlePreviousPage = () => {
        if (currentPage > 0) {
            setCurrentPage((prevPage) => prevPage - 1);
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
                            <p>
                                <span className="username">{review.username}</span>
                                <span className="rating">({review.rating}/5)</span>:
                            </p>
                            <p>{review.comment}</p>
                        </div>
                    ))
                ) : (
                    <p>Brak opinii dla tej gry.</p>
                )}
            </div>
            <div className="pagination-controls">
                <button onClick={handlePreviousPage} disabled={currentPage === 0}>
                    Poprzednia
                </button>
                <span>
                    Strona {currentPage + 1} z {totalPages}
                </span>
                <button
                    onClick={handleNextPage}
                    disabled={currentPage === totalPages - 1}
                >
                    Następna
                </button>
            </div>
        </div>
    );
}

export default GameRating;
