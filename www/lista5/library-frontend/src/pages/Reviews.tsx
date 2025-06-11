import { useEffect, useState } from 'react';
import api from '../api';

interface Review {
    id: number;
    rating: number;
    comment: string;
    Book?: { title: string };
    User?: { username: string };
}

interface Book {
    id: number;
    title: string;
}

export default function Reviews() {
    const [reviews, setReviews] = useState<Review[]>([]);
    const [books, setBooks] = useState<Book[]>([]);
    const [bookId, setBookId] = useState('');
    const [rating, setRating] = useState('');
    const [comment, setComment] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        fetchReviews();
        fetchBooks();
    }, []);

    const fetchReviews = async () => {
        try {
            const res = await api.get('/reviews');
            setReviews(res.data);
        } catch (err: any) {
            setError(err.response?.data?.error || 'Error loading reviews');
        }
    };

    const fetchBooks = async () => {
        try {
            const res = await api.get('/books');
            setBooks(res.data.data || res.data); // dopasuj w razie potrzeby
        } catch {
            setError('Failed to load books');
        }
    };

    const addReview = async () => {
        if (!bookId) {
            setError('Please select a book');
            return;
        }
        const ratingNum = Number(rating);
        if (!rating || isNaN(ratingNum) || ratingNum < 1 || ratingNum > 5) {
            setError('Rating must be a number between 1 and 5');
            return;
        }
        if (!comment.trim()) {
            setError('Comment cannot be empty');
            return;
        }

        setError('');
        try {
            await api.post('/reviews', {
                bookId: parseInt(bookId),
                rating: ratingNum,
                comment,
            });
            setBookId('');
            setRating('');
            setComment('');
            fetchReviews();
        } catch (err: any) {
            setError(err.response?.data?.error || 'Error adding review');
        }
    };

    const deleteReview = async (id: number) => {
        try {
            await api.delete(`/reviews/${id}`);
            fetchReviews();
        } catch (err: any) {
            setError(err.response?.data?.error || 'Error deleting review');
        }
    };

    return (
        <div>
            <h2>My Reviews</h2>
            <select value={bookId} onChange={(e) => setBookId(e.target.value)}>
                <option value="">Select a book</option>
                {books.map((book) => (
                    <option key={book.id} value={book.id}>{book.title}</option>
                ))}
            </select>
            <input placeholder="Rating (1–5)" value={rating} onChange={(e) => setRating(e.target.value)} />
            <input placeholder="Comment" value={comment} onChange={(e) => setComment(e.target.value)} />
            <button onClick={addReview}>Add Review</button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <ul>
                {reviews.map((r) => (
                    <li key={r.id}>
                        {r.Book?.title || 'Unknown'} - {r.rating}/5 - "{r.comment}"
                        <button onClick={() => deleteReview(r.id)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
}
