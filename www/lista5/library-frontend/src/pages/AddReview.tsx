import { useEffect, useState } from 'react';
import api from '../api';

export default function AddReview() {
    const [bookId, setBookId] = useState('');
    const [books, setBooks] = useState([]);
    const [rating, setRating] = useState('');
    const [comment, setComment] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        api.get('/books')
            .then(res => setBooks(res.data.data)) // zakładam, że masz `data.data`
            .catch(() => setError('Failed to load books'));
    }, []);

    const handleAdd = async () => {
        try {
            await api.post('/reviews', {
                bookId: parseInt(bookId),
                rating: parseInt(rating),
                comment
            });
            setBookId('');
            setRating('');
            setComment('');
            setError('');
        } catch (err: any) {
            setError(err.response?.data?.error || 'Add review failed');
        }
    };

    return (
        <div>
            <h2>Add Review</h2>
            <select value={bookId} onChange={(e) => setBookId(e.target.value)}>
                <option value="">Select Book</option>
                {books.map((b: any) => (
                    <option key={b.id} value={b.id}>{b.title}</option>
                ))}
            </select>
            <input placeholder="Rating" type="number" value={rating} onChange={(e) => setRating(e.target.value)} />
            <textarea placeholder="Comment" value={comment} onChange={(e) => setComment(e.target.value)} />
            <button onClick={handleAdd}>Add Review</button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
}
