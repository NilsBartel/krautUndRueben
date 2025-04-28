import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route, useNavigate, useLocation } from 'react-router-dom';
import { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';


function Header() {
  const navigate = useNavigate();
  const location = useLocation(); 

  const handleAccountClick = () => {
    navigate('/account');
  };

  return (
    <div className="container-fluid p-5 text-white text-center header-section">
      <h1>KRAUT UND RÜBEN</h1>

      
      {location.pathname === '/' && (
        <button className="accountbutton btn btn-light mt-3" id="accountbutton" aria-label="Account" onClick={handleAccountClick}>
          <img src="account.png" alt="Account" className="account-img" />
        </button>
      )}
    </div>
  );
}

function Home() {
  return (
    <>
      <Header />
      <div className="container mt-5 text-center">
        <p>Willkommen auf der Startseite!</p>
      </div>
    </>
  );
}

function Account() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Username:', username);
    console.log('Password:', password);
    alert(`Eingeloggt als ${username}`);
  };

  return (
    <>
      <Header />
      <div className="container mt-5">
        <h2 className="text-center mb-4">Login</h2>
        <form onSubmit={handleSubmit} className="mx-auto" style={{ maxWidth: '400px' }}>
          <div className="mb-3">
            <label htmlFor="username" className="form-label">Benutzername</label>
            <input
              type="text"
              className="form-control"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="password" className="form-label">Passwort</label>
            <input
              type="password"
              className="form-control"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="btn btn-primary w-100">Login</button>
        </form>
      </div>
    </>
  );
}

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/account" element={<Account />} />
      </Routes>
    </Router>
  );
}

export default App;
