import { BrowserRouter as Router, Routes, Route, useNavigate, useLocation } from 'react-router-dom';
import { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css';
import logo from './logo.svg';

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
        <button
          className="accountbutton"
          id="accountbutton"
          aria-label="Account"
          onClick={handleAccountClick}
        >
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
  const [isRegistering, setIsRegistering] = useState(false);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [address, setAddress] = useState('');
  const [securityQuestion, setSecurityQuestion] = useState('');
  const [securityAnswer, setSecurityAnswer] = useState('');
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    if (isRegistering) {
      console.log('Registrierung:');
      console.log('Username:', username);
      console.log('Passwort:', password);
      console.log('Adresse:', address);
      console.log('Sicherheitsfrage:', securityQuestion);
      console.log('Antwort:', securityAnswer);
      alert(`Account für ${username} erstellt.`);
    } else {
      console.log('Login:');
      console.log('Username:', username);
      console.log('Passwort:', password);
      alert(`Eingeloggt als ${username}`);
    }
  };

  return (
    <>
      <Header />
      <div className="container mt-5">
        <h2 className="text-center mb-4">{isRegistering ? 'Registrieren' : 'Login'}</h2>

        <div className="mb-4 text-center">
          <button className="btn-secondary me-2" onClick={() => navigate('/')}>
            Zur Startseite
          </button>
          <button
            className="btn-primary"
            onClick={() => setIsRegistering(!isRegistering)}
          >
            {isRegistering ? 'Zurück zum Login' : 'Account erstellen'}
          </button>
        </div>

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

          {isRegistering && (
            <>
              <div className="mb-3">
                <label htmlFor="address" className="form-label">Adresse</label>
                <input
                  type="text"
                  className="form-control"
                  id="address"
                  value={address}
                  onChange={(e) => setAddress(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
  {/* <label className="form-label">Sicherheitsfrage</label> */}
  <p className="form-text">Wie heißt dein Lieblingskuscheltier?</p>
  
</div>

              <div className="mb-3">
                <label htmlFor="securityAnswer" className="form-label">Antwort auf Sicherheitsfrage</label>
                <input
                  type="text"
                  className="form-control"
                  id="securityAnswer"
                  value={securityAnswer}
                  onChange={(e) => setSecurityAnswer(e.target.value)}
                  required
                />
              </div>
            </>
          )}

          <button type="submit" className="custom-login-button w-100">
            {isRegistering ? 'Registrieren' : 'Login'}
          </button>
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
