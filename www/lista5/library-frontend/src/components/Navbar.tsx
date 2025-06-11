import { Link } from 'react-router-dom';

export default function Navbar() {
    return (
        <nav style={{ display: 'flex', gap: '1rem', marginBottom: '1rem' }}>
            <Link to="/">Dashboard</Link>
            <Link to="/books">Books</Link>
            <Link to="/authors">Authors</Link>
            <Link to="/reviews">Reviews</Link>
            <Link to="/login">Login</Link>
        </nav>
    );
}
