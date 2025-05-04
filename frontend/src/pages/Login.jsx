import { useState } from 'react';
import { resolvePath, useNavigate } from 'react-router-dom';
import Header from '../components/Header';
import { setToken } from '../lib/token';

function Login() {
  const [isRegistering, setIsRegistering] = useState(false);
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [address, setAddress] = useState('');
  const [name, setName] = useState('');
  const [nachname, setNachname] = useState('');
  const [subscribe, setSubscribe] = useState('');
  const [securityQuestion, setSecurityQuestion] = useState('');
  const [securityAnswer, setSecurityAnswer] = useState('');
  const [street, setStreet] = useState('');
  const [houseNumber, setHouseNumber] = useState('');
  const [postalCode, setPostalCode] = useState('');
  const [city, setCity] = useState('');
  const [state, setState] = useState('');
  const [country, setCountry] = useState('');
  const [isResettingPassword, setIsResettingPassword] = useState(false);


  const navigate = useNavigate();

  const apiPath = "http://localhost:8080"

  const handleSubmit = (e) => {
    e.preventDefault();
    if (isRegistering) {
      onRegistration(e);
    } else {
      onLogin(e);

    }
    if (isResettingPassword) {
      onResetPassword(e);
    } else if (isRegistering) {
      onRegistration(e);
    } else {
      onLogin(e);
    }



    function onLogin(e) {
      let form = e.target;


      fetch(apiPath + "/account/login", {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          username: form.username.value,
          password: form.password.value
        })
      })
        .then((response) => {
          return response.json().then(json => {
            return response.ok ? json : Promise.reject(json);
          });
        })
        .then((response) => {
          console.log(response);

          setToken(response.token);
          navigate("/");
        })
        .catch((response) => {
          console.log(response);
          alert(response.error)
        });




    }

    function onRegistration(e) {
      let form = e.target;

      fetch(apiPath + "/account/register", {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          username: form.username.value,
          password: form.password.value,
          securityQuestion: form.securityAnswer.value,
          firstName: form.name.value,
          lastName: form.nachname.value,
          email: "replace@me.please",
          abo: subscribe,
          street: form.street.value,
          houseNumber: form.houseNumber.value,
          zipCode: form.postalCode.value,
          cityDistrict: form.city.value,
          city: form.state.value,
          country: form.country.value
        })
      })
        .then((response) => {
          return response.json().then(json => {
            return response.ok ? json : Promise.reject(json);
          });
        })
        .then((response) => {
          console.log(response);

          setToken(response.token)
          // navigate("/");
        })
        .catch((response) => {
          console.log(response);
          alert(response.error)
        });
    }


    function onResetPassword(e) {
      let form = e.target;

      fetch(apiPath + "/account/reset-password", {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          username: form.username.value,
          securityAnswer: form.securityAnswer.value,
          newPassword: form.newPassword.value
        })
      })
        .then(response => response.json().then(json => response.ok ? json : Promise.reject(json)))
        .then(response => {
          alert("Passwort erfolgreich geändert!");
          setIsResettingPassword(false);
          setPassword('');
        })
        .catch(response => {
          alert(response.error);
        });
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


          {!isRegistering && !isResettingPassword && (
            <>
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

              <div className="mb-3 text-end">
                <button
                  type="button"
                  className="btn btn-link p-0"
                  onClick={() => setIsResettingPassword(true)}
                >
                  Passwort vergessen?
                </button>
              </div>
            </>
          )}
          {isResettingPassword && (
            <>
              <div className="mb-3">
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

              <div className="mb-3">
                <label htmlFor="newPassword" className="form-label">Neues Passwort</label>
                <input
                  type="password"
                  className="form-control"
                  id="newPassword"
                  onChange={(e) => setPassword(e.target.value)}
                  required
                />
              </div>
              {isResettingPassword && (
                <div className="mb-3 text-end">
                  <button
                    type="button"
                    className="btn btn-link p-0"
                    onClick={() => setIsResettingPassword(false)}
                  >
                    Zurück zum Login
                  </button>
                </div>
              )}

            </>
          )}



          {isRegistering && (
            <>

              <div className="mb-3">
                <label htmlFor="name" className="form-label">Vorname</label>
                <input
                  type="text"
                  className="form-control"
                  id="name"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="nachname" className="form-label">Nachname</label>
                <input
                  type="text"
                  className="form-control"
                  id="nachname"
                  value={nachname}
                  onChange={(e) => setNachname(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="street" className="form-label">Straße</label>
                <input
                  type="text"
                  className="form-control"
                  id="street"
                  value={street}
                  onChange={(e) => setStreet(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="houseNumber" className="form-label">Hausnummer</label>
                <input
                  type="text"
                  className="form-control"
                  id="houseNumber"
                  value={houseNumber}
                  onChange={(e) => setHouseNumber(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="postalCode" className="form-label">Postleitzahl</label>
                <input
                  type="text"
                  className="form-control"
                  id="postalCode"
                  value={postalCode}
                  onChange={(e) => setPostalCode(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="city" className="form-label">Ort</label>
                <input
                  type="text"
                  className="form-control"
                  id="city"
                  value={city}
                  onChange={(e) => setCity(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="state" className="form-label">Stadt (Bundesland)</label>
                <input
                  type="text"
                  className="form-control"
                  id="state"
                  value={state}
                  onChange={(e) => setState(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="country" className="form-label">Land</label>
                <input
                  type="text"
                  className="form-control"
                  id="country"
                  value={country}
                  onChange={(e) => setCountry(e.target.value)}
                  required
                />
              </div>

              <div className="mb-3">
                <label htmlFor="subscribe" className="form-label">Abo abschließen?</label>
                <select
                  id="subscribe"
                  className="form-control"
                  value={subscribe}
                  onChange={(e) => setSubscribe(e.target.value)}
                  required
                >
                  <option value="">Bitte wählen</option>
                  <option value="ja">Ja</option>
                  <option value="nein">Nein</option>
                </select>
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

export default Login;
