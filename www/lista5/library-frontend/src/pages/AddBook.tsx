import { useEffect, useState } from 'react';
import api from '../api';

export default function AddBook() {
    const [title, setTitle] = useState('');
    const [authorId, setAuthorId] = useState('');
    const [authors, setAuthors] = useState([]);
    const [publicationYear, setPublicationYear] = useState('');
    const [isbn, setIsbn] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        api.get('/authors')
            .then(res => setAuthors(res.data))
            .catch(() => setError('Failed to load authors'));
    }, []);

    const handleAdd = async () => {
        try {
            await api.post('/books', {
                title,
                authorId: parseInt(authorId),
                publicationYear: parseInt(publicationYear),
                isbn,
            });
            setTitle('');
            setAuthorId('');
            setPublicationYear('');
            setIsbn('');
            setError('');
        } catch (err: any) {
            setError(err.response?.data?.error || 'Add book failed');
        }
    };

    return (
        <div>
            <h2>Add Book</h2>
            <input placeholder="Title" value={title} onChange={(e) => setTitle(e.target.value)} />
            <select value={authorId} onChange={(e) => setAuthorId(e.target.value)}>
                <option value="">Select Author</option>
                {authors.map((a: any) => (
                    <option key={a.id} value={a.id}>{a.name}</option>
                ))}
            </select>
            <input placeholder="Publication Year" type="number" value={publicationYear} onChange={(e) => setPublicationYear(e.target.value)} />
            <input placeholder="ISBN" value={isbn} onChange={(e) => setIsbn(e.target.value)} />
            <button onClick={handleAdd}>Add Book</button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
    );
}
