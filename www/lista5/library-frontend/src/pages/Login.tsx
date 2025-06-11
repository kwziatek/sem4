import { useState } from 'react';
import api from '../api';

export default function Login() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [token, setToken] = useState(localStorage.getItem('token'));

  const handleLogin = async () => {
    try {
      const res = await api.post('/login', { username, password });
      localStorage.setItem('token', res.data.token);
      setToken(res.data.token);
      setError('');
    } catch (err: any) {
      setError(err.response?.data?.error || 'Login failed');
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    setToken(null);
  };

  return (
      <div>
        <h2>{token ? 'Logged In' : 'Login'}</h2>
        {token ? (
            <button onClick={handleLogout}>Logout</button>
        ) : (
            <>
              <input
                  placeholder="Username"
                  value={username}
                  onChange={(e) => setUsername(e.target.value)}
              />
              <input
                  placeholder="Password"
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
              />
              <button onClick={handleLogin}>Login</button>
              {error && <p style={{ color: 'red' }}>{error}</p>}
            </>
        )}
      </div>
  );
}
