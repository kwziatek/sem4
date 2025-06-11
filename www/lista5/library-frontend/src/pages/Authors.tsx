import { useEffect, useState } from 'react';
import api from '../api';

interface Author {
  id: number;
  name: string;
  birthYear: number;
}

export default function Authors() {
  const [authors, setAuthors] = useState<Author[]>([]);
  const [name, setName] = useState('');
  const [birthYear, setBirthYear] = useState('');
  const [error, setError] = useState('');

  const fetchAuthors = async () => {
    try {
      const res = await api.get('/authors');
      setAuthors(res.data);
    } catch (err: any) {
      setError(err.response?.data?.error || 'Error loading authors');
    }
  };

  const addAuthor = async () => {
    try {
      await api.post('/authors', {
        name,
        birthYear: parseInt(birthYear),
      });
      setName('');
      setBirthYear('');
      fetchAuthors();
    } catch (err: any) {
      setError(err.response?.data?.error || 'Error adding author');
    }
  };

  const deleteAuthor = async (id: number) => {
    try {
      await api.delete(`/authors/${id}`);
      fetchAuthors();
    } catch (err: any) {
      setError(err.response?.data?.error || 'Error deleting author');
    }
  };

  useEffect(() => {
    fetchAuthors();
  }, []);

  return (
    <div>
      <h2>Authors</h2>
      <input placeholder="Name" value={name} onChange={(e) => setName(e.target.value)} />
      <input placeholder="Birth Year" value={birthYear} onChange={(e) => setBirthYear(e.target.value)} />
      <button onClick={addAuthor}>Add Author</button>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <ul>
        {authors.map((a) => (
          <li key={a.id}>
            {a.name} ({a.birthYear})
            <button onClick={() => deleteAuthor(a.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
