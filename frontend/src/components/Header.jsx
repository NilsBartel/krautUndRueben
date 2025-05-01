import { useNavigate, useLocation } from 'react-router-dom';

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

export default Header;
