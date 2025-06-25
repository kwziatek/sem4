import { useEffect, useState } from 'react';
import api from '../api';

export default function Books() {
    const [books, setBooks] = useState([]);
    const [title, setTitle] = useState('');
    const [authorId, setAuthorId] = useState('');
    const [publicationYear, setPublicationYear] = useState('');
    const [authors, setAuthors] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        fetchBooks();
        api.get('/authors')
            .then(res => setAuthors(res.data))
            .catch(() => setError('Failed to load authors'));
    }, []);

    const fetchBooks = async () => {
        try {
            const res = await api.get('/books');
            setBooks(res.data.data);
        } catch {
            setError('Failed to fetch books');
        }
    };

    const handleAddBook = async () => {
        if (!title.trim()) {
            setError('Title is required');
            return;
        }
        if (!authorId) {
            setError('Please select an author');
            return;
        }

        try {
            await api.post('/books', {
                title,
                authorId: parseInt(authorId),
                publicationYear: publicationYear ? parseInt(publicationYear) : null,
            });
            setTitle('');
            setAuthorId('');
            setPublicationYear('');
            setError('');
            fetchBooks();
        } catch (err: any) {
            setError(err.response?.data?.error || 'Error adding book');
        }
    };

    return (
        <div>
            <h2>Books</h2>
            <input
                placeholder="Title"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
            />
            <select
                value={authorId}
                onChange={(e) => setAuthorId(e.target.value)}
            >
                <option value="">Select Author</option>
                {authors.map((a: any) => (
                    <option key={a.id} value={a.id}>{a.name}</option>
                ))}
            </select>
            <input
                placeholder="Publication Year"
                type="number"
                value={publicationYear}
                onChange={(e) => setPublicationYear(e.target.value)}
            />
            <button onClick={handleAddBook}>Add Book</button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <ul>
                {books.map((book: any) => (
                    <li key={book.id}>{book.title} by {book.Author?.name}</li>
                ))}
            </ul>
        </div>
    );
}
